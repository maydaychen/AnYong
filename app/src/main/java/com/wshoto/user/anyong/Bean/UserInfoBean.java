package com.wshoto.user.anyong.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"integral":"20","id":"10","username":"Yi Chen","avatar":"https://anyong.wshoto.com/uploads/20180625164129.png","english_name":"Yi Chen","job_no":"CN010458","email":"chenyi@cn.ey.com","mobile":"13665137658","birthday":"12-06-91","join_time":"2011-09-17","position":"Associate Manager","department":"CBS PEO Field Services SZ","invitecode":"","talent_ops":"","gds_pss":"","tranning":"0","location_city":"苏州市","tranning_hour":"0","more_time":"0","exceed_award":"0","has_new":"0","anniversary":"","counts":5,"nickname":"暂无等级","firend_num":"2","switch":1,"switcherHat":1,"hatUrl":"https://anyong.wshoto.com/uploads/thumbnail/2018113019080577187.png"}
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
         * integral : 20
         * id : 10
         * username : Yi Chen
         * avatar : https://anyong.wshoto.com/uploads/20180625164129.png
         * english_name : Yi Chen
         * job_no : CN010458
         * email : chenyi@cn.ey.com
         * mobile : 13665137658
         * birthday : 12-06-91
         * join_time : 2011-09-17
         * position : Associate Manager
         * department : CBS PEO Field Services SZ
         * invitecode :
         * talent_ops :
         * gds_pss :
         * tranning : 0
         * location_city : 苏州市
         * tranning_hour : 0
         * more_time : 0
         * exceed_award : 0
         * has_new : 0
         * anniversary :
         * counts : 5
         * nickname : 暂无等级
         * firend_num : 2
         * switch : 1
         * switcherHat : 1
         * hatUrl : https://anyong.wshoto.com/uploads/thumbnail/2018113019080577187.png
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
        private String location_city;
        private String tranning_hour;
        private String more_time;
        private String exceed_award;
        private String has_new;
        private String anniversary;
        private int counts;
        private String nickname;
        private String firend_num;
        @SerializedName("switch")
        private int switchX;
        private int switcherHat;
        private String hatUrl;

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

        public String getLocation_city() {
            return location_city;
        }

        public void setLocation_city(String location_city) {
            this.location_city = location_city;
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

        public String getHas_new() {
            return has_new;
        }

        public void setHas_new(String has_new) {
            this.has_new = has_new;
        }

        public String getAnniversary() {
            return anniversary;
        }

        public void setAnniversary(String anniversary) {
            this.anniversary = anniversary;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
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

        public int getSwitcherHat() {
            return switcherHat;
        }

        public void setSwitcherHat(int switcherHat) {
            this.switcherHat = switcherHat;
        }

        public String getHatUrl() {
            return hatUrl;
        }

        public void setHatUrl(String hatUrl) {
            this.hatUrl = hatUrl;
        }
    }
}
