package com.xswq.consumer.bean;

import java.util.List;

public class AnnouncementBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : [{"imgUrl":"http://file.xswq361.cn/consumer/story/img/1553239239975.jpg?Expires=1868599237&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=JsKNpiyB3dMDWwqBGco5sauYl4Q%3D"},{"imgUrl":"http://file.xswq361.cn/consumer/story/img/1553239239975.jpg?Expires=1868599237&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=JsKNpiyB3dMDWwqBGco5sauYl4Q%3D"},{"imgUrl":"http://file.xswq361.cn/consumer/story/img/1553239239975.jpg?Expires=1868599237&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=JsKNpiyB3dMDWwqBGco5sauYl4Q%3D"},{"imgUrl":"http://file.xswq361.cn/consumer/story/img/1553239239975.jpg?Expires=1868599237&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=JsKNpiyB3dMDWwqBGco5sauYl4Q%3D"}]
     */

    private ErrorBean error;
    private List<DataBean> data;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ErrorBean {
        /**
         * returnCode : 0
         * returnMessage : 操作成功
         * returnUserMessage : 操作成功
         * userRanking : null
         * homeTable : null
         */

        private String returnCode;
        private String returnMessage;
        private String returnUserMessage;
        private Object userRanking;
        private Object homeTable;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getReturnUserMessage() {
            return returnUserMessage;
        }

        public void setReturnUserMessage(String returnUserMessage) {
            this.returnUserMessage = returnUserMessage;
        }

        public Object getUserRanking() {
            return userRanking;
        }

        public void setUserRanking(Object userRanking) {
            this.userRanking = userRanking;
        }

        public Object getHomeTable() {
            return homeTable;
        }

        public void setHomeTable(Object homeTable) {
            this.homeTable = homeTable;
        }
    }

    public static class DataBean {
        /**
         * imgUrl : http://file.xswq361.cn/consumer/story/img/1553239239975.jpg?Expires=1868599237&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=JsKNpiyB3dMDWwqBGco5sauYl4Q%3D
         */

        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
