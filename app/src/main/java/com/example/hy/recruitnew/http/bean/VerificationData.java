package com.example.hy.recruitnew.http.bean;

/**
 * 验证码信息
 * Created by 陈健宇 at 2019/2/13
 */
public class VerificationData {

    private byte[] bigPicture;
    private byte[] smallPicture;
    private int x;
    private int y;

    public byte[] getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(byte[] bigPicture) {
        this.bigPicture = bigPicture;
    }

    public byte[] getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(byte[] smallPicture) {
        this.smallPicture = smallPicture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
