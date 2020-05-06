package main;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.awt.windows.WPrinterJob;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import java.math.*;


public class Player extends gameObject {
    Random r= new Random();
    private Handler handler;
    private int o = 0;
    private int Celebrate = 0;
    private int DoomsDayInit = 1;
    public Player(int x, int y, ID id, Handler handler, int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
    }

    public double ang;

    public void tick() {
        x += velX;
        y += velY;
        if (y - 4 < 0) y = 0;
        if (y + 4 > 702) y = 702;
        if (x - 4 < 0) x = 0;
        if (x + 4 > 1322) x = 1322;
        Handler handler = new Handler();
        Random r = new Random();
        if(score > 9999){
            Celebrate = 1;
        }
    }

    int i = 0;
    public void render(Graphics g) {
        if(Celebrate == 1){
            g.setColor(Color.orange);
            g.fillRect(0,0,1366,768);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.setColor(Color.gray);
            g.drawString("You Beat the Game", 650, 600);
        }
        Point p = MouseInfo.getPointerInfo().getLocation();
        float mouseX = p.x - (x+133);
        float mouseY = p.y - (y+83);
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        BufferedImage icon = null;
        ang = Math.atan2(mouseY, mouseX);
        at.rotate(ang, 18, 18);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.BLUE);
        g2d.drawString("Score:" + String.valueOf(score), x+80, y+50);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        if(score > 149 && score < 175){
            g.drawString("You have a DoomsDay", x-50, y-100);
        }
        if(score > 99 && score < 125){
            g.drawString("You have a Taser and Sniper", x-50, y-100);
        }
        if(score > 49 && score < 75){
            g.drawString("You have a Rifle, scroll to switch weapons", x-50, y-100);
        }
        if(damage == 3){
            g.drawString("Right-Click to switch to a StunGun", 1170, 730);
        }
        if(damage == 10){
            g.drawString("Right-Click to switch to a Taser", 1180, 730);
        }
        //Health Bar
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x-32, y+48, 104, 24);
        g2d.setColor(Color.GREEN);
        if(damage == 0) {
            icon = LoadImage(".\\src\\GunGameSprites\\Pistol.png");
        }else if(damage == 1){
            icon = LoadImage(".\\src\\GunGameSprites\\Rifle.png");
        } else if(damage == 2){
            icon = LoadImage(".\\src\\GunGameSprites\\Sniper.png");
        } else if(damage == 3){
            icon = LoadImage(".\\src\\GunGameSprites\\Taser.png");
        } else if(damage == 4){
            icon = LoadImage(".\\src\\GunGameSprites\\DoomsDay.png");
        }else if(damage == 5){
            icon = LoadImage(".\\src\\GunGameSprites\\UsedDoomsDay.png");
        }else if(damage == 10){
            icon = LoadImage(".\\src\\GunGameSprites\\StunGun.png");
        }else if(damage == 100){
            icon = LoadImage(".\\src\\GunGameSprites\\TaserActive.png");
        }
        if (Health < 800 && Health > 500){
            g2d.setColor(Color.YELLOW);
        }else if (Health < 501 && Health > 300){
            g2d.setColor(Color.ORANGE);
        }else if (Health < 301){
            g2d.setColor(Color.RED);
        }
        g2d.fillRect(x-30, y+50, Health/10, 20);

        if (Health < 0){
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if (tempObject.getid() == ID.Player){
                    handler.object.remove(tempObject);
                }
            }
        }
        g2d.drawImage(icon, at, null);
    }



    private BufferedImage LoadImage(String FileName) {

        BufferedImage img = null;


        try{
            img = ImageIO.read(new File(FileName));
        } catch (IOException e){

        }
        return img;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
