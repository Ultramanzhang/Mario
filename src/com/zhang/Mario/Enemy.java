package com.zhang.Mario;

import java.awt.image.BufferedImage;
//引入一个Runnable接口
public class Enemy implements Runnable {

    // 坐标
    private int x;
    private int y;

    // 初始坐标
    private int startX;
    private int startY;

    // 类型
    private int type;

    // 显示图片
    private BufferedImage showImage = null ;

    // 移动方向
    private boolean isLeftOrUp = true ;  //布尔型，因为在场景中 两种敌人都是一种的移动方式  向左或者向右

    // 移动的范围---向上与向下的极限坐标
    private int upMax = 0;
    private int downMax = 0;

    // 定义线程
    private Thread t = new Thread(this);

    //定义图片状态
    private int imageType = 0;

    // 定义一个场景对象
    private BackGround bg;

    // 创建普通敌人
    public Enemy(int x,int y,boolean isLeft,int type,BackGround bg) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.isLeftOrUp = isLeft;
        this.type = type;
        this.bg = bg ;
        if(type == 1) {
            this.showImage = StaticValue.allTriangleImage.get(0) ;//也可以加入乌龟
        }
        t.start();
        t.suspend();
    }
    //创建食人花
    public Enemy(int x,int y,boolean isUp,int type,int upMax,int downMax,BackGround bg) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.isLeftOrUp = isUp;
        this.type = type;
        this.upMax = upMax ;
        this.downMax = downMax ;
        this.bg = bg ;
        if(type == 2) {
            this.showImage = StaticValue.allFlowerImage.get(0) ;//也可以加入乌龟
        }
        t.start();
    }
    public void run() {
        // TODO Auto-generated method stub
        while(true) {

            boolean canLeft = true;
            boolean canRight = true;
            //是否在障碍物上
            boolean onLand = false ;
            for(int i = 0; i<this.bg.getAllObstruction().size();i++) {
                Obstruction ob = this.bg.getAllObstruction().get(i);
                //不允许向右继续移动   x代表敌人的x轴坐标不是马里奥的x坐标
                if(ob.getX() == this.x+ 60 && (ob.getY()+ 60 > this.y && ob.getY() - 60 < this.y)) {
                    canRight = false ;
                }
                //不允许向左继续移动
                if(ob.getX()==this.x- 60 && (ob.getY()+ 60 > this.y && ob.getY() - 60 < this.y)) {
                    canLeft = false ;
                }
            }//蘑菇头的移动规则
            if(!canLeft && this.isLeftOrUp  || this.x == 0) {
                this.isLeftOrUp=false;
            } else if (!canRight && !this.isLeftOrUp || this.x == 840) {
                this.isLeftOrUp=true;
            }

            //对于不同的类型要进行不同的处理
            if(type == 1) {
                if (this.isLeftOrUp) {
                    this.x -=2;
                } else {
                    this.x +=2;
                }
                if (imageType == 0) {
                    imageType =1;
                } else {
                    imageType = 0;
                }
                this.showImage = StaticValue.allTriangleImage.get(imageType);
            }
            if(type == 2) {
                if(this.isLeftOrUp) {
                    this.y -= 2;
                } else {
                    this.y += 2;
                }
                if (imageType == 0) {
                    imageType = 1;
                } else {
                    imageType = 0;
                }
                if (this.isLeftOrUp && this.y == this.upMax) {
                    this.isLeftOrUp = false ;
                }
                if (!this.isLeftOrUp && this.y == this.downMax) {
                    this.isLeftOrUp = true ;
                }
                this.showImage = StaticValue.allFlowerImage.get(imageType);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    //死亡方法
    public void dead() {
        //将显示的图片修改为死亡时的图片
        this.showImage = StaticValue.allTriangleImage.get(2);
        this.bg.getAllEnemy().remove(this);
        this.bg.getRemovedEnemy().add(this);
    }
    public void startMove() {
        t.resume();
    }
    public void reset() {
        //将坐标重置
        this.x = this.startX;
        this.y = this.startY;
        // 将显示图片重置
        if(this.type == 1) {
            this.showImage = StaticValue.allTriangleImage.get(0) ;
        } else if (this.type == 2){
            this.showImage = StaticValue.allFlowerImage.get(0) ;
        }
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
    public int getStartX() {
        return startX;
    }
    public void setStartX(int startX) {
        this.startX = startX;
    }
    public int getStartY() {
        return startY;
    }
    public void setStartY(int startY) {
        this.startY = startY;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public BufferedImage getShowImage() {
        return showImage;
    }
    public void setShowImage(BufferedImage showImage) {
        this.showImage = showImage;
    }
    public boolean isLeftOrUp() {
        return isLeftOrUp;
    }
    public void setLeftOrUp(boolean isLeftOrUp) {
        this.isLeftOrUp = isLeftOrUp;
    }
    public int getUpMax() {
        return upMax;
    }
    public void setUpMax(int upMax) {
        this.upMax = upMax;
    }
    public int getDownMax() {
        return downMax;
    }
    public void setDownMax(int downMax) {
        this.downMax = downMax;
    }
}


