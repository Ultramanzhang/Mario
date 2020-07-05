package com.zhang.Mario;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class StaticValue
{
    public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>();

    public static BufferedImage startImage = null;

    public static BufferedImage endImage = null;

    public static BufferedImage bgImage = null;

    public static List<BufferedImage> allFlowerImage = new ArrayList<BufferedImage>();  //花的图片

    public static List<BufferedImage> allTriangleImage = new ArrayList<BufferedImage>(); //

    public static List<BufferedImage> allTurtleImage = new ArrayList<BufferedImage>();

    public static List<BufferedImage> allobstuctionImage = new ArrayList<BufferedImage>();

    public static BufferedImage marioDeadImage = null;

    public static String imagePath = System.getProperty("user.dir")+"/images/";

    //将全部的图片初始化
    public static void init() {
        //将所有的Mario图片保存到静态属性中---Mario的十张图片
        for(int i=1;i<=10;i++) {
            try {
                allMarioImage.add(ImageIO.read(new File(imagePath+i+".gif")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //导入全部背景
        try {
            //开始的背景
            startImage = ImageIO.read(new File(imagePath+"start.gif"));
            //当前场景的背景
            bgImage = ImageIO.read(new File(imagePath+"firststage.gif"));
            //结束时候的背景
            endImage = ImageIO.read(new File(imagePath+"firststageend.gif"));
            marioDeadImage=ImageIO.read(new File(imagePath+"over.gif"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //导入全部敌人
        for(int i=1;i<=5;i++) {
            try {
                if(i<=2) {
                    allFlowerImage.add(ImageIO.read(new File(imagePath+"flower"+i+".gif")));
                }
                if(i<=3) {
                    allTriangleImage.add(ImageIO.read(new File(imagePath+"triangle"+i+".gif")));
                }
                allTurtleImage.add(ImageIO.read(new File(imagePath+"Turtle"+i+".gif")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //导入障碍物的图片
        for(int i=1;i <= 12;i++) {
            try {
                allobstuctionImage.add(ImageIO.read(new File(imagePath+"ob"+i+".gif")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //导入Mario死亡的图片
//	     try {
//			marioDeadImage = ImageIO.read(new File(imagePath+"over.gif"));   //此处没有死亡图片
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}