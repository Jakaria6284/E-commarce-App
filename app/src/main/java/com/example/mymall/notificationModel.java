package com.example.mymall;

public class notificationModel {
    private String notificationTitle;
    private String notificationBody;
    private boolean readed;

    public notificationModel(String notificationTitle, String notificationBody, boolean readed) {
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.readed = readed;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }
}
