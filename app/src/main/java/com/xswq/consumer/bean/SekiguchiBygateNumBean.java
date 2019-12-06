package com.xswq.consumer.bean;

public class SekiguchiBygateNumBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"maxTime":{"id":0,"userId":2224,"gateNum":0,"seat":0,"testScore":null,"questionId":null,"createTime":null,"rightQuestion":null,"wrongQuestion":null}}
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
         * maxTime : {"id":0,"userId":2224,"gateNum":0,"seat":0,"testScore":null,"questionId":null,"createTime":null,"rightQuestion":null,"wrongQuestion":null}
         */

        private MaxTimeBean maxTime;

        public MaxTimeBean getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(MaxTimeBean maxTime) {
            this.maxTime = maxTime;
        }

        public static class MaxTimeBean {
            /**
             * id : 0
             * userId : 2224
             * gateNum : 0
             * seat : 0
             * testScore : null
             * questionId : null
             * createTime : null
             * rightQuestion : null
             * wrongQuestion : null
             */

            private int id;
            private int userId;
            private int gateNum;
            private int seat;
            private Object testScore;
            private Object questionId;
            private Object createTime;
            private Object rightQuestion;
            private Object wrongQuestion;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getGateNum() {
                return gateNum;
            }

            public void setGateNum(int gateNum) {
                this.gateNum = gateNum;
            }

            public int getSeat() {
                return seat;
            }

            public void setSeat(int seat) {
                this.seat = seat;
            }

            public Object getTestScore() {
                return testScore;
            }

            public void setTestScore(Object testScore) {
                this.testScore = testScore;
            }

            public Object getQuestionId() {
                return questionId;
            }

            public void setQuestionId(Object questionId) {
                this.questionId = questionId;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getRightQuestion() {
                return rightQuestion;
            }

            public void setRightQuestion(Object rightQuestion) {
                this.rightQuestion = rightQuestion;
            }

            public Object getWrongQuestion() {
                return wrongQuestion;
            }

            public void setWrongQuestion(Object wrongQuestion) {
                this.wrongQuestion = wrongQuestion;
            }
        }
    }
}
