package com.zhang.Mario;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable {


    //坐标
    private int x,y;
    //降旗
    private Thread t = new Thread(this);


    private BufferedImage showImage = null ;
    private BackGround bg;

    //类型
    private int type;
    //初始化的类型
    private int startType;
    //显示图片

    //构造方法
    public Obstruction(int x,int y,int type,BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.startType = type ;
        this.bg = bg ;
//		showImage=StaticValue.allObstructionImage.get(type);
        setImage();
        if(this.type==11) {
            t.start();
        }
    }

    //重置方法
    public void reset() {
        //修改类型为初始化类型
        this.type = startType;
        //改变显示图片
        this.setImage();
    }

    //根据类型改变显示图片
    public void setImage() {
        showImage = StaticValue.allobstuctionImage.get(type);
    }

    public void run() {
        while(true) {
            if(this.bg.isOver()) {
                if(this.y < 420 ) {
                    this.y += 5;
                } else {
                    this.bg.setDown(true);
                }
            }
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStartType() {
        return startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    public BufferedImage getShowImage() {
        return showImage;
    }

    public void setShowImage(BufferedImage showImage) {
        this.showImage = showImage;
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

