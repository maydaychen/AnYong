package com.wshoto.user.anyong.Bean;

import java.util.List;

public class ThankBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : [{"id":"650","title":"感谢信","template_id":"1","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550918805","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"317","title":"感谢信","template_id":"0","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550023836","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"316","title":"感谢信","template_id":"0","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550022843","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"315","title":"感谢信","template_id":"0","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550022122","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"314","title":"感谢信","template_id":"0","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550021952","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"313","title":"感谢信","template_id":"0","author":"206","receiver":"13332","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1550021349","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190213092820.png","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"309","title":"感谢信","template_id":"0","author":"206","receiver":"13360","content":"千里之行，积于跬步；万里之船，成于罗盘。感谢您平日的指导和帮助，是您昨日的提携成就了今天的自我。祝您拥有美好的一天！","created":"1549966024","published":"0","status":"1","reply":"0","thumb":"https://anyong.wshoto.com/uploads/20190212180701.png","avatar":"https://anyong.wshoto.com/uploads/20180625164129.png"},{"id":"308","title":"感谢信","template_id":"0","author":"206","receiver":"13360","content":"千里之行，积于跬步；万里之船，成于罗盘。感谢您平日的指导和帮助，是您昨日的提携成就了今天的自我。祝您拥有美好的一天！","created":"1549965843","published":"0","status":"1","reply":"0","thumb":"","avatar":"https://anyong.wshoto.com/uploads/20180625164129.png"},{"id":"178","title":"感谢信","template_id":"2","author":"206","receiver":"13332","content":"test","created":"1549000897","published":"0","status":"1","reply":"0","thumb":"","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"175","title":"感谢信","template_id":"2","author":"206","receiver":"13332","content":"千里之行，积于跬步；万里之船，成于罗盘。感谢您平日的指导和帮助，是您昨日的提携成就了今天的自我。祝您拥有美好的一天！","created":"1549000839","published":"0","status":"1","reply":"0","thumb":"","avatar":"https://anyong.wshoto.com/uploads/20190129153037.png"},{"id":"79","title":"感谢信","template_id":"1","author":"206","receiver":"7","content":"谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。","created":"1547795057","published":"1547795062","status":"1","reply":"0","thumb":"","avatar":null}]
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
         * id : 650
         * title : 感谢信
         * template_id : 1
         * author : 206
         * receiver : 13332
         * content : 谢谢你的努力和付出，是你对工作的热情和投入让我们追求更卓越的安永，创造更美好的明天。
         * created : 1550918805
         * published : 0
         * status : 1
         * reply : 0
         * thumb : https://anyong.wshoto.com/uploads/20190213092820.png
         * avatar : https://anyong.wshoto.com/uploads/20190129153037.png
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
        private String avatar;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
