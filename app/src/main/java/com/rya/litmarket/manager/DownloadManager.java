package com.rya.litmarket.manager;

import android.content.Intent;
import android.net.Uri;

import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.bean.DownloadBean;
import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.utils.IOUtils;
import com.rya.litmarket.utils.UiUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Response;

/**
 * Created by ryanyans32 on 2017/3/28.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class DownloadManager {

    //存储观察者对象
    private List<DownloadObserver> mDownloadObservers = new ArrayList<>();
    // 存储下载信息条目
    private ConcurrentHashMap<Integer, DownloadBean> mDownloadBeanInfos = new ConcurrentHashMap<>();
    // 存储下载任务条目
    private ConcurrentHashMap<Integer, Task> mDownoloadTaskInfos = new ConcurrentHashMap<>();

    private static DownloadManager mDownloadManager;

    private DownloadManager() {

    }

    // 单例-懒汉
    public static DownloadManager getInstance() {
        if (mDownloadManager == null) {
            synchronized (DownloadManager.class) {
                if (mDownloadManager == null) {
                    mDownloadManager = new DownloadManager();
                }
            }
        }
        return mDownloadManager;
    }

    // 注册监听者
    public synchronized void registerObserver(DownloadObserver observer) {
        if (observer != null && !mDownloadObservers.contains(observer)) {
            mDownloadObservers.add(observer);
        }
    }

    // 注销监听者
    public synchronized void unRegisterObserver(DownloadObserver observer) {
        if (observer != null && mDownloadObservers.contains(observer)) {
            mDownloadObservers.remove(observer);
        }
    }

    public synchronized void notifyObserverStateChanged(DownloadBean info) {
        for (DownloadObserver observer : mDownloadObservers) {
            observer.onDownloadStateChanged(info);
        }
    }

    public synchronized void notifyObserverProgressChanged(DownloadBean info) {
        for (DownloadObserver observer : mDownloadObservers) {
            observer.onDownloadProgressChanged(info);
        }
    }

    public synchronized void download(AppDetailBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        //如果列表无下载对象，创建一个
        if (downloadInfo == null) {
            downloadInfo = DownloadBean.copy(info);
        }
        // 更改当前状态，并通知监听者改变
        downloadInfo.setCurrentState(DownloadBean.STATE_WAITING);
        notifyObserverStateChanged(downloadInfo);

        // 把下载信息加入集合中
        mDownloadBeanInfos.put(downloadInfo.getId(), downloadInfo);

        Task task = new Task(downloadInfo);
        ThreadManager.getThreadPool().execute(task);
        // 把下载任务加入集合中
        mDownoloadTaskInfos.put(downloadInfo.getId(), task);
    }

    public synchronized void download(HomeBean.ListBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        //如果列表无下载对象，创建一个
        if (downloadInfo == null) {
            downloadInfo = DownloadBean.copy(info);
        }
        // 更改当前状态，并通知监听者改变
        downloadInfo.setCurrentState(DownloadBean.STATE_WAITING);
        notifyObserverStateChanged(downloadInfo);

        // 把下载信息加入集合中
        mDownloadBeanInfos.put(downloadInfo.getId(), downloadInfo);

        Task task = new Task(downloadInfo);
        ThreadManager.getThreadPool().execute(task);
        // 把下载任务加入集合中
        mDownoloadTaskInfos.put(downloadInfo.getId(), task);
    }


    private class Task implements Runnable {
        private DownloadBean downloadInfo;
        private Response response;

        public Task(DownloadBean downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            // 更新下载任务
            downloadInfo.setCurrentState(DownloadBean.STATE_DOWNLOADING);
            notifyObserverStateChanged(downloadInfo);

            File file = new File(downloadInfo.getPath());
            // 文件存储出错或文件大小为0，删除文件。
            // 文件不存在，创建文件执行下载任务
            if (!file.exists() || (file.length() != downloadInfo.getCurrentPos()) || (downloadInfo.getCurrentPos() == 0)) {
                file.delete();
                downloadInfo.setCurrentPos(0);
                response = HttpUtil.download(downloadInfo.getDownloadUrl());

            } else {
                // 文件存在，端点续传 + "range参数"
                response = HttpUtil.download(downloadInfo.getDownloadUrl() + "&range=" + file.length());
            }

            InputStream in = null;
            FileOutputStream fos = null;
            if ((response != null) && (response.body() != null)) {
                in = response.body().byteStream();
                try {
                    fos = new FileOutputStream(file, true);
                    byte[] buff = new byte[1024 * 4];
                    int length = 0;

                    while (((length = in.read(buff)) != -1) &&
                            downloadInfo.getCurrentState() == DownloadBean.STATE_DOWNLOADING) {
                        fos.write(buff, 0, length);
                        fos.flush();

                        downloadInfo.setCurrentPos(downloadInfo.getCurrentPos() + length);

                        notifyObserverProgressChanged(downloadInfo);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(in);
                    IOUtils.close(fos);
                }

                // 下载结束（或中途暂停）
                if (file.length() == downloadInfo.getSize()) {
                    // 文件长度正确
                    downloadInfo.setCurrentState(DownloadBean.STATE_SUCCESS);
                    notifyObserverStateChanged(downloadInfo);
                } else if (downloadInfo.getCurrentState() == DownloadBean.STATE_PAUSE) {
                    // 中途暂停
                    notifyObserverStateChanged(downloadInfo);
                } else {
                    //下载失败
                    downloadInfo.setCurrentState(DownloadBean.STATE_ERROR);
                    downloadInfo.setCurrentPos(0);
                    notifyObserverStateChanged(downloadInfo);

                    //删除文件
                    file.delete();
                }
            } else {
                // 无数据，下载失败
                downloadInfo.setCurrentState(DownloadBean.STATE_ERROR);
                downloadInfo.setCurrentPos(0);
                notifyObserverStateChanged(downloadInfo);

                //删除文件
                file.delete();
            }

            //最后移除下载任务
            mDownoloadTaskInfos.remove(downloadInfo.getId());
        }
    }

    public synchronized void pause(AppDetailBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        if (downloadInfo != null) {
            int state = downloadInfo.getCurrentState();
            if (state == DownloadBean.STATE_DOWNLOADING || state == DownloadBean.STATE_WAITING) {
                Task task = mDownoloadTaskInfos.get(info.getId());
                if (task != null) {
                    ThreadManager.getThreadPool().cancelTask(task);
                }
                downloadInfo.setCurrentState(DownloadBean.STATE_PAUSE);
                notifyObserverStateChanged(downloadInfo);
            }
        }
    }

    public synchronized void pause(HomeBean.ListBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        if (downloadInfo != null) {
            int state = downloadInfo.getCurrentState();
            if (state == DownloadBean.STATE_DOWNLOADING || state == DownloadBean.STATE_WAITING) {
                Task task = mDownoloadTaskInfos.get(info.getId());
                if (task != null) {
                    ThreadManager.getThreadPool().cancelTask(task);
                }
                downloadInfo.setCurrentState(DownloadBean.STATE_PAUSE);
                notifyObserverStateChanged(downloadInfo);
            }
        }
    }


    public synchronized void install(AppDetailBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + downloadInfo.getPath()),
                "application/vnd.android.package-archive");
        UiUtil.getContext().startActivity(intent);
    }

    public synchronized void install(HomeBean.ListBean info) {
        if (info == null) {
            return;
        }
        DownloadBean downloadInfo = mDownloadBeanInfos.get(info.getId());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + downloadInfo.getPath()),
                "application/vnd.android.package-archive");
        UiUtil.getContext().startActivity(intent);
    }

    public DownloadBean getInfo(AppDetailBean data) {
        if (data != null) {
            return mDownloadBeanInfos.get(data.getId());
        }
        return null;
    }

    public DownloadBean getInfo(HomeBean.ListBean data) {
        if (data != null) {
            return mDownloadBeanInfos.get(data.getId());
        }
        return null;
    }


    public interface DownloadObserver {
        public void onDownloadStateChanged(DownloadBean info);

        public void onDownloadProgressChanged(DownloadBean info);
    }
}
