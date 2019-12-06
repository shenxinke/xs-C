package com.xswq.consumer.bean;

import java.util.List;

public class MyChessManualBean {


    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"total":2,"list":[{"WhiteUserId":2255,"BlackUserName":"","WhiteUserName":"邹珍琳","EndTime":"2019-03-15 17:11:16","WhiteHead":"img/headImg/1.png","PlayResult":1,"BlackUserId":2209,"BlackHead":"img/headImg/1.png","ID":77278,"GameType":3,"evaluateState":0,"chessId":8461},{"WhiteUserId":2248,"BlackUserName":"","WhiteUserName":"何克信","EndTime":"2019-03-15 17:05:56","WhiteHead":"img/headImg/1.png","PlayResult":4,"BlackUserId":2209,"BlackHead":"img/headImg/1.png","ID":77277,"GameType":3,"evaluateState":0,"chessId":8459}],"pageNum":1,"pageSize":10,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
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
         * total : 2
         * list : [{"WhiteUserId":2255,"BlackUserName":"","WhiteUserName":"邹珍琳","EndTime":"2019-03-15 17:11:16","WhiteHead":"img/headImg/1.png","PlayResult":1,"BlackUserId":2209,"BlackHead":"img/headImg/1.png","ID":77278,"GameType":3,"evaluateState":0,"chessId":8461},{"WhiteUserId":2248,"BlackUserName":"","WhiteUserName":"何克信","EndTime":"2019-03-15 17:05:56","WhiteHead":"img/headImg/1.png","PlayResult":4,"BlackUserId":2209,"BlackHead":"img/headImg/1.png","ID":77277,"GameType":3,"evaluateState":0,"chessId":8459}]
         * pageNum : 1
         * pageSize : 10
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
             * WhiteUserId : 2255
             * BlackUserName :
             * WhiteUserName : 邹珍琳
             * EndTime : 2019-03-15 17:11:16
             * WhiteHead : img/headImg/1.png
             * PlayResult : 1
             * BlackUserId : 2209
             * BlackHead : img/headImg/1.png
             * ID : 77278
             * GameType : 3
             * evaluateState : 0
             * chessId : 8461
             */

            private int WhiteUserId;
            private String BlackUserName;
            private String WhiteUserName;
            private long EndTime;
            private String WhiteHead;
            private int PlayResult;
            private int BlackUserId;
            private String BlackHead;
            private int ID;
            private int GameType;
            private int evaluateState;
            private int chessId;

            public int getWhiteUserId() {
                return WhiteUserId;
            }

            public void setWhiteUserId(int WhiteUserId) {
                this.WhiteUserId = WhiteUserId;
            }

            public String getBlackUserName() {
                return BlackUserName;
            }

            public void setBlackUserName(String BlackUserName) {
                this.BlackUserName = BlackUserName;
            }

            public String getWhiteUserName() {
                return WhiteUserName;
            }

            public void setWhiteUserName(String WhiteUserName) {
                this.WhiteUserName = WhiteUserName;
            }

            public long getEndTime() {
                return EndTime;
            }

            public void setEndTime(long EndTime) {
                this.EndTime = EndTime;
            }

            public String getWhiteHead() {
                return WhiteHead;
            }

            public void setWhiteHead(String WhiteHead) {
                this.WhiteHead = WhiteHead;
            }

            public int getPlayResult() {
                return PlayResult;
            }

            public void setPlayResult(int PlayResult) {
                this.PlayResult = PlayResult;
            }

            public int getBlackUserId() {
                return BlackUserId;
            }

            public void setBlackUserId(int BlackUserId) {
                this.BlackUserId = BlackUserId;
            }

            public String getBlackHead() {
                return BlackHead;
            }

            public void setBlackHead(String BlackHead) {
                this.BlackHead = BlackHead;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getGameType() {
                return GameType;
            }

            public void setGameType(int GameType) {
                this.GameType = GameType;
            }

            public int getEvaluateState() {
                return evaluateState;
            }

            public void setEvaluateState(int evaluateState) {
                this.evaluateState = evaluateState;
            }

            public int getChessId() {
                return chessId;
            }

            public void setChessId(int chessId) {
                this.chessId = chessId;
            }
        }
    }
}
