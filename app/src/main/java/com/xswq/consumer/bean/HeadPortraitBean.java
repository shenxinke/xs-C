package com.xswq.consumer.bean;

import java.util.List;

public class HeadPortraitBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : [{"belong":1,"productImg":"9.png","ID":31,"productName":"头像2"},{"belong":0,"productImg":"3.png","ID":24,"productName":"头像1"},{"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552355501473.png?Expires=1867715491&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=LIei3ER6bQxyuJdzTjy3XqPENhM%3D","ID":34,"productName":"11"},{"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552357160103.png?Expires=1867717155&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=7IxaX%2FdxvVvsKQTDLAHI226lNOw%3D","ID":36,"productName":"测试商品1带礼包","showImg":""},{"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552358993905.png?Expires=1867718986&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=vfmrEZYBMo%2FmHavxryV5w8ui2d0%3D","ID":37,"productName":"测试商品2带礼包","showImg":""},{"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359588705.png?Expires=1867719579&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=lmseAmXbBd0BGJ6K0rQq56bz0kg%3D","ID":38,"productName":"哈哈","showImg":""},{"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359889968.png?Expires=1867719886&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=sKp6JPox9LSDjQRRDei0LCDDG7E%3D","ID":39,"productName":"呵呵","showImg":""}]
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
         * belong : 1
         * productImg : 9.png
         * ID : 31
         * productName : 头像2
         * showImg :
         */

        private int belong;
        private String productImg;
        private int ID;
        private String productName;
        private String showImg;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getBelong() {
            return belong;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }
    }
}
