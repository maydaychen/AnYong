package com.wshoto.user.anyong.Bean;

import java.util.List;

/**
 * Created by user on 2018/5/8.
 * 2091320109@qq.com
 */

public class MyRadiuBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"206","english_name":"陈译","mobile":"13665137658","integral":"230","avatar":"https://anyong.wshoto.com/uploads/20180625164129.png","level":92,"friendlevel":1}]
     * my : {"id":"206","english_name":"陈译","mobile":"13665137658","integral":"230","avatar":"https://anyong.wshoto.com/uploads/20180625164129.png","level":92,"friendlevel":1}
     */

    private int code;
    private MessageBean message;
    private MyBean my;
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

    public MyBean getMy() {
        return my;
    }

    public void setMy(MyBean my) {
        this.my = my;
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

    public static class MyBean {
        /**
         * id : 206
         * english_name : 陈译
         * mobile : 13665137658
         * integral : 230
         * avatar : https://anyong.wshoto.com/uploads/20180625164129.png
         * level : 92
         * friendlevel : 1
         */

        private String id;
        private String english_name;
        private String mobile;
        private String integral;
        private String avatar;
        private int level;
        private int friendlevel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getFriendlevel() {
            return friendlevel;
        }

        public void setFriendlevel(int friendlevel) {
            this.friendlevel = friendlevel;
        }
    }

    public static class DataBean {
        /**
         * id : 206
         * english_name : 陈译
         * mobile : 13665137658
         * integral : 230
         * avatar : https://anyong.wshoto.com/uploads/20180625164129.png
         * level : 92
         * friendlevel : 1
         */

        private String id;
        private String english_name;
        private String mobile;
        private String integral;
        private String avatar;
        private int level;
        private int friendlevel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getFriendlevel() {
            return friendlevel;
        }

        public void setFriendlevel(int friendlevel) {
            this.friendlevel = friendlevel;
        }
    }
}
