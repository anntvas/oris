package org.example;

public class ResponceContent {
    private String mimeType;
    private byte[] content;

    public ResponceContent() {
    }

    public ResponceContent(String mimeType, byte[] content) {
        this.mimeType = mimeType;
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}