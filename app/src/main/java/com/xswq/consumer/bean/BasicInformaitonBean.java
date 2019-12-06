package com.xswq.consumer.bean;

public class BasicInformaitonBean {


    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"userInfo":{"id":2224,"username":"城北幼儿园","password":"","mobile":"","usertype":1,"usersourse":0,"qq":"","wechat":"","sina":"oG6lf1VB9YuadMDsP8Q7375hCClk","createtime":1551170254000,"headimg":"http://thirdwx.qlogo.cn/mmopen/vi_32/NYfjRelia7DMRw7qia8tuWkyHdGwoFCferkVXeWBibV5dHUehgO6XhedvtZAZBlg7MzaraMYPY2OlfvP9jR2lufKA/132","address":"","sex":1,"birthday":null,"state":0,"loginTime":null,"client":null,"imToken":"","imAccid":""},"userDetail":{"id":20,"userId":2224,"gold":0,"purchasedGateId":2,"level":0,"star":0,"headImgBorder":null,"skinId":0,"playEffectId":0}}
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
         * userInfo : {"id":2224,"username":"城北幼儿园","password":"","mobile":"","usertype":1,"usersourse":0,"qq":"","wechat":"","sina":"oG6lf1VB9YuadMDsP8Q7375hCClk","createtime":1551170254000,"headimg":"http://thirdwx.qlogo.cn/mmopen/vi_32/NYfjRelia7DMRw7qia8tuWkyHdGwoFCferkVXeWBibV5dHUehgO6XhedvtZAZBlg7MzaraMYPY2OlfvP9jR2lufKA/132","address":"","sex":1,"birthday":null,"state":0,"loginTime":null,"client":null,"imToken":"","imAccid":""}
         * userDetail : {"id":20,"userId":2224,"gold":0,"purchasedGateId":2,"level":0,"star":0,"headImgBorder":null,"skinId":0,"playEffectId":0}
         */

        private UserInfoBean userInfo;
        private UserDetailBean userDetail;
        private int maxGateNum;

        public int getMaxGateNum() {
            return maxGateNum;
        }

        public void setMaxGateNum(int maxGateNum) {
            this.maxGateNum = maxGateNum;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public UserDetailBean getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetailBean userDetail) {
            this.userDetail = userDetail;
        }

        public static class UserInfoBean {
            /**
             * id : 2224
             * username : 城北幼儿园
             * password :
             * mobile :
             * usertype : 1
             * usersourse : 0
             * qq :
             * wechat :
             * sina : oG6lf1VB9YuadMDsP8Q7375hCClk
             * createtime : 1551170254000
             * headimg : http://thirdwx.qlogo.cn/mmopen/vi_32/NYfjRelia7DMRw7qia8tuWkyHdGwoFCferkVXeWBibV5dHUehgO6XhedvtZAZBlg7MzaraMYPY2OlfvP9jR2lufKA/132
             * address :
             * sex : 1
             * birthday : null
             * state : 0
             * loginTime : null
             * client : null
             * imToken :
             * imAccid :
             */

            private int id;
            private String username;
            private String password;
            private String mobile;
            private int usertype;
            private int usersourse;
            private String qq;
            private String wechat;
            private String sina;
            private long createtime;
            private String headimg;
            private String address;
            private int sex;
            private long birthday;
            private int state;
            private Object loginTime;
            private Object client;
            private String imToken;
            private String imAccid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getUsertype() {
                return usertype;
            }

            public void setUsertype(int usertype) {
                this.usertype = usertype;
            }

            public int getUsersourse() {
                return usersourse;
            }

            public void setUsersourse(int usersourse) {
                this.usersourse = usersourse;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWechat() {
                return wechat;
            }

            public void setWechat(String wechat) {
                this.wechat = wechat;
            }

            public String getSina() {
                return sina;
            }

            public void setSina(String sina) {
                this.sina = sina;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public long getBirthday() {
                return birthday;
            }

            public void setBirthday(long birthday) {
                this.birthday = birthday;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public Object getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(Object loginTime) {
                this.loginTime = loginTime;
            }

            public Object getClient() {
                return client;
            }

            public void setClient(Object client) {
                this.client = client;
            }

            public String getImToken() {
                return imToken;
            }

            public void setImToken(String imToken) {
                this.imToken = imToken;
            }

            public String getImAccid() {
                return imAccid;
            }

            public void setImAccid(String imAccid) {
                this.imAccid = imAccid;
            }
        }

        public static class UserDetailBean {
            /**
             * id : 20
             * userId : 2224
             * gold : 0
             * purchasedGateId : 2
             * level : 0
             * star : 0
             * headImgBorder : null
             * skinId : 0
             * playEffectId : 0
             */

            private int id;
            private int userId;
            private int gold;
            private int purchasedGateId;
            private int level;
            private int star;
            private String headImgBorder;
            private int skinId;
            private int playEffectId;

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

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }

            public int getPurchasedGateId() {
                return purchasedGateId;
            }

            public void setPurchasedGateId(int purchasedGateId) {
                this.purchasedGateId = purchasedGateId;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getStar() {
                return star;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public String getHeadImgBorder() {
                return headImgBorder;
            }

            public void setHeadImgBorder(String headImgBorder) {
                this.headImgBorder = headImgBorder;
            }

            public int getSkinId() {
                return skinId;
            }

            public void setSkinId(int skinId) {
                this.skinId = skinId;
            }

            public int getPlayEffectId() {
                return playEffectId;
            }

            public void setPlayEffectId(int playEffectId) {
                this.playEffectId = playEffectId;
            }
        }
    }
}
