package com.xswq.consumer.bean;

import java.util.List;

public class UnAchuevementListBean {


    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : [{"id":0,"productName":"一碗水","productValue":0,"productClassify":0,"createTime":null,"state":0,"productDescription":null,"productImg":"http://file.xswq361.cn/consumer/Product/1552544570830.png?Expires=1867904563&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=Dmp0HbcuJmLUJg%2Fjo8eo1l3o6Bo%3D","productEffect":null,"discountValue":null,"attribute":0,"secondClassify":null,"isGift":0,"showImg":null,"isRmb":0,"beginTime":null,"endTime":null,"purchaseLimit":null,"productIds":null,"productNums":null,"backPackId":0,"userId":0,"productId":52,"productNum":1}]
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
         * id : 0
         * productName : 一碗水
         * productValue : 0
         * productClassify : 0
         * createTime : null
         * state : 0
         * productDescription : null
         * productImg : http://file.xswq361.cn/consumer/Product/1552544570830.png?Expires=1867904563&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=Dmp0HbcuJmLUJg%2Fjo8eo1l3o6Bo%3D
         * productEffect : null
         * discountValue : null
         * attribute : 0
         * secondClassify : null
         * isGift : 0
         * showImg : null
         * isRmb : 0
         * beginTime : null
         * endTime : null
         * purchaseLimit : null
         * productIds : null
         * productNums : null
         * backPackId : 0
         * userId : 0
         * productId : 52
         * productNum : 1
         */

        private int id;
        private String productName;
        private int productValue;
        private int productClassify;
        private Object createTime;
        private int state;
        private Object productDescription;
        private String productImg;
        private Object productEffect;
        private Object discountValue;
        private int attribute;
        private Object secondClassify;
        private int isGift;
        private Object showImg;
        private int isRmb;
        private Object beginTime;
        private Object endTime;
        private Object purchaseLimit;
        private Object productIds;
        private Object productNums;
        private int backPackId;
        private int userId;
        private int productId;
        private int productNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductValue() {
            return productValue;
        }

        public void setProductValue(int productValue) {
            this.productValue = productValue;
        }

        public int getProductClassify() {
            return productClassify;
        }

        public void setProductClassify(int productClassify) {
            this.productClassify = productClassify;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(Object productDescription) {
            this.productDescription = productDescription;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public Object getProductEffect() {
            return productEffect;
        }

        public void setProductEffect(Object productEffect) {
            this.productEffect = productEffect;
        }

        public Object getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(Object discountValue) {
            this.discountValue = discountValue;
        }

        public int getAttribute() {
            return attribute;
        }

        public void setAttribute(int attribute) {
            this.attribute = attribute;
        }

        public Object getSecondClassify() {
            return secondClassify;
        }

        public void setSecondClassify(Object secondClassify) {
            this.secondClassify = secondClassify;
        }

        public int getIsGift() {
            return isGift;
        }

        public void setIsGift(int isGift) {
            this.isGift = isGift;
        }

        public Object getShowImg() {
            return showImg;
        }

        public void setShowImg(Object showImg) {
            this.showImg = showImg;
        }

        public int getIsRmb() {
            return isRmb;
        }

        public void setIsRmb(int isRmb) {
            this.isRmb = isRmb;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public Object getPurchaseLimit() {
            return purchaseLimit;
        }

        public void setPurchaseLimit(Object purchaseLimit) {
            this.purchaseLimit = purchaseLimit;
        }

        public Object getProductIds() {
            return productIds;
        }

        public void setProductIds(Object productIds) {
            this.productIds = productIds;
        }

        public Object getProductNums() {
            return productNums;
        }

        public void setProductNums(Object productNums) {
            this.productNums = productNums;
        }

        public int getBackPackId() {
            return backPackId;
        }

        public void setBackPackId(int backPackId) {
            this.backPackId = backPackId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }
    }
}
