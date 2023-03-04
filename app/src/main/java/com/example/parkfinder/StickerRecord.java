package com.example.parkfinder;

public class StickerRecord {
    private StickerType stickerType;
    private String senderUsername;
    private String receiverUsername;
    private String time;

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
}
