package com.xswq.consumer.bean;

import java.util.List;

public class ShoppingMallParticularsBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"total":5,"list":[{"isRmb":0,"productEffect":"","productValue":2,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359889968.png?Expires=1867719886&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=sKp6JPox9LSDjQRRDei0LCDDG7E%3D","id":39,"attribute":2,"purchaseNum":0,"discountValue":0,"productName":"呵呵","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"","productValue":12,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359588705.png?Expires=1867719579&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=lmseAmXbBd0BGJ6K0rQq56bz0kg%3D","id":38,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"哈哈","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"111","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552357160103.png?Expires=1867717155&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=7IxaX%2FdxvVvsKQTDLAHI226lNOw%3D","id":36,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"测试商品1带礼包","productDescription":"111","showImg":""},{"isRmb":0,"productEffect":"","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552358993905.png?Expires=1867718986&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=vfmrEZYBMo%2FmHavxryV5w8ui2d0%3D","id":37,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"测试商品2带礼包","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"11","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552368803322.png?Expires=1867728800&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=4q6ievKI2C6hoyjBBGlNfmIftMs%3D","id":40,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"礼包","productDescription":"11","showImg":""}],"pageNum":1,"pageSize":6,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}
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
         * total : 5
         * list : [{"isRmb":0,"productEffect":"","productValue":2,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359889968.png?Expires=1867719886&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=sKp6JPox9LSDjQRRDei0LCDDG7E%3D","id":39,"attribute":2,"purchaseNum":0,"discountValue":0,"productName":"呵呵","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"","productValue":12,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552359588705.png?Expires=1867719579&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=lmseAmXbBd0BGJ6K0rQq56bz0kg%3D","id":38,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"哈哈","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"111","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552357160103.png?Expires=1867717155&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=7IxaX%2FdxvVvsKQTDLAHI226lNOw%3D","id":36,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"测试商品1带礼包","productDescription":"111","showImg":""},{"isRmb":0,"productEffect":"","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552358993905.png?Expires=1867718986&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=vfmrEZYBMo%2FmHavxryV5w8ui2d0%3D","id":37,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"测试商品2带礼包","productDescription":"","showImg":""},{"isRmb":0,"productEffect":"11","productValue":100,"belong":0,"productImg":"http://file.xswq361.cn/consumer/Product/1552368803322.png?Expires=1867728800&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=4q6ievKI2C6hoyjBBGlNfmIftMs%3D","id":40,"attribute":1,"purchaseNum":0,"discountValue":0,"productName":"礼包","productDescription":"11","showImg":""}]
         * pageNum : 1
         * pageSize : 6
         * size : 5
         * startRow : 1
         * endRow : 5
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
         * lastPage : 1
         * firstPage : 1
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
        private int lastPage;
        private int firstPage;
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

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
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
             * isRmb : 0
             * productEffect :
             * productValue : 2
             * belong : 0
             * productImg : http://file.xswq361.cn/consumer/Product/1552359889968.png?Expires=1867719886&OSSAccessKeyId=LTAIWmJ5x4OSdvV6&Signature=sKp6JPox9LSDjQRRDei0LCDDG7E%3D
             * id : 39
             * attribute : 2
             * purchaseNum : 0
             * discountValue : 0
             * productName : 呵呵
             * productDescription :
             * showImg :
             */

            private int isRmb;
            private String productEffect;
            private int productValue;
            private int belong;
            private String productImg;
            private int id;
            private int attribute;
            private int purchaseNum;
            private int discountValue;
            private String productName;
            private String productDescription;
            private String showImg;
            private long endTime;
            private boolean isSelect;

            public boolean getIsSelect() {
                return isSelect;
            }

            public void setIsSelect(boolean select) {
                isSelect = select;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getIsRmb() {
                return isRmb;
            }

            public void setIsRmb(int isRmb) {
                this.isRmb = isRmb;
            }

            public String getProductEffect() {
                return productEffect;
            }

            public void setProductEffect(String productEffect) {
                this.productEffect = productEffect;
            }

            public int getProductValue() {
                return productValue;
            }

            public void setProductValue(int productValue) {
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

            public int getAttribute() {
                return attribute;
            }

            public void setAttribute(int attribute) {
                this.attribute = attribute;
            }

            public int getPurchaseNum() {
                return purchaseNum;
            }

            public void setPurchaseNum(int purchaseNum) {
                this.purchaseNum = purchaseNum;
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

            public String getProductDescription() {
                return productDescription;
            }

            public void setProductDescription(String productDescription) {
                this.productDescription = productDescription;
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
