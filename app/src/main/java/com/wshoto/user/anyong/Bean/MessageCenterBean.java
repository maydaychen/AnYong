package com.wshoto.user.anyong.Bean;

import java.util.List;

public class MessageCenterBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"107","title":"感谢信","template_id":"https://anyong.wshoto.com/uploads/thumbnail/2018091016575142356.jpeg","author":"雪花","receiver":"5","content":"333333","created":"2018-11-07 16:56:42","published":"2018-11-07 16:56:45","status":"已读","reply":"正常","thumb":"https://anyong.wshoto.com/uploads/thumbnail/2018091016575142356.jpeg"},{"id":"72","title":"感谢信","template_id":"https://anyong.wshoto.com/uploads/thumbnail/2018091016573217617.jpeg","author":"雪花","receiver":"5","content":"在\n","created":"2018-10-11 17:33:29","published":"2018-10-11 17:33:32","status":"已读","reply":"正常","thumb":"https://anyong.wshoto.com/uploads/20181011173328.png"},{"id":"920","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-07 08:00:02","department":"1","push_type":"0","published":"2018-10-07 08:00:02"},{"id":"919","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-07 08:00:01","department":"1","push_type":"0","published":"2018-10-07 08:00:01"},{"id":"918","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-07 08:00:01","department":"1","push_type":"0","published":"2018-10-07 08:00:01"},{"id":"810","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-06 08:00:02","department":"1","push_type":"0","published":"2018-10-06 08:00:02"},{"id":"809","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-06 08:00:01","department":"1","push_type":"0","published":"2018-10-06 08:00:01"},{"id":"808","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-06 08:00:01","department":"1","push_type":"0","published":"2018-10-06 08:00:01"},{"id":"700","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-05 08:00:02","department":"1","push_type":"0","published":"2018-10-05 08:00:02"},{"id":"698","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-05 08:00:02","department":"1","push_type":"0","published":"2018-10-05 08:00:02"},{"id":"699","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-05 08:00:02","department":"1","push_type":"0","published":"2018-10-05 08:00:02"},{"id":"590","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-04 08:00:03","department":"1","push_type":"0","published":"2018-10-04 08:00:03"},{"id":"589","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-04 08:00:02","department":"1","push_type":"0","published":"2018-10-04 08:00:02"},{"id":"588","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-04 08:00:02","department":"1","push_type":"0","published":"2018-10-04 08:00:02"},{"id":"478","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-03 08:00:01","department":"1","push_type":"0","published":"2018-10-03 08:00:01"},{"id":"480","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-03 08:00:01","department":"1","push_type":"0","published":"2018-10-03 08:00:01"},{"id":"479","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-03 08:00:01","department":"1","push_type":"0","published":"2018-10-03 08:00:01"},{"id":"368","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-02 08:00:02","department":"1","push_type":"0","published":"2018-10-02 08:00:02"},{"id":"370","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-02 08:00:02","department":"1","push_type":"0","published":"2018-10-02 08:00:02"},{"id":"369","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-02 08:00:02","department":"1","push_type":"0","published":"2018-10-02 08:00:02"},{"id":"258","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-01 08:00:02","department":"1","push_type":"0","published":"2018-10-01 08:00:02"},{"id":"260","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-01 08:00:02","department":"1","push_type":"0","published":"2018-10-01 08:00:02"},{"id":"259","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-10-01 08:00:02","department":"1","push_type":"0","published":"2018-10-01 08:00:02"},{"id":"148","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-30 08:00:02","department":"1","push_type":"0","published":"2018-09-30 08:00:02"},{"id":"150","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-30 08:00:02","department":"1","push_type":"0","published":"2018-09-30 08:00:02"},{"id":"149","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-30 08:00:02","department":"1","push_type":"0","published":"2018-09-30 08:00:02"},{"id":"40","title":"生日快乐","receiver":"jon","job_no":"123454","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-29 08:00:02","department":"1","push_type":"0","published":"2018-09-29 08:00:02"},{"id":"38","title":"生日快乐","receiver":"bob","job_no":"123456","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-29 08:00:02","department":"1","push_type":"0","published":"2018-09-29 08:00:02"},{"id":"39","title":"生日快乐","receiver":"tom","job_no":"123455","position":"Account","content":"今天是您的生日，祝您生日快乐~！","created":"2018-09-29 08:00:02","department":"1","push_type":"0","published":"2018-09-29 08:00:02"},{"id":"33","title":"a","receiver":"","job_no":"123455","position":"Account","content":"b","created":"2018-09-18 15:52:00","department":"1","push_type":"0","published":"2018-09-18 15:52:00"},{"id":"29","title":"111","receiver":"","job_no":"1","position":"1","content":"111","created":"2018-09-13 21:38:00","department":"1","push_type":"1","published":"2018-09-13 21:38:00"},{"id":"13","title":"今日天气","receiver":"","job_no":"","position":"","content":"天气：阴。出门请带伞。","created":"2018-09-13 14:32:00","department":"1","push_type":"0","published":"2018-09-13 14:32:00"},{"id":"12","title":"1","receiver":"","job_no":"","position":"","content":"2","created":"2018-09-10 09:29:00","department":"1","push_type":"0","published":"2018-09-10 09:29:00"},{"id":"11","title":"1231","receiver":"","job_no":"123454","position":"Account","content":"123","created":"2018-09-09 13:32:00","department":"1","push_type":"0","published":"2018-09-09 13:32:00"},{"id":"4","title":"感谢信","template_id":"https://anyong.wshoto.com/uploads/thumbnail/2018091016573217617.jpeg","author":"bob","receiver":"5","content":"2","created":"0","published":"2018-06-24 15:26:40","status":"未读","reply":"正常","thumb":""}]
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
         * id : 107
         * title : 感谢信
         * template_id : https://anyong.wshoto.com/uploads/thumbnail/2018091016575142356.jpeg
         * author : 雪花
         * receiver : 5
         * content : 333333
         * created : 2018-11-07 16:56:42
         * published : 2018-11-07 16:56:45
         * status : 已读
         * reply : 正常
         * thumb : https://anyong.wshoto.com/uploads/thumbnail/2018091016575142356.jpeg
         * job_no : 123454
         * position : Account
         * department : 1
         * push_type : 0
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
        private String thumb;
        private String job_no;
        private String position;
        private String department;
        private String push_type;

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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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
    }
}
