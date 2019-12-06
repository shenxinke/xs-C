package com.xswq.consumer.bean;

import java.util.List;

public class SekiguchListBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"maxSekiguchi":{"id":35,"userId":2224,"gateNum":1,"seat":2,"testScore":null,"questionId":null,"createTime":1551405620000,"rightQuestion":null,"wrongQuestion":null},"maxBuy":2,"gateAll":{"total":2,"list":[{"id":1,"gateNum":1,"gateName":"第一关","storyId":1,"gameId":"100101,100102","videoId":1,"testQuestionId":"21176,21177,21178,21181,21182,21183","questionId":"21176,21177,21178,21181,21182,21183","createUser":1516,"createTime":1550558777000,"waterDropNum":20},{"id":2,"gateNum":2,"gateName":"第二关","storyId":2,"gameId":"100103,100104","videoId":2,"testQuestionId":"21179,21180","questionId":"55555,66666","createUser":1516,"createTime":1550558961000,"waterDropNum":30}],"pageNum":1,"pageSize":12,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
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
         * maxSekiguchi : {"id":35,"userId":2224,"gateNum":1,"seat":2,"testScore":null,"questionId":null,"createTime":1551405620000,"rightQuestion":null,"wrongQuestion":null}
         * maxBuy : 2
         * gateAll : {"total":2,"list":[{"id":1,"gateNum":1,"gateName":"第一关","storyId":1,"gameId":"100101,100102","videoId":1,"testQuestionId":"21176,21177,21178,21181,21182,21183","questionId":"21176,21177,21178,21181,21182,21183","createUser":1516,"createTime":1550558777000,"waterDropNum":20},{"id":2,"gateNum":2,"gateName":"第二关","storyId":2,"gameId":"100103,100104","videoId":2,"testQuestionId":"21179,21180","questionId":"55555,66666","createUser":1516,"createTime":1550558961000,"waterDropNum":30}],"pageNum":1,"pageSize":12,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
         */

        private MaxSekiguchiBean maxSekiguchi;
        private int maxBuy;
        private GateAllBean gateAll;

        public MaxSekiguchiBean getMaxSekiguchi() {
            return maxSekiguchi;
        }

        public void setMaxSekiguchi(MaxSekiguchiBean maxSekiguchi) {
            this.maxSekiguchi = maxSekiguchi;
        }

        public int getMaxBuy() {
            return maxBuy;
        }

        public void setMaxBuy(int maxBuy) {
            this.maxBuy = maxBuy;
        }

        public GateAllBean getGateAll() {
            return gateAll;
        }

        public void setGateAll(GateAllBean gateAll) {
            this.gateAll = gateAll;
        }

        public static class MaxSekiguchiBean {
            /**
             * id : 35
             * userId : 2224
             * gateNum : 1
             * seat : 2
             * testScore : null
             * questionId : null
             * createTime : 1551405620000
             * rightQuestion : null
             * wrongQuestion : null
             */

            private int id;
            private int userId;
            private int gateNum;
            private int seat;
            private Object testScore;
            private Object questionId;
            private long createTime;
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

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
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

        public static class GateAllBean {
            /**
             * total : 2
             * list : [{"id":1,"gateNum":1,"gateName":"第一关","storyId":1,"gameId":"100101,100102","videoId":1,"testQuestionId":"21176,21177,21178,21181,21182,21183","questionId":"21176,21177,21178,21181,21182,21183","createUser":1516,"createTime":1550558777000,"waterDropNum":20},{"id":2,"gateNum":2,"gateName":"第二关","storyId":2,"gameId":"100103,100104","videoId":2,"testQuestionId":"21179,21180","questionId":"55555,66666","createUser":1516,"createTime":1550558961000,"waterDropNum":30}]
             * pageNum : 1
             * pageSize : 12
             * size : 2
             * startRow : 1
             * endRow : 2
             * pages : 1
             * prePage : 0
             * nextPage : 0
             * isFirstPage : true
             * isLastPage : true
             * hasPreviousPage : false
             * hasNextPage : false
             * navigatePages : 8
             * navigatepageNums : [1]
             * navigateFirstPage : 1
             * navigateLastPage : 1
             * firstPage : 1
             * lastPage : 1
             */

            private int total;
            private int pageNum;
            private int pageSize;
            private int size;
            private int startRow;
            private int endRow;
            private int pages;
            private int prePage;
            private int nextPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private boolean hasPreviousPage;
            private boolean hasNextPage;
            private int navigatePages;
            private int navigateFirstPage;
            private int navigateLastPage;
            private int firstPage;
            private int lastPage;
            private List<ListBean> list;
            private List<Integer> navigatepageNums;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public int getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(int navigatePages) {
                this.navigatePages = navigatePages;
            }

            public int getNavigateFirstPage() {
                return navigateFirstPage;
            }

            public void setNavigateFirstPage(int navigateFirstPage) {
                this.navigateFirstPage = navigateFirstPage;
            }

            public int getNavigateLastPage() {
                return navigateLastPage;
            }

            public void setNavigateLastPage(int navigateLastPage) {
                this.navigateLastPage = navigateLastPage;
            }

            public int getFirstPage() {
                return firstPage;
            }

            public void setFirstPage(int firstPage) {
                this.firstPage = firstPage;
            }

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }

            public static class ListBean {
                /**
                 * id : 1
                 * gateNum : 1
                 * gateName : 第一关
                 * storyId : 1
                 * gameId : 100101,100102
                 * videoId : 1
                 * testQuestionId : 21176,21177,21178,21181,21182,21183
                 * questionId : 21176,21177,21178,21181,21182,21183
                 * createUser : 1516
                 * createTime : 1550558777000
                 * waterDropNum : 20
                 */

                private int id;
                private String gateNum;
                private String gateName;
                private String storyId;
                private String gameId;
                private String videoId;
                private String testQuestionId;
                private String questionId;
                private int createUser;
                private long createTime;
                private int waterDropNum;
                private String score;
                private String errorQuestionType;

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getGateNum() {
                    return gateNum;
                }

                public void setGateNum(String gateNum) {
                    this.gateNum = gateNum;
                }

                public String getGateName() {
                    return gateName;
                }

                public void setGateName(String gateName) {
                    this.gateName = gateName;
                }

                public String getStoryId() {
                    return storyId;
                }

                public void setStoryId(String storyId) {
                    this.storyId = storyId;
                }

                public String getGameId() {
                    return gameId;
                }

                public void setGameId(String gameId) {
                    this.gameId = gameId;
                }

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public String getTestQuestionId() {
                    return testQuestionId;
                }

                public void setTestQuestionId(String testQuestionId) {
                    this.testQuestionId = testQuestionId;
                }

                public String getErrorQuestionType() {
                    return errorQuestionType;
                }

                public void setErrorQuestionType(String errorQuestionType) {
                    this.errorQuestionType = errorQuestionType;
                }

                public String getQuestionId() {
                    return questionId;
                }

                public void setQuestionId(String questionId) {
                    this.questionId = questionId;
                }

                public int getCreateUser() {
                    return createUser;
                }

                public void setCreateUser(int createUser) {
                    this.createUser = createUser;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getWaterDropNum() {
                    return waterDropNum;
                }

                public void setWaterDropNum(int waterDropNum) {
                    this.waterDropNum = waterDropNum;
                }
            }
        }
    }
}
