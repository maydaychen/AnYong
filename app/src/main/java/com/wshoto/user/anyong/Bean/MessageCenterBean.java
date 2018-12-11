package com.wshoto.user.anyong.Bean;

import java.util.List;

public class MessageCenterBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"185","title":"眼保健操要开始啦","receiver":"All","city":null,"job_no":"","job_nos":null,"position":"","content":"眼保健操要开始啦~快进入APP签到确认吧。","created":"2018-12-11 10:00:02","department":"","push_type":"1","type":"4","isread":0,"published":"2018-12-11 10:00:02"},{"id":"184","title":"颈椎保健操要开始啦","receiver":"All","city":null,"job_no":"","job_nos":null,"position":"","content":"颈椎保健操要开始啦~快进入APP签到确认吧。","created":"2018-12-10 16:00:01","department":"","push_type":"1","type":"4","isread":0,"published":"2018-12-10 16:00:01"},{"id":"179","title":"批量推送工号标题","receiver":"","city":null,"job_no":"","job_nos":"CN010492,CN010287,CN010079,CN010405,CN010458","position":"","content":"批量推送工号内容","created":"2018-12-08 18:18:30","department":"","push_type":"1","type":"1","isread":0,"published":"2018-12-08 18:18:30"},{"id":"126","title":"Test Rank","receiver":"","city":null,"job_no":"","job_nos":"","position":"Associate Manager","content":"Test Rank","created":"2018-12-07 11:23:37","department":"","push_type":"1","type":"1","isread":0,"published":"2018-12-07 11:23:37"},{"id":"26","title":"Associate Manager标题","receiver":"","city":null,"job_no":"","job_nos":"","position":"Associate Manager","content":"Associate Manager内容","created":"2018-12-05 21:40:29","department":"","push_type":"1","type":"1","isread":0,"published":"2018-12-05 21:40:29"}]
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

    public static class DataBean {
        /**
         * id : 185
         * title : 眼保健操要开始啦
         * receiver : All
         * city : null
         * job_no :
         * job_nos : null
         * position :
         * content : 眼保健操要开始啦~快进入APP签到确认吧。
         * created : 2018-12-11 10:00:02
         * department :
         * push_type : 1
         * type : 4
         * isread : 0
         * published : 2018-12-11 10:00:02
         */

        private String id;
        private String title;
        private String receiver;
        private Object city;
        private String job_no;
        private Object job_nos;
        private String position;
        private String content;
        private String created;
        private String department;
        private String push_type;
        private String type;
        private int isread;
        private String published;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no;
        }

        public Object getJob_nos() {
            return job_nos;
        }

        public void setJob_nos(Object job_nos) {
            this.job_nos = job_nos;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPush_type() {
            return push_type;
        }

        public void setPush_type(String push_type) {
            this.push_type = push_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsread() {
            return isread;
        }

        public void setIsread(int isread) {
            this.isread = isread;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
        }
    }
}
