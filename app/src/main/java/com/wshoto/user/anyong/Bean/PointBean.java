package com.wshoto.user.anyong.Bean;

import java.util.List;

public class PointBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"8","integral_log":"转赠好友积分","uid":"5","opt":"-","created":"2018-06-25 18:13:05","value":"1"},{"id":"9","integral_log":"转赠好友积分","uid":"5","opt":"-","created":"2018-06-25 18:14:40","value":"1"}]
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
         * id : 8
         * integral_log : 转赠好友积分
         * uid : 5
         * opt : -
         * created : 2018-06-25 18:13:05
         * value : 1
         */

        private String id;
        private String integral_log;
        private String uid;
        private String opt;
        private String created;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntegral_log() {
            return integral_log;
        }

        public void setIntegral_log(String integral_log) {
            this.integral_log = integral_log;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getOpt() {
            return opt;
        }

        public void setOpt(String opt) {
            this.opt = opt;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
