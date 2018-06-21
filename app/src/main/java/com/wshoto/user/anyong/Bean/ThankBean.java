package com.wshoto.user.anyong.Bean;

import java.util.List;

public class ThankBean {
    /**
     * code : 1
     * message : {"status":"success","data":[{"id":"3","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"},{"id":"4","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"},{"id":"5","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"}]}
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
         * data : [{"id":"3","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"},{"id":"4","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"},{"id":"5","title":"感谢信","template_id":"","author":"","receiver":"5","content":"2","created":"0","published":"0","status":"未读","reply":"正常"}]
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
             * id : 3
             * title : 感谢信
             * template_id :
             * author :
             * receiver : 5
             * content : 2
             * created : 0
             * published : 0
             * status : 未读
             * reply : 正常
             */

            private String id;
            private String title;
            private String template_id;
            private String author;
            private String receiver;
            private String content;
            private String created;
            private String published;
            private String status;
            private String reply;

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

            public String getTemplate_id() {
                return template_id;
            }

            public void setTemplate_id(String template_id) {
                this.template_id = template_id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }
        }
    }
}
