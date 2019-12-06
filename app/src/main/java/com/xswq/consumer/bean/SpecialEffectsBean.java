package com.xswq.consumer.bean;

import java.util.List;

public class SpecialEffectsBean {


    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"skinList":[{"productValue":500,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552377424531.jpg?Expires=1867737424&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=v2VvocfUQyyPgP%2F2OH65LP%2By4jo%3D","id":26,"discountValue":0,"productName":"皮肤1","showImg":"7.png"}],"effectList":[{"productValue":800,"belong":0,"productImg":"6.png","id":27,"discountValue":700,"productName":"行棋特效1","showImg":"6.json"},{"productValue":1000,"belong":0,"productImg":"6.png","id":33,"productName":"行棋特效2","showImg":"6.json"}]}
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
        private List<SkinListBean> skinList;
        private List<EffectListBean> effectList;

        public List<SkinListBean> getSkinList() {
            return skinList;
        }

        public void setSkinList(List<SkinListBean> skinList) {
            this.skinList = skinList;
        }

        public List<EffectListBean> getEffectList() {
            return effectList;
        }

        public void setEffectList(List<EffectListBean> effectList) {
            this.effectList = effectList;
        }

        public static class SkinListBean {
            /**
             * productValue : 500
             * belong : 0
             * productImg : http://file.xswq361.cn/consumer/Product/1552377424531.jpg?Expires=1867737424&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=v2VvocfUQyyPgP%2F2OH65LP%2By4jo%3D
             * id : 26
             * discountValue : 0
             * productName : 皮肤1
             * showImg : 7.png
             */

            private String productValue;
            private int belong;
            private String productImg;
            private int id;
            private int discountValue;
            private int used;
            private String productName;
            private String showImg;

            public int getUsed() {
                return used;
            }

            public void setUsed(int used) {
                this.used = used;
            }

            public String getProductValue() {
                return productValue;
            }

            public void setProductValue(String productValue) {
                this.productValue = productValue;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDiscountValue() {
                return discountValue;
            }

            public void setDiscountValue(int discountValue) {
                this.discountValue = discountValue;
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

        public static class EffectListBean {
            /**
             * productValue : 800
             * belong : 0
             * productImg : 6.png
             * id : 27
             * discountValue : 700
             * productName : 行棋特效1
             * showImg : 6.json
             */

            private String productValue;
            private int belong;
            private String productImg;
            private int id;
            private int used;
            private int discountValue;
            private String productName;
            private String showImg;

            public int getUsed() {
                return used;
            }

            public void setUsed(int used) {
                this.used = used;
            }

            public String getProductValue() {
                return productValue;
            }

            public void setProductValue(String productValue) {
                this.productValue = productValue;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDiscountValue() {
                return discountValue;
            }

            public void setDiscountValue(int discountValue) {
                this.discountValue = discountValue;
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
}
