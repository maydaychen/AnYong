package com.wshoto.user.anyong.Bean;

public class CalendarDetailBean {
    /**
     * code : 1
     * message : {"status":"success"}
     * data : {"id":"50","title":"Outdoor barbecue","province":"江苏","city":"无锡市","start_time":"2018-12-01 00:00:00","end_time":"2018-12-24 00:00:00","thumb":"https://anyong.wshoto.com/uploads/thumbnail/2018121213424960425.jpeg","content":"<p>Outdoor barbecues will be held this Sunday. You can bring your family to participate in the event.<\/p><p>Time: September 9, 2018<\/p>","type":"有时效","is_join":"1","created":"2018-12-12 13:43:04","updated":"2018-12-12 13:43:04","published":"2018-12-12 00:00:00","qrcode":"https://anyong.wshoto.com/uploads/qrcode/2018121213430449081.png","is_today":1}
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
         * id : 50
         * title : Outdoor barbecue
         * province : 江苏
         * city : 无锡市
         * start_time : 2018-12-01 00:00:00
         * end_time : 2018-12-24 00:00:00
         * thumb : https://anyong.wshoto.com/uploads/thumbnail/2018121213424960425.jpeg
         * content : <p>Outdoor barbecues will be held this Sunday. You can bring your family to participate in the event.</p><p>Time: September 9, 2018</p>
         * type : 有时效
         * is_join : 1
         * created : 2018-12-12 13:43:04
         * updated : 2018-12-12 13:43:04
         * published : 2018-12-12 00:00:00
         * qrcode : https://anyong.wshoto.com/uploads/qrcode/2018121213430449081.png
         * is_today : 1
         */

        private String id;
        private String title;
        private String province;
        private String city;
        private String start_time;
        private String end_time;
        private String thumb;
        private String content;
        private String type;
        private String is_join;
        private String created;
        private String updated;
        private String published;
        private String qrcode;
        private int is_today;

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public int getIs_today() {
            return is_today;
        }

        public void setIs_today(int is_today) {
            this.is_today = is_today;
        }
    }
}
