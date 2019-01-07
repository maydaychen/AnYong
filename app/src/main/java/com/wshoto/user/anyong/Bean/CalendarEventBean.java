package com.wshoto.user.anyong.Bean;

import java.util.List;

public class CalendarEventBean {
    /**
     * code : 1
     * message : {"status":""}
     * timedata : ["",""]
     */

    private int code;
    private MessageBean message;
    private List<String> timedata;

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

    public List<String> getTimedata() {
        return timedata;
    }

    public void setTimedata(List<String> timedata) {
        this.timedata = timedata;
    }

    public static class MessageBean {
        /**
         * status :
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
