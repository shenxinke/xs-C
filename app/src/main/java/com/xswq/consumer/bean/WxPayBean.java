package com.xswq.consumer.bean;

public class WxPayBean {


    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"appId":"wx3b1584e17601e9a0","mchId":"1523754391","timeStamp":"1552566983","nonceStr":"V9VEgEPUPC5bwu5P","prepayId":"wx14203621937856d7e512dbe62156530610","signType":"MD5","paySign":"643FB5813EAD4128D4818774D55EBA7A","orderKey":"3F0328891736284CA2952C4D2D025278ADABA800A510D065F86CC1095204BB4660C71781E5B24CD92FB740AC596E05E5"}
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
         * appId : wx3b1584e17601e9a0
         * mchId : 1523754391
         * timeStamp : 1552566983
         * nonceStr : V9VEgEPUPC5bwu5P
         * prepayId : wx14203621937856d7e512dbe62156530610
         * signType : MD5
         * paySign : 643FB5813EAD4128D4818774D55EBA7A
         * orderKey : 3F0328891736284CA2952C4D2D025278ADABA800A510D065F86CC1095204BB4660C71781E5B24CD92FB740AC596E05E5
         */

        private String appId;
        private String mchId;
        private String timeStamp;
        private String nonceStr;
        private String prepayId;
        private String signType;
        private String paySign;
        private String orderKey;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getOrderKey() {
            return orderKey;
        }

        public void setOrderKey(String orderKey) {
            this.orderKey = orderKey;
        }
    }
}
