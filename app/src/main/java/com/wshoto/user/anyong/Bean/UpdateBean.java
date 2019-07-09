package com.wshoto.user.anyong.Bean;

public class UpdateBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"id":"1","key":"hat","value":"https://anyong.wshoto.com/uploads/thumbnail/2019021217064116313.png","vandroid":"1.0.0","vios":"1.0.0"}
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
         * id : 1
         * key : hat
         * value : https://anyong.wshoto.com/uploads/thumbnail/2019021217064116313.png
         * vandroid : 1.0.0
         * vios : 1.0.0
         */

        private String id;
        private String key;
        private String value;
        private String vandroid;
        private String vios;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getVandroid() {
            return vandroid;
        }

        public void setVandroid(String vandroid) {
            this.vandroid = vandroid;
        }

        public String getVios() {
            return vios;
        }

        public void setVios(String vios) {
            this.vios = vios;
        }
    }
}
