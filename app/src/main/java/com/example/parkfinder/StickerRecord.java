package com.example.parkfinder;

public class StickerRecord {
    private StickerType stickerType;
    private String senderUsername;
    private String receiverUsername;
    private String time;

    public StickerRecord(){}

    public StickerRecord(StickerType stickerType, String senderUsername, String receiverUsername,
                         String time) {
        this.stickerType = stickerType;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.time = time;
    }

    public StickerType getStickerType() {
        return stickerType;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getTime() {
        return time;
    }

    public String getCompleteRecord (){
        StringBuilder builder = new StringBuilder();
        builder.append(receiverUsername)
                .append(" received a sticker of ")
                .append(stickerType)
                .append(" from ")
                .append(senderUsername)
                .append(" at ")
                .append(time);
        return builder.toString();
    }
    public void setStickerType(StickerType stickerType) {
        this.stickerType = stickerType;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
