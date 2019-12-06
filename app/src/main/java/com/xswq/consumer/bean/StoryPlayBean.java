package com.xswq.consumer.bean;

public class StoryPlayBean {

    /**
     * error : {"returnCode":"0","returnMessage":"操作成功","returnUserMessage":"操作成功","userRanking":null,"homeTable":null}
     * data : {"id":1,"storyName":"关羽下棋刮骨疗伤","storyImg":"https://test.xswq361.cn/files/gs/image/1_8.jpg;https://test.xswq361.cn/files/gs/image/1_7.jpg","storySound":"https://test.xswq361.cn/files/gs/audio/1.mp3","createTime":1544579599000,"changeTime":"0;24;35;60;70;92;142;206","soundTime":"4:35"}
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
         * id : 1
         * storyName : 关羽下棋刮骨疗伤
         * storyImg : https://test.xswq361.cn/files/gs/image/1_8.jpg;https://test.xswq361.cn/files/gs/image/1_7.jpg
         * storySound : https://test.xswq361.cn/files/gs/audio/1.mp3
         * createTime : 1544579599000
         * changeTime : 0;24;35;60;70;92;142;206
         * soundTime : 4:35
         */

        private int id;
        private String storyName;
        private String storyImg;
        private String storySound;
        private long createTime;
        private String changeTime;
        private String soundTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStoryName() {
            return storyName;
        }

        public void setStoryName(String storyName) {
            this.storyName = storyName;
        }

        public String getStoryImg() {
            return storyImg;
        }

        public void setStoryImg(String storyImg) {
            this.storyImg = storyImg;
        }

        public String getStorySound() {
            return storySound;
        }

        public void setStorySound(String storySound) {
            this.storySound = storySound;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getChangeTime() {
            return changeTime;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public String getSoundTime() {
            return soundTime;
        }

        public void setSoundTime(String soundTime) {
            this.soundTime = soundTime;
        }
    }
}
