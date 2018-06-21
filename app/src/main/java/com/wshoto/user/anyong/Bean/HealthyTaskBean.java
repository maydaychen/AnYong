package com.wshoto.user.anyong.Bean;

import java.util.List;

public class HealthyTaskBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"1","start":"2018-06-10 10:21:11","end":"2018-06-10 10:21:11","title":"眼保健操","value":"0","is_done":0},{"id":"2","start":"2018-06-10 10:21:11","end":"2018-06-10 10:21:11","title":"颈椎操","value":"0","is_done":0}]
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
         * id : 1
         * start : 2018-06-10 10:21:11
         * end : 2018-06-10 10:21:11
         * title : 眼保健操
         * value : 0
         * is_done : 0
         */

        private String id;
        private String start;
        private String end;
        private String title;
        private String value;
        private int is_done;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIs_done() {
            return is_done;
        }

        public void setIs_done(int is_done) {
            this.is_done = is_done;
        }
    }
}
