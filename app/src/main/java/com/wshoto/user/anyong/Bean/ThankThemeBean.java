package com.wshoto.user.anyong.Bean;

import java.util.List;

public class ThankThemeBean {
    /**
     * code : 1
     * message : {"status":"success","data":[{"id":"1","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"2","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"3","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"}]}
     */

    private int code;
    private MessageBean message;

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

    public static class MessageBean {
        /**
         * status : success
         * data : [{"id":"1","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"2","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"3","template_name":"测试","template_path":"http://www.baidu.com","created":"1970-01-01 08:00:00","is_available":"1"}]
         */

        private String status;
        private List<DataBean> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * template_name : 测试
             * template_path : http://www.baidu.com
             * created : 1970-01-01 08:00:00
             * is_available : 1
             */

            private String id;
            private String template_name;
            private String template_path;
            private String created;
            private String is_available;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTemplate_name() {
                return template_name;
            }

            public void setTemplate_name(String template_name) {
                this.template_name = template_name;
            }

            public String getTemplate_path() {
                return template_path;
            }

            public void setTemplate_path(String template_path) {
                this.template_path = template_path;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getIs_available() {
                return is_available;
            }

            public void setIs_available(String is_available) {
                this.is_available = is_available;
            }
        }
    }
}
