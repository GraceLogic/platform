package com.gracelogic.platform.tcpserver.dto;

public class Package {
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private byte[] data;

    public Package(byte[] data) {
        this.data = data;
    }
}
