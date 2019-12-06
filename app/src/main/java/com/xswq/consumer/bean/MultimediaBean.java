package com.xswq.consumer.bean;

import java.io.Serializable;

public class MultimediaBean implements Serializable {
    private String storyId;
    private String videoId;
    private String gameId;
    private String gateNum;
    private String testQuestionId;
    private String questionId;
    private int seat;
    private int maxGateNum;
    private int maxSeat;

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getMaxGateNum() {
        return maxGateNum;
    }

    public void setMaxGateNum(int maxGateNum) {
        this.maxGateNum = maxGateNum;
    }

    public int getMaxSeat() {
        return maxSeat;
    }

    public void setMaxSeat(int maxSeat) {
        this.maxSeat = maxSeat;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGateNum() {
        return gateNum;
    }

    public void setGateNum(String gateNum) {
        this.gateNum = gateNum;
    }

    public String getTestQuestionId() {
        return testQuestionId;
    }

    public void setTestQuestionId(String testQuestionId) {
        this.testQuestionId = testQuestionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
