package com.zhang.Mario;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Mario implements Runnable {

    //标记Mario是否死亡
    private boolean isDead = false ;

    //胜利
    private boolean isClear;

    //坐标
    private int x;
    private int y;

    //定义一个场景对象，保存当前Mario所在的场景
    private BackGround bg = null ;

//	public void setBg(BackGround bg) {
//		this.bg = bg;
//	}

    //加入线程
    private Thread t = null;

    //定义一个速度的属性
    private int xmove = 0;;

    //定义一个垂直方向的速度
    private int ymove = 0;;

    //状态
    private String status;

    //显示图片
    private BufferedImage showImage;

    //生命和分数
    private int score;

    private int lift;

    //当前移动中显示的图片索引
    private int moving = 0;

    //上升的时间
    private int upTime = 0;

    private int life;
    //构造方法
    public Mario(int x,int y) {
        this.x = x;
        this.y = y;
        //初始化Mario的图片
        this.showImage = StaticValue.allMarioImage.get(0);
        this.score = 0;
        this.lift = 3;
        this.status = "reght--standing";

        t=new Thread(this);
        t.start();
    }



    //Mario的移动方法
    public void run(){
        while(true){

            //如果是最后一个场景，并且到达旗杆位置，游戏 结束，不能控制
            if(this.bg.isFlag() && this.x >= 520 ) {
                this.bg.setOver(true);
                if(this.bg.isDown()) {
                    //向城门移动
                    this.status="right--moving";
                    if(this.x<580) {
                        //在台阶上向右
                        x += 5;
                    } else {
                        x += 5;
                        if(y<480) {
                            //向下
                            y += 5;
                        }
                        //到达城门。游戏结束
                        if(x >= 780){
                            isClear = true;
                        }
                    }

                } else {
                    if(this.y < 420){
                        this.y += 5;
                    }
                    if(this.y >= 420){
                        this.y = 420;
                        this.status = "right--standing";
                    }
                }
            } else {
                //判断是否可以移动
                boolean canLeft = true;
                boolean canRight = true;
                //是否在障碍物上
                boolean onLand = false;
                for(int i=0;i<this.bg.getAllObstruction().size(); i++) {
                    Obstruction ob=bg.getAllObstruction().get(i);
                    //不能向右
                    if(ob.getX() == this.x+60&&(ob.getY()+50>this.y&&ob.getY()-50<this.y)) {
                        if(ob.getType() != 3){
                            canRight = false;
                        }
                    }
                    //不能向左
                    if(ob.getX()==this.x-60&&(ob.getY()+50>this.y&&ob.getY()-50<this.y)) {
                        if(ob.getType()!=3) {
                            canLeft = false;
                        }
                    }
                    //在某个障碍物上，可以跳跃
                    if(ob.getY()==this.y+60 && (ob.getX()+60 > this.x && ob.getX() - 60 < this.x)){
                        if(ob.getType()!=3){
                            onLand=true;
                        }
                    }

                    //判断是否顶到障碍物
                    if(ob.getY() == this.y - 60 && (ob.getX()+50 > this.x && ob.getX()-50 < this.x)){
                        //对砖块进行处理
                        if(ob.getType()==0){
                            this.bg.getAllObstruction().remove(ob);
                            this.bg.getRemovedObstruction().add(ob);
                        }

                        //对于问号的处理和对隐形砖块的处理
                        if(ob.getType()==4||ob.getType()==3&&upTime>0){
                            ob.setType(2);
                            ob.setImage();
                        }
                        upTime=0;
                    }

                }


                //对敌人的碰撞进行判断
                for(int i=0;i<this.bg.getAllEnemy().size();i++) {
                    Enemy e=this.bg.getAllEnemy().get(i);
                    if(e.getX()+50 > this.x &&  e.getX() - 50 < this.x &&
                            e.getY()+50 > this.y && e.getY()-50 < this.y) {
                        this.dead();
                    }
                    if(e.getY() == this.y + 60 && (e.getX() + 60 > this.x &&
                            e.getX()-60 < this.x )){
                        if(e.getType()==1) {
                            e.dead();
                            this.upTime=10;
                            this.ymove=-5;
                        }else if(e.getType()==2) {
                            this.dead();
                        }
                    }
                }


                if(onLand && upTime == 0) {
                    if(this.status.indexOf("left") != -1 ) {
                        if(xmove != 0) {
                            this.status = "left--moving" ;
                        } else {
                            this.status = "left--standing";
                        }
                    } else {
                        if(xmove != 0) {
                            this.status = "right--moving" ;
                        } else {
                            this.status = "right--standing";
                        }
                    }
                } else {
                    //表示当前上升的状态
                    if(upTime != 0) {
                        upTime--;
                    } else {
                        this.down();
                    }
                    y +=ymove;
                }

                if(canLeft && xmove < 0 || (canRight && xmove > 0)) {
                    //改变坐标
                    x += xmove;
                    if(x<0) {
                        x=0;
                    }
                }
            }
            //定义一个图片去的初始索引数
            int temp = 0;
            //当前面向右
            if(this.status.indexOf("left")!= -1) {
                temp +=5;
            }
            //判断当前是否为移动
            if(this.status.indexOf("moving") != -1) {
                temp +=moving;
                moving++;
                if(moving==4) {
                    moving=0;
                }
            }
            if(this.status.indexOf("jumping") != -1) {
                temp += 4;
            }

            //改变显示图片
            this.showImage = StaticValue.allMarioImage.get(temp);


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //Mario的死亡方法
    public void dead() {
        this.lift--;
        if(this.lift == 0) {
            this.isDead = true ;
        } else {
            this.bg.reset();
            this.x = 0 ;
            this.y = 480 ;
        }
    }
    public void leftMove() {
        // 改变速度
        xmove = -5;
        // 改变状态
        // 如果当前已经是跳跃状态，应该保留原有的状态，而不能修改为移动状态
        if(this.status.indexOf("jumping") != -1) {
            this.status = "left--jumping";
        } else {
            this.status = "left--moving";
        }
    }

    public void rightMove() {
        xmove = 5;
        if(this.status.indexOf("jumping") != -1) {
            this.status = "right--jumping";
        } else {
            this.status = "right--moving";
        }

    }

    public void leftStop() {
        this.xmove = 0;
        if(this.status.indexOf("jumping") != -1) {
            this.status = "left--jumping";
        } else {
            this.status = "left--standing";
        }

    }
    public void rightStop() {
        this.xmove = 0;
        if(this.status.indexOf("jumping") != -1) {
            this.status = "right--jumping";
        } else {
            this.status = "right--standing";
        }

    }

    public void jump() {
        if(this.status.indexOf("jumping") == -1) {
            if(this.status.indexOf("left") != -1) {
                this.status = ("left--jumping");
            } else {
                this.status = ("right--jumping");
            }
            ymove = -5;
            upTime = 36 ;
        }
    }

    //加入一个下落的方法
    public void down () {
        if(this.status.indexOf("left") != -1) {
            this.status = "left--jumping";
        } else {
            this.status = "right--jumping";
        }
        ymove = 5;
    }
    public BackGround getBg() {
        return bg;
    }

    public void setBg(BackGround bg) {
        this.bg = bg;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BufferedImage getShowImage() {
        return showImage;
    }

    public void setShowImage(BufferedImage showImage) {
        this.showImage = showImage;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean isClear) {
        this.isClear = isClear;
    }
}

