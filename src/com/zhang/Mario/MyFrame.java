package com.zhang.Mario;



import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements KeyListener,Runnable {

    private List<BackGround> allBG = new ArrayList<BackGround>();

    private Mario mario =null;

    //	private List <BackGround> nowBG = null ;
    private BackGround nowBG=null;

    private Thread t = new Thread(this);

    // 是否游戏已经开始游戏
    private boolean isStart = false ;

    public static void main (String[] args) {
        new MyFrame();
    }

    public MyFrame()
    {
        this.setTitle("马里奥游戏程序");
        this.setSize(900,600);
        //得到屏幕大小，设置开始出现的位置
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int heigth = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation(( width - 900)/2, ( heigth - 600)/2);
        //不能够改变屏幕大小
        this.setResizable(false);
        this.addKeyListener(this);

        //初始化全部的图片
        StaticValue.init();

        //创建全部的场景
        for(int i=1;i<=3;i++) {
            this.allBG.add(new BackGround( i, i == 3 ? true : false));
        }
        //将第一个场景设置为当前场景
        this.nowBG = this.allBG.get(0);

        mario = new Mario(0,600-2*60);
        this.mario.setBg(nowBG);
//		//初始化Mario对象
//		this.mario = new Mario(0,480);
//
//		//将场景放入mario对象的属性中
//		this.mario.setBg(nowBG);
//
//
//
//		this.addKeyListener(this);
//		//键位编码：← 37  → 39  空格 32
//
        t.start();
        this.repaint();


        //关闭后结束
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        //建立临时的缓冲图片

        BufferedImage image = new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g2 = image.getGraphics();

        if(this.isStart) {
            //绘制背景
            g2.drawImage(this.nowBG.getBgImage(), 0 , 0 , this );

            g2.drawString("生命:"+this.mario.getLife(),700 ,60);

            //绘制敌人
            Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
            while(iterEnemy.hasNext()) {         //-------------en
                Enemy en= iterEnemy.next();
                g2.drawImage(en.getShowImage(),en.getX(),en.getY(),this);
            }

            //绘制障碍物
            Iterator<Obstruction> iter = this.nowBG.getAllObstruction().iterator();
            while(iter.hasNext()) {
                Obstruction ob = iter.next();
                g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
            }

            //绘制mario
            g2.drawImage(this.mario.getShowImage(),this.mario.getX(),this.mario.getY(),this);

        } else {
            g2.drawImage(StaticValue.startImage,0,0,this) ;
        }

        //把缓冲图片绘制到窗体中
        g.drawImage(image,0,0,this);
    }
    /**
     * 当点击键盘上的某一个键时
     */
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
//		返回键盘上的值
//		System.out.println(e.getKeyChar());
//		//返回键盘上字符的编码值 左37 右39 空格32
//		System.out.println(e.getKeyCode());
        if(this.isStart) {
            //当按下39是（-->）mario向右移动
            if(e.getKeyCode() == 39) {
                this.mario.rightMove();
            }
            //当按下37是（<--）mario向左移动
            if(e.getKeyCode() == 37) {
                this.mario.leftMove();
            }
            //当按下32时，空格键，mario开始 跳跃
            if(e.getKeyCode() == 32) {
                this.mario.jump();
            }
        } else {
            if(e.getKeyCode() == 32) {
                isStart = true ;
                this.nowBG.enemyStartMove();
            }
        }
    }
    /**
     * 当抬起键盘上的某一个键时
     */
    public void keyReleased(KeyEvent ke)
    {
        // TODO Auto-generated method stub
        if(this.isStart) {
            //当抬起37时（-->）时，mario停止向左移动
            if(ke.getKeyCode()==39) {
                this.mario.rightStop();
            }
            //当抬起37时（<--），mario停止向右移动
            if(ke.getKeyCode()==37) {
                this.mario.leftStop();
                this.mario.setScore(0);
                this.mario.setLife(3);
            }
        }
    }
    /*
     * 当通过键盘输入一些信息时
     * */
    public void keyTyped(KeyEvent e)
    {
        // TODO Auto-generated method stub
    }

    public void run() {
        // TODO Auto-generated method stub
        while(true) {
            this.repaint();
            try {
                Thread.sleep(60);
                if(this.mario.getX() >= 840) {
                    //切换场景
                    this.nowBG = this.allBG.get(this.nowBG.getSort());
                    //将当前场景设置mario中
                    this.mario.setBg(this.nowBG);
                    //将场景中的敌人移动
                    this.nowBG.enemyStartMove();
//					//修改mario的坐标
                    this.mario.setX(0);
                }
                if(this.mario.isDead()) {
                    JOptionPane.showMessageDialog(this,"游戏结束，mario死亡！");
                    System.exit(0);
                }
                if(this.mario.isClear()) {
                    JOptionPane.showMessageDialog(this,"恭喜你取得胜利！");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
