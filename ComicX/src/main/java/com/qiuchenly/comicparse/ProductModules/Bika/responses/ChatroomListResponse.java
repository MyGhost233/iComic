package com.qiuchenly.comicparse.ProductModules.Bika.responses;


import com.qiuchenly.comicparse.ProductModules.Bika.ChatroomListObject;

import java.util.ArrayList;

public class ChatroomListResponse {
    ArrayList<ChatroomListObject> chatList;

    public ChatroomListResponse(ArrayList<ChatroomListObject> chatList) {
        this.chatList = chatList;
    }

    public ArrayList<ChatroomListObject> getChatList() {
        return this.chatList;
    }

    public void setChatList(ArrayList<ChatroomListObject> chatList) {
        this.chatList = chatList;
    }
}
