package com.xswq.consumer.bean;

import java.util.List;

public class InvitationBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"totalCount":0,"shareList":[{"id":0,"count":1,"state":0,"gold":10},{"id":0,"count":5,"state":0,"gold":80},{"id":0,"count":10,"state":0,"gold":150},{"id":0,"count":20,"state":0,"gold":200}]}
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
         * totalCount : 0
         * shareList : [{"id":0,"count":1,"state":0,"gold":10},{"id":0,"count":5,"state":0,"gold":80},{"id":0,"count":10,"state":0,"gold":150},{"id":0,"count":20,"state":0,"gold":200}]
         */

        private String totalCount;
        private List<ShareListBean> shareList;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public List<ShareListBean> getShareList() {
            return shareList;
        }

        public void setShareList(List<ShareListBean> shareList) {
            this.shareList = shareList;
        }

        public static class ShareListBean {
            /**
             * id : 0
             * count : 1
             * state : 0
             * gold : 10
             */

            private String id;
            private int count;
            private int state;
            private String gold;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }
        }
    }
}
