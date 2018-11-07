package com.wshoto.user.anyong.Bean;

import java.util.List;

public class ThankThemeBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"1","template_name":"Test3","template_desc":"We make the world work better in a million different ways. Share just one by nominating the individual / team that inspires you for EY\u2019s 2018 Better begins with you, global employee award program: giving bold actions the attention they deserve!","template_path":"https://anyong.wshoto.com/uploads/thumbnail/2018091016573217617.jpeg","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"2","template_name":"Test 2","template_desc":"We\u2019re always trying to make the working world better as a team. Just like the World Cup, our teammates make great contributions to our own achievements. Cheer on a teammate who strives with you, recognize a coach who guides you, or show your admiration for a team you are inspired by.","template_path":"https://anyong.wshoto.com/uploads/thumbnail/2018091016571092576.jpeg","created":"1970-01-01 08:00:00","is_available":"1"},{"id":"4","template_name":"Test1","template_desc":"We are like dwarfs sitting on the shoulders of giants. We see more, and things that are more distant, than they did, not because our sight is superior or because we are taller than they, but because they raise us up, and by their great stature add to ours. \r\n                            ----  John of Salisbury, Metalogicon Circa 1159","template_path":"https://anyong.wshoto.com/uploads/thumbnail/2018091016575142356.jpeg","created":"2018-07-11 20:18:22","is_available":"1"}]
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
         * template_name : Test3
         * template_desc : We make the world work better in a million different ways. Share just one by nominating the individual / team that inspires you for EY’s 2018 Better begins with you, global employee award program: giving bold actions the attention they deserve!
         * template_path : https://anyong.wshoto.com/uploads/thumbnail/2018091016573217617.jpeg
         * created : 1970-01-01 08:00:00
         * is_available : 1
         */

        private String id;
        private String template_name;
        private String template_desc;
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

        public String getTemplate_desc() {
            return template_desc;
        }

        public void setTemplate_desc(String template_desc) {
            this.template_desc = template_desc;
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
