package com.accenture.test.springboot.entity;

import jakarta.persistence.Entity;

@Entity
public class UserSettingKeyLov {
    private String biometric_login = "false";
    private String push_notification = "false";
    private String sms_notification = "false";
    private String show_onboarding = "false";
    private String widget_order = "1,2,3,4,5";

    public String getBiometric_login() {
        return biometric_login;
    }

    public void setBiometric_login(String biometric_login) {
        this.biometric_login = biometric_login;
    }

    public String getPush_notification() {
        return push_notification;
    }

    public void setPush_notification(String push_notification) {
        this.push_notification = push_notification;
    }

    public String getSms_notification() {
        return sms_notification;
    }

    public void setSms_notification(String sms_notification) {
        this.sms_notification = sms_notification;
    }

    public String getShow_onboarding() {
        return show_onboarding;
    }

    public void setShow_onboarding(String show_onboarding) {
        this.show_onboarding = show_onboarding;
    }

    public String getWidget_order() {
        return widget_order;
    }

    public void setWidget_order(String widget_order) {
        this.widget_order = widget_order;
    }
}
