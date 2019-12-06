package com.xswq.consumer.bean;

public class LevelInformationBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"userQuestionRecord":12,"loginDay":3,"playSum":0,"star":0,"level":0,"maxGateNum":1,"playAccuracy":"0.0%","way19Accuracy":"0.0%","accuracy":"91.67%","way9Accuracy":"0.0%","way9":0,"way19":0}
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
         * userQuestionRecord : 12
         * loginDay : 3
         * playSum : 0
         * star : 0
         * level : 0
         * maxGateNum : 1
         * playAccuracy : 0.0%
         * way19Accuracy : 0.0%
         * accuracy : 91.67%
         * way9Accuracy : 0.0%
         * way9 : 0
         * way19 : 0
         */

        private int userQuestionRecord;
        private int loginDay;
        private int playSum;
        private int star;
        private int level;
        private int maxGateNum;
        private String playAccuracy;
        private String way19Accuracy;
        private String accuracy;
        private String way9Accuracy;
        private int way9;
        private int way19;

        public int getUserQuestionRecord() {
            return userQuestionRecord;
        }

        public void setUserQuestionRecord(int userQuestionRecord) {
            this.userQuestionRecord = userQuestionRecord;
        }

        public int getLoginDay() {
            return loginDay;
        }

        public void setLoginDay(int loginDay) {
            this.loginDay = loginDay;
        }

        public int getPlaySum() {
            return playSum;
        }

        public void setPlaySum(int playSum) {
            this.playSum = playSum;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getMaxGateNum() {
            return maxGateNum;
        }

        public void setMaxGateNum(int maxGateNum) {
            this.maxGateNum = maxGateNum;
        }

        public String getPlayAccuracy() {
            return playAccuracy;
        }

        public void setPlayAccuracy(String playAccuracy) {
            this.playAccuracy = playAccuracy;
        }

        public String getWay19Accuracy() {
            return way19Accuracy;
        }

        public void setWay19Accuracy(String way19Accuracy) {
            this.way19Accuracy = way19Accuracy;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getWay9Accuracy() {
            return way9Accuracy;
        }

        public void setWay9Accuracy(String way9Accuracy) {
            this.way9Accuracy = way9Accuracy;
        }

        public int getWay9() {
            return way9;
        }

        public void setWay9(int way9) {
            this.way9 = way9;
        }

        public int getWay19() {
            return way19;
        }

        public void setWay19(int way19) {
            this.way19 = way19;
        }
    }
}
