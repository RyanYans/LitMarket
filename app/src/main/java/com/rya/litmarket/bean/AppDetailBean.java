package com.rya.litmarket.bean;

import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/25.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppDetailBean {

    /**
     * id : 1599700
     * name : 酷狗音乐
     * packageName : com.kugou.android
     * iconUrl : app/com.kugou.android/icon.jpg
     * stars : 4.0
     * downloadNum : 6000万+
     * version : 6.3.2
     * date : 2014-05-28
     * size : 8790060
     * downloadUrl : app/com.kugou.android/com.kugou.android.apk
     * des : 全新改版震撼发布！让
     * author : 酷狗音乐
     * screen : ["app/com.kugou.android/screen0.jpg","app/com.kugou.android/screen1.jpg","app/com.kugou.android/screen2.jpg","app/com.kugou.android/screen3.jpg","app/com.kugou.android/screen4.jpg"]
     * safe : [{"safeUrl":"app/com.kugou.android/safeIcon0.jpg","safeDesUrl":"app/com.kugou.android/safeDesUrl0.jpg","safeDes":"已通过安智市场官方认证，是正版软件","safeDesColor":0},{"safeUrl":"app/com.kugou.android/safeIcon1.jpg","safeDesUrl":"app/com.kugou.android/safeDesUrl1.jpg","safeDes":"已通过安智市场安全检测，请放心使用","safeDesColor":0},{"safeUrl":"app/com.kugou.android/safeIcon2.jpg","safeDesUrl":"app/com.kugou.android/safeDesUrl2.jpg","safeDes":"无任何形式的广告","safeDesColor":0}]
     */

    private int id;
    private String name;
    private String packageName;
    private String iconUrl;
    private double stars;
    private String downloadNum;
    private String version;
    private String date;
    private int size;
    private String downloadUrl;
    private String des;
    private String author;
    private List<String> screen;
    private List<SafeBean> safe;

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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public List<SafeBean> getSafe() {
        return safe;
    }

    public void setSafe(List<SafeBean> safe) {
        this.safe = safe;
    }

    public static class SafeBean {
        /**
         * safeUrl : app/com.kugou.android/safeIcon0.jpg
         * safeDesUrl : app/com.kugou.android/safeDesUrl0.jpg
         * safeDes : 已通过安智市场官方认证，是正版软件
         * safeDesColor : 0
         */

        private String safeUrl;
        private String safeDesUrl;
        private String safeDes;
        private int safeDesColor;

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public int getSafeDesColor() {
            return safeDesColor;
        }

        public void setSafeDesColor(int safeDesColor) {
            this.safeDesColor = safeDesColor;
        }
    }
}
