package com.xswq.consumer.bean;

import java.util.List;

public class FeedBackInfoBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : [{"id":1,"desciption":"问题1","type":1,"answerA":"答案1","answerB":"答案2","answerC":"答案3","answerD":"答案4","state":1},{"id":2,"desciption":"问题2","type":2,"answerA":"答案1","answerB":"答案2","answerC":"答案3","answerD":"答案4","state":1},{"id":3,"desciption":"问题3","type":3,"answerA":null,"answerB":null,"answerC":null,"answerD":null,"state":1}]
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
        private int mustStage;

        public int getMustStage() {
            return mustStage;
        }

        public void setMustStage(int mustStage) {
            this.mustStage = mustStage;
        }

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
         * id : 1
         * desciption : 问题1
         * type : 1
         * answerA : 答案1
         * answerB : 答案2
         * answerC : 答案3
         * answerD : 答案4
         * state : 1
         */

        private String id;
        private String desciption;
        private int type;
        private String answerA;
        private String answerB;
        private String answerC;
        private String answerD;
        private int mustStage;

        public int getMustStage() {
            return mustStage;
        }

        public void setMustStage(int mustStage) {
            this.mustStage = mustStage;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDesciption() {
            return desciption;
        }

        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAnswerA() {
            return answerA;
        }

        public void setAnswerA(String answerA) {
            this.answerA = answerA;
        }

        public String getAnswerB() {
            return answerB;
        }

        public void setAnswerB(String answerB) {
            this.answerB = answerB;
        }

        public String getAnswerC() {
            return answerC;
        }

        public void setAnswerC(String answerC) {
            this.answerC = answerC;
        }

        public String getAnswerD() {
            return answerD;
        }

        public void setAnswerD(String answerD) {
            this.answerD = answerD;
        }


    }
}
