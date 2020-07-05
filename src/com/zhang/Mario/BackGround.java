package com.zhang.Mario;



import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {


    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    private boolean isDown=false;
    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean isDown) {
        this.isDown = isDown;
    }

    //当前场景的背景
    private BufferedImage bgImage = null;

    //	public BufferedImage getBgImage() {
//		return bgImage;
//	}
    //场景的顺序
    private int sort;

    //当前是否为最后一个场景
    private boolean flag;

    //通过集合来保存
    //全部的敌人
    private List<Enemy> allEnemy = new ArrayList<Enemy>();

    //全部的障碍物
    private List<Obstruction> allObstruction = new ArrayList<Obstruction>();

    //	public List<Obstruction> getAllObstruction() {
//		return allObstruction;
//	}
    //被消灭的敌人
    private List<Enemy> removedEnemy = new ArrayList<Enemy>();

    //被消灭的障碍物
    private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();

    private boolean isOver = false ;
    //构造方法
    public BackGround(int sort,boolean flag) {
        this.sort = sort;
        this.flag = flag;
        if(flag) {
            bgImage = StaticValue.endImage;
        } else {
            bgImage = StaticValue.bgImage;
        }
        if(sort==1){
            //绘制地面
            for(int i = 0;i < 15; i++){
                this.allObstruction.add(new Obstruction(i*60,540,9,this));
            }

            //绘制砖块和问号砖块
            this.allObstruction.add(new Obstruction(120,360,4,this));
            this.allObstruction.add(new Obstruction(300,360,0,this));
            this.allObstruction.add(new Obstruction(360,360,4,this));
            this.allObstruction.add(new Obstruction(420,360,0,this));
            this.allObstruction.add(new Obstruction(480,360,4,this));
            this.allObstruction.add(new Obstruction(540,360,0,this));
            this.allObstruction.add(new Obstruction(420,360,4,this));
            this.allObstruction.add(new Obstruction(420,180,4,this));

            //绘制水管
            this.allObstruction.add(new Obstruction(11*60,600-2*60,8,this));
            this.allObstruction.add(new Obstruction(12*60,600-2*60,7,this));
            this.allObstruction.add(new Obstruction(11*60,600-60,6,this));
            this.allObstruction.add(new Obstruction(12*60,600-60,5,this));

            //加入隐形砖块
            this.allObstruction.add(new Obstruction(660,300,3,this));
            //绘制敌人--蘑菇型的
            this.allEnemy.add(new Enemy(600,480,true,1,this)) ;
            //绘制食人花
            this.allEnemy.add(new Enemy(690,540,true,2,420,540,this)) ;

        }
        if(sort==2){
            //绘制地面
            for(int i=0;i<15;i++){
                //在10处加入一个陷阱
                if(i!=10){
                    this.allObstruction.add(new Obstruction(i*60,540,9,this));
                }
            }
            this.allObstruction.add(new Obstruction(60,540,6,this));
            this.allObstruction.add(new Obstruction(120,540,5,this));
            this.allObstruction.add(new Obstruction(60,480,6,this));
            this.allObstruction.add(new Obstruction(120,480,5,this));
            this.allObstruction.add(new Obstruction(60,420,8,this));
            this.allObstruction.add(new Obstruction(120,420,7,this));


            this.allObstruction.add(new Obstruction(240,540,6,this));
            this.allObstruction.add(new Obstruction(300,540,5,this));
            this.allObstruction.add(new Obstruction(240,480,6,this));
            this.allObstruction.add(new Obstruction(300,480,5,this));
            this.allObstruction.add(new Obstruction(240,420,6,this));
            this.allObstruction.add(new Obstruction(300,420,5,this));
            this.allObstruction.add(new Obstruction(240,360,8,this));
            this.allObstruction.add(new Obstruction(300,360,7,this));

        }
        if(sort==3){
            for(int i=0;i<15;i++){
                this.allObstruction.add(new Obstruction(i*60,540,9,this));
            }
            this.allObstruction.add(new Obstruction(550,180,11,this));
            this.allObstruction.add(new Obstruction(520,480,2,this));
        }
    }
    //使敌人开始移动的方法----敌人开始移动
    public void enemyStartMove() {
        for(int i = 0; i< this.allEnemy.size();i++) {
            this.allEnemy.get(i).startMove();
        }
    }
    //重置方法，将所有的障碍物和敌人返回到原有的坐标，并将其状态也修改
    public void reset() {
        //将已经移除的障碍物和敌人放回到全部的内容中
        this.allEnemy.addAll(this.removedEnemy) ;
        this.allObstruction.addAll(this.removedObstruction) ;
        //调用所有的障碍物和敌人的重置方法
        for(int i = 0; i< this.allEnemy.size();i++) {
            this.allEnemy.get(i).reset();
        }
        for(int i = 0; i< this.allObstruction.size();i++) {
            this.allObstruction.get(i).reset();
        }
    }
    public BufferedImage getBgImage() {
        return bgImage;
    }

    public void setBgImage(BufferedImage bgImage) {
        this.bgImage = bgImage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Enemy> getAllEnemy() {
        return allEnemy;
    }

    public void setAllEnemy(List<Enemy> allEnemy) {
        this.allEnemy = allEnemy;
    }

    public List<Obstruction> getAllObstruction() {
        return allObstruction;
    }

    public void setAllObstruction(List<Obstruction> allObstruction) {
        this.allObstruction = allObstruction;
    }

    public List<Enemy> getRemovedEnemy() {
        return removedEnemy;
    }

    public void setRemovedEnemy(List<Enemy> removedEnemy) {
        this.removedEnemy = removedEnemy;
    }

    public List<Obstruction> getRemovedObstruction() {
        return removedObstruction;
    }

    public void setRemovedObstruction(List<Obstruction> removedObstruction) {
        removedObstruction = removedObstruction;
    }


}

