package com.lsh.variousqrcode;

/**
 * Created by "小灰灰"
 * on 1/12/2016 11:03 .
 */

public class BitmapPosition {
    private int size;
    private int x;
    private int y;
    private int angle;

    /**
     * size : 762
     * x : 119
     * y : 112
     * angle : 0
     */


    @Override
    public String toString() {
        return "BitmapPosition{" +
                "size=" + size +
                ", x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
