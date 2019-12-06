package com.xswq.consumer.bean;

import java.io.Serializable;

public class LoginSignInBean implements Serializable {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"tomorrowReward":{"productId":61,"productImg":"http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D","days":3,"productNum":4,"productName":"一滴水"},"specialDaysReward":{"difDays":5,"productId":62,"productImg":"http://file.xswq361.cn/consumer/Product/1552545411349.png?Expires=1867905405&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=fjyAP2uTWpidbwWonTbyeYjAYM0%3D","days":7,"productNum":1,"productName":"矮人国王"},"todayReward":{"productId":61,"productImg":"http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D","days":2,"productNum":2,"productName":"一滴水"}}
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
         * tomorrowReward : {"productId":61,"productImg":"http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D","days":3,"productNum":4,"productName":"一滴水"}
         * specialDaysReward : {"difDays":5,"productId":62,"productImg":"http://file.xswq361.cn/consumer/Product/1552545411349.png?Expires=1867905405&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=fjyAP2uTWpidbwWonTbyeYjAYM0%3D","days":7,"productNum":1,"productName":"矮人国王"}
         * todayReward : {"productId":61,"productImg":"http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D","days":2,"productNum":2,"productName":"一滴水"}
         */

        private TomorrowRewardBean tomorrowReward;
        private SpecialDaysRewardBean specialDaysReward;
        private TodayRewardBean todayReward;

        public TomorrowRewardBean getTomorrowReward() {
            return tomorrowReward;
        }

        public void setTomorrowReward(TomorrowRewardBean tomorrowReward) {
            this.tomorrowReward = tomorrowReward;
        }

        public SpecialDaysRewardBean getSpecialDaysReward() {
            return specialDaysReward;
        }

        public void setSpecialDaysReward(SpecialDaysRewardBean specialDaysReward) {
            this.specialDaysReward = specialDaysReward;
        }

        public TodayRewardBean getTodayReward() {
            return todayReward;
        }

        public void setTodayReward(TodayRewardBean todayReward) {
            this.todayReward = todayReward;
        }

        public static class TomorrowRewardBean {
            /**
             * productId : 61
             * productImg : http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D
             * days : 3
             * productNum : 4
             * productName : 一滴水
             */

            private int productId;
            private String productImg;
            private int days;
            private int productNum;
            private String productName;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }

        public static class SpecialDaysRewardBean {
            /**
             * difDays : 5
             * productId : 62
             * productImg : http://file.xswq361.cn/consumer/Product/1552545411349.png?Expires=1867905405&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=fjyAP2uTWpidbwWonTbyeYjAYM0%3D
             * days : 7
             * productNum : 1
             * productName : 矮人国王
             */

            private int difDays;
            private int productId;
            private String productImg;
            private int days;
            private int productNum;
            private String productName;

            public int getDifDays() {
                return difDays;
            }

            public void setDifDays(int difDays) {
                this.difDays = difDays;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }

        public static class TodayRewardBean {
            /**
             * productId : 61
             * productImg : http://file.xswq361.cn/consumer/Product/1552545269360.png?Expires=1867905266&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=VMmqE1lBpPiLdUQpOcU0zuClMTw%3D
             * days : 2
             * productNum : 2
             * productName : 一滴水
             */

            private int productId;
            private String productImg;
            private int days;
            private int productNum;
            private String productName;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }
    }
}
