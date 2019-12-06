package com.xswq.consumer.bean;

public class NowAcitivityBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"id":3,"activityType":0,"road":9,"rule":1,"ruleNum":5,"randomChess":2,"baseTime":300,"countNum":3,"stepTime":20,"beginTime":1555052400000,"endTime":1555171080000,"createTime":1555033879000,"createUser":0,"state":1,"ruleInstruction":"小黑小白在9路棋盘上淘气了！\r\n开始对弈时，棋盘上会随机出现两个黑棋和两个白棋。\r\n用黑棋的小朋友先走，用白棋的小朋友后走，谁先吃掉对方5颗子就算胜利。\r\n胜利的小朋友会得到3分，失败的小朋友会得到1分，要记住：胜不骄，败不馁！\r\n用时规则：基础用时3分钟，3次20秒读秒\r\n活动时间：4月12日 中午12点 至 4月13日 午夜12点\r\n参加方式及积分上限：每次对弈需要消耗一张活动券，每天积分上限=10分\r\n","awardInstruction":"第一名：100水滴\r\n第二名：50水滴\r\n第三名：30水滴\r\n第四~十名：10水滴\r\n","winScore":0,"loseScore":0,"maxScore":0}
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
         * id : 3
         * activityType : 0
         * road : 9
         * rule : 1
         * ruleNum : 5
         * randomChess : 2
         * baseTime : 300
         * countNum : 3
         * stepTime : 20
         * beginTime : 1555052400000
         * endTime : 1555171080000
         * createTime : 1555033879000
         * createUser : 0
         * state : 1
         * ruleInstruction : 小黑小白在9路棋盘上淘气了！
         开始对弈时，棋盘上会随机出现两个黑棋和两个白棋。
         用黑棋的小朋友先走，用白棋的小朋友后走，谁先吃掉对方5颗子就算胜利。
         胜利的小朋友会得到3分，失败的小朋友会得到1分，要记住：胜不骄，败不馁！
         用时规则：基础用时3分钟，3次20秒读秒
         活动时间：4月12日 中午12点 至 4月13日 午夜12点
         参加方式及积分上限：每次对弈需要消耗一张活动券，每天积分上限=10分

         * awardInstruction : 第一名：100水滴
         第二名：50水滴
         第三名：30水滴
         第四~十名：10水滴

         * winScore : 0
         * loseScore : 0
         * maxScore : 0
         */

        private int id;
        private int activityType;
        private int road;
        private String rule;
        private String ruleNum;
        private int randomChess;
        private int baseTime;
        private int countNum;
        private int stepTime;
        private long beginTime;
        private long endTime;
        private long createTime;
        private int createUser;
        private int state;
        private String ruleInstruction;
        private String awardInstruction;
        private int winScore;
        private int loseScore;
        private int maxScore;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public int getRoad() {
            return road;
        }

        public void setRoad(int road) {
            this.road = road;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getRuleNum() {
            return ruleNum;
        }

        public void setRuleNum(String ruleNum) {
            this.ruleNum = ruleNum;
        }

        public int getRandomChess() {
            return randomChess;
        }

        public void setRandomChess(int randomChess) {
            this.randomChess = randomChess;
        }

        public int getBaseTime() {
            return baseTime;
        }

        public void setBaseTime(int baseTime) {
            this.baseTime = baseTime;
        }

        public int getCountNum() {
            return countNum;
        }

        public void setCountNum(int countNum) {
            this.countNum = countNum;
        }

        public int getStepTime() {
            return stepTime;
        }

        public void setStepTime(int stepTime) {
            this.stepTime = stepTime;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getRuleInstruction() {
            return ruleInstruction;
        }

        public void setRuleInstruction(String ruleInstruction) {
            this.ruleInstruction = ruleInstruction;
        }

        public String getAwardInstruction() {
            return awardInstruction;
        }

        public void setAwardInstruction(String awardInstruction) {
            this.awardInstruction = awardInstruction;
        }

        public int getWinScore() {
            return winScore;
        }

        public void setWinScore(int winScore) {
            this.winScore = winScore;
        }

        public int getLoseScore() {
            return loseScore;
        }

        public void setLoseScore(int loseScore) {
            this.loseScore = loseScore;
        }

        public int getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(int maxScore) {
            this.maxScore = maxScore;
        }
    }
}
