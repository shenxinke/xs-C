package com.xswq.consumer.bean;

public class NewMobileVersionBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"id":6,"versionContent":"汉字&&hello","incrementalUpdating":1,"forcedUpdating":1,"mobileType":2,"version":100}
     */

    private ErrorBean error;
    private DataBean data;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * id : 6
         * versionContent : 汉字&&hello
         * incrementalUpdating : 1
         * forcedUpdating : 1
         * mobileType : 2
         * version : 100
         */

        private int id;
        private String versionContent;
        private int incrementalUpdating;
        private int forcedUpdating;
        private int mobileType;
        private int version;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersionContent() {
            return versionContent;
        }

        public void setVersionContent(String versionContent) {
            this.versionContent = versionContent;
        }

        public int getIncrementalUpdating() {
            return incrementalUpdating;
        }

        public void setIncrementalUpdating(int incrementalUpdating) {
            this.incrementalUpdating = incrementalUpdating;
        }

        public int getForcedUpdating() {
            return forcedUpdating;
        }

        public void setForcedUpdating(int forcedUpdating) {
            this.forcedUpdating = forcedUpdating;
        }

        public int getMobileType() {
            return mobileType;
        }

        public void setMobileType(int mobileType) {
            this.mobileType = mobileType;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
