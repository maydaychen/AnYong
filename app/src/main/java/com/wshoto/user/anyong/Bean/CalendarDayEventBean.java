package com.wshoto.user.anyong.Bean;

import java.util.List;

public class CalendarDayEventBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"3","title":"测试","start_time":"2018-06-21 10:51:12","end_time":"2018-06-21 21:57:52","content":"测试","type":"有时效","is_join":"1","created":0,"updated":0,"published":"2018-10-11 17:17:52","qrcode":""},{"id":"1","title":"测试","start_time":"2018-06-21 10:51:12","end_time":"2018-06-21 21:57:52","content":"测试","type":"有时效","is_join":"1","created":0,"updated":0,"published":"2018-06-21 10:51:12","qrcode":""},{"id":"2","title":"测试","start_time":"2018-06-21 10:51:12","end_time":"2018-06-21 21:57:52","content":"测试","type":"有时效","is_join":"1","created":0,"updated":0,"published":"2018-06-21 10:51:12","qrcode":""}]
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
         * id : 3
         * title : 测试
         * start_time : 2018-06-21 10:51:12
         * end_time : 2018-06-21 21:57:52
         * content : 测试
         * type : 有时效
         * is_join : 1
         * created : 0
         * updated : 0
         * published : 2018-10-11 17:17:52
         * qrcode :
         */

        private String id;
        private String title;
        private String start_time;
        private String end_time;
        private String content;
        private String type;
        private String is_join;
        private String created;
        private String updated;
        private String published;
        private String qrcode;

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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }
}
