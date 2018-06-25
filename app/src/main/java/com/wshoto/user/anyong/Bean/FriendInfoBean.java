package com.wshoto.user.anyong.Bean;

public class FriendInfoBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"id":"2","username":"","avatar":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2194440970,3478897226&fm=179&app=42&f=JPEG?w=121&h=140","english_name":"bob","job_no":"123456","email":"test@wshoto.com","mobile":"18168839589","birthday":"2018-06-10","join_time":"0","position":"会计","department_id":"暂无部门","invitecode":"1235","integral":21,"nickname":"青铜用户","level":21,"friendlevel":21}
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

    public static class DataBean {
        /**
         * id : 2
         * username :
         * avatar : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2194440970,3478897226&fm=179&app=42&f=JPEG?w=121&h=140
         * english_name : bob
         * job_no : 123456
         * email : test@wshoto.com
         * mobile : 18168839589
         * birthday : 2018-06-10
         * join_time : 0
         * position : 会计
         * department_id : 暂无部门
         * invitecode : 1235
         * integral : 21
         * nickname : 青铜用户
         * level : 21
         * friendlevel : 21
         */

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
        private String department_id;
        private String invitecode;
        private int integral;
        private String nickname;
        private int level;
        private int friendlevel;

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

        public String getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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
