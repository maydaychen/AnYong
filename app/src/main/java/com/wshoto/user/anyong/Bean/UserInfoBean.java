package com.wshoto.user.anyong.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"integral":"897","id":"5","username":"1","avatar":"https://anyong.wshoto.com/uploads/20181010173956.png","english_name":"tom","job_no":"123455","email":"tom@wshoto.com","mobile":"13665137658","birthday":"2018-09-04","join_time":"2000-01-01","position":"Account","department":"1","invitecode":"00000005","talent_ops":"1","gds_pss":"1","tranning":"0","tranning_hour":"0","more_time":"0","exceed_award":"0","nickname":"good master","firend_num":"6","switch":1}
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

    public static class DataBean implements Serializable {
        /**
         * integral : 897
         * id : 5
         * username : 1
         * avatar : https://anyong.wshoto.com/uploads/20181010173956.png
         * english_name : tom
         * job_no : 123455
         * email : tom@wshoto.com
         * mobile : 13665137658
         * birthday : 2018-09-04
         * join_time : 2000-01-01
         * position : Account
         * department : 1
         * invitecode : 00000005
         * talent_ops : 1
         * gds_pss : 1
         * tranning : 0
         * tranning_hour : 0
         * more_time : 0
         * exceed_award : 0
         * nickname : good master
         * firend_num : 6
         * switch : 1
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
        private String talent_ops;
        private String gds_pss;
        private String tranning;
        private String tranning_hour;
        private String more_time;
        private String exceed_award;
        private String nickname;
        private String firend_num;
        @SerializedName("switch")
        private int switchX;
        private boolean has_new;

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

        public String getTalent_ops() {
            return talent_ops;
        }

        public void setTalent_ops(String talent_ops) {
            this.talent_ops = talent_ops;
        }

        public String getGds_pss() {
            return gds_pss;
        }

        public void setGds_pss(String gds_pss) {
            this.gds_pss = gds_pss;
        }

        public String getTranning() {
            return tranning;
        }

        public void setTranning(String tranning) {
            this.tranning = tranning;
        }

        public String getTranning_hour() {
            return tranning_hour;
        }

        public void setTranning_hour(String tranning_hour) {
            this.tranning_hour = tranning_hour;
        }

        public String getMore_time() {
            return more_time;
        }

        public void setMore_time(String more_time) {
            this.more_time = more_time;
        }

        public String getExceed_award() {
            return exceed_award;
        }

        public void setExceed_award(String exceed_award) {
            this.exceed_award = exceed_award;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFirend_num() {
            return firend_num;
        }

        public void setFirend_num(String firend_num) {
            this.firend_num = firend_num;
        }

        public int getSwitchX() {
            return switchX;
        }

        public void setSwitchX(int switchX) {
            this.switchX = switchX;
        }

        public boolean isHas_new() {
            return has_new;
        }

        public void setHas_new(boolean has_new) {
            this.has_new = has_new;
        }
    }
}
