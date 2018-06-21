package com.wshoto.user.anyong.Bean;

import java.io.Serializable;
import java.util.List;

public class ThankUserBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"2","username":"","avatar":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2194440970,3478897226&fm=179&app=42&f=JPEG?w=121&h=140","english_name":"bob","mobile":"18168839589"}]
     */

    private int code;
    private MessageBean message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MessageBean {
        /**
         * status : success
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class DataBean implements Serializable{
        /**
         * id : 2
         * username :
         * avatar : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2194440970,3478897226&fm=179&app=42&f=JPEG?w=121&h=140
         * english_name : bob
         * mobile : 18168839589
         */

        private String id;
        private String username;
        private String avatar;
        private String english_name;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEnglish_name() {
            return english_name;
        }

        public void setEnglish_name(String english_name) {
            this.english_name = english_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
