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

    public static Boolean WidgetOrderValidation(String input){
        for (int i = 1; i <= 5; i++) {
            if(!input.contains(String.valueOf(i))){
                return false;
            }
            if(i%2 == 0 && String.valueOf(input.charAt(i-1)).equals(",") || i%2 == 1){

            } else {
                return false;
            }
        }
        return true;
    }
}
