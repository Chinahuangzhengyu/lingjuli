package com.zhjl.qihao.abrefactor.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9.
 */

public class RoomBean implements Serializable {

    /**
     * roomCode : 00200101011102
     * newRoomCode : 00020010010111002
     * roomName : H31单元1102
     * smallCommunityCode : 002001
     * newSmallCommunityCode : 0002001
     * smallCommunityName : 北京铭基国际创意公园
     * userType : 1
     * token : 1501496277573f6b06c2eaf34391b2a6
     * isDefault : 1
     */

    private String roomCode;
    private String newRoomCode;
    private String roomName;
    private String smallCommunityCode;
    private String newSmallCommunityCode;
    private String smallCommunityName;
    private String userType;
    private String token;
    private String isDefault;

    public static RoomBean objectFromData(String str) {

        return new Gson().fromJson(str, RoomBean.class);
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getNewRoomCode() {
        return newRoomCode;
    }

    public void setNewRoomCode(String newRoomCode) {
        this.newRoomCode = newRoomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSmallCommunityCode() {
        return smallCommunityCode;
    }

    public void setSmallCommunityCode(String smallCommunityCode) {
        this.smallCommunityCode = smallCommunityCode;
    }

    public String getNewSmallCommunityCode() {
        return newSmallCommunityCode;
    }

    public void setNewSmallCommunityCode(String newSmallCommunityCode) {
        this.newSmallCommunityCode = newSmallCommunityCode;
    }

    public String getSmallCommunityName() {
        return smallCommunityName;
    }

    public void setSmallCommunityName(String smallCommunityName) {
        this.smallCommunityName = smallCommunityName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
