package com.accenture.test.springboot.util;

public class Helper {
    public enum Env{
        BIOMETRIC_LOGIN("biometric_login"),
        PUSH_NOTIFICATION("push_notification"),
        SMS_NOTIFICATION("sms_notification"),
        SHOW_ONBOARDING("show_onboarding"),
        WIDGET_ORDER("widget_order");

        private String desc;
        Env(String stringEnv){
            this.desc = stringEnv;
        }

        public String getDesc() {
            return desc;
        }

    }
}
