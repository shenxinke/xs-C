package com.xswq.consumer.bean;

import java.util.List;

public class CDKeyConver {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : [{"id":1,"productName":"默认棋盘","productValue":0,"productClassify":3,"createTime":1552557967000,"state":0,"productDescription":"","productImg":"http://file.xswq361.cn/consumer/Product/1552557942165.jpg?Expires=1867917942&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=AzeaNIEaAc20EGOVpN9CPWSWxqs%3D","productEffect":"","discountValue":null,"attribute":2,"secondClassify":301,"isGift":0,"showImg":"http://file.xswq361.cn/consumer/Product/1552557962824.jpg?Expires=1867917954&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=aj2fgEPmP9ReDzWjBaganRCj7fM%3D","isRmb":0,"beginTime":null,"endTime":null,"purchaseLimit":null,"productIds":null,"productNums":"2"}]
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
         * id : 1
         * productName : 默认棋盘
         * productValue : 0
         * productClassify : 3
         * createTime : 1552557967000
         * state : 0
         * productDescription :
         * productImg : http://file.xswq361.cn/consumer/Product/1552557942165.jpg?Expires=1867917942&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=AzeaNIEaAc20EGOVpN9CPWSWxqs%3D
         * productEffect :
         * discountValue : null
         * attribute : 2
         * secondClassify : 301
         * isGift : 0
         * showImg : http://file.xswq361.cn/consumer/Product/1552557962824.jpg?Expires=1867917954&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=aj2fgEPmP9ReDzWjBaganRCj7fM%3D
         * isRmb : 0
         * beginTime : null
         * endTime : null
         * purchaseLimit : null
         * productIds : null
         * productNums : 2
         */

        private int id;
        private String productName;
        private int productValue;
        private int productClassify;
        private long createTime;
        private int state;
        private String productDescription;
        private String productImg;
        private String productEffect;
        private Object discountValue;
        private int attribute;
        private int secondClassify;
        private int isGift;
        private String showImg;
        private int isRmb;
        private Object beginTime;
        private Object endTime;
        private Object purchaseLimit;
        private String productIds;
        private String productNums;

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductEffect() {
            return productEffect;
        }

        public void setProductEffect(String productEffect) {
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

        public int getSecondClassify() {
            return secondClassify;
        }

        public void setSecondClassify(int secondClassify) {
            this.secondClassify = secondClassify;
        }

        public int getIsGift() {
            return isGift;
        }

        public void setIsGift(int isGift) {
            this.isGift = isGift;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
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

        public String getProductIds() {
            return productIds;
        }

        public void setProductIds(String productIds) {
            this.productIds = productIds;
        }

        public String getProductNums() {
            return productNums;
        }

        public void setProductNums(String productNums) {
            this.productNums = productNums;
        }
    }
}
