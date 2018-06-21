package com.wshoto.user.anyong.Bean;

import java.util.List;

public class MessageCenterBean {
    /**
     * code : 1
     * message : {"status":"success","data":[{"id":"1","title":"测试","content":"测试","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"},{"id":"2","title":"测试1","content":"测试2","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"},{"id":"3","title":"测试3","content":"测试3","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"}]}
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
         * data : [{"id":"1","title":"测试","content":"测试","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"},{"id":"2","title":"测试1","content":"测试2","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"},{"id":"3","title":"测试3","content":"测试3","created":"1970-01-01 08:00:00","published":"1970-01-01 08:00:00"}]
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
             * title : 测试
             * content : 测试
             * created : 1970-01-01 08:00:00
             * published : 1970-01-01 08:00:00
             */

            private String id;
            private String title;
            private String content;
            private String created;
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

            public String getPublished() {
                return published;
            }

            public void setPublished(String published) {
                this.published = published;
            }
        }
    }
}
