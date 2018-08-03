package com.wshoto.user.anyong.Bean;

import java.io.Serializable;

public class UserInfoBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"integral":"500","id":"5","username":"","avatar":"","english_name":"tom","job_no":"123455","email":"tom@wshoto.com","mobile":"13665137658","birthday":"1991-05-03","join_time":"2000-01-01","position":"会计","department":"1","invitecode":"00000005","nickname":"钻石 "}
     */

    private int code;
    private MessageBean message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * integral : 500
         * id : 5
         * username :
         * avatar :
         * english_name : tom
         * job_no : 123455
         * email : tom@wshoto.com
         * mobile : 13665137658
         * birthday : 1991-05-03
         * join_time : 2000-01-01
         * position : 会计
         * department : 1
         * invitecode : 00000005
         * nickname : 钻石
         */

        private String integral;
        private String id;
        private String username;
        private String avatar;
        private String english_name;
        private String job_no;
        private String email;
        private String mobile;
        private String birthday;
        private String join_time;
        private String position;
        private String department;
        private String invitecode;
        private String nickname;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

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

        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJoin_time() {
            return join_time;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
