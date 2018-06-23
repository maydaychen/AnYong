package com.wshoto.user.anyong.Bean;

import java.util.List;

public class CalendarMineBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"3","activity_id":"3","uid":"5","created":"1970-01-01 08:00:00","title":"测试","start_time":"2018-06-21 10:51:12"}]
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
         * activity_id : 3
         * uid : 5
         * created : 1970-01-01 08:00:00
         * title : 测试
         * start_time : 2018-06-21 10:51:12
         */

        private String id;
        private String activity_id;
        private String uid;
        private String created;
        private String title;
        private String start_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
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
    }
}
