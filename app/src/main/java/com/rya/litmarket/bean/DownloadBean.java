package com.rya.litmarket.bean;

import android.os.Environment;

import java.io.File;

/**
 * Created by ryanyans32 on 2017/3/28.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class DownloadBean {
    public static final int STATE_NONE = 0;
    public static final int STATE_WAITING = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_ERROR = 4;
    public static final int STATE_SUCCESS = 5;


    private int id;
    private String name;
    private String packageName;
    private String downloadUrl;
    private long size;

    private int currentState;
    private long currentPos;
    private String path;

    private static final String MARKET_DIR = "litmarket";// 文件夹名称
    private static final String DOWNLOAD_DIR = "download";// 子文件夹名称



    public static DownloadBean copy(AppDetailBean info) {
        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setId(info.getId());
        downloadBean.setName(info.getName());
        downloadBean.setPackageName(info.getPackageName());
        downloadBean.setDownloadUrl(info.getDownloadUrl());
        downloadBean.setSize(info.getSize());

        downloadBean.setCurrentState(STATE_NONE);
        downloadBean.setCurrentPos(0);
        downloadBean.setPath(getFilePath(info.getName()));

        return downloadBean;
    }
    public static DownloadBean copy(HomeBean.ListBean info) {
        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setId(info.getId());
        downloadBean.setName(info.getName());
        downloadBean.setPackageName(info.getPackageName());
        downloadBean.setDownloadUrl(info.getDownloadUrl());
        downloadBean.setSize(info.getSize());

        downloadBean.setCurrentState(STATE_NONE);
        downloadBean.setCurrentPos(0);
        downloadBean.setPath(getFilePath(info.getName()));

        return downloadBean;
    }


    private static String getFilePath(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        builder.append(File.separator);
        builder.append(MARKET_DIR);
        builder.append(File.separator);
        builder.append(DOWNLOAD_DIR);

        if (createDir(builder.toString())) {
            return builder.toString() + File.separator + name + ".apk";
        }
        return null;
    }

    private static boolean createDir(String dir) {
        File file = new File(dir);
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    public float getProgress() {
        return ((currentPos / (size + 0.5f)));
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public long getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(long currentPos) {
        this.currentPos = currentPos;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
