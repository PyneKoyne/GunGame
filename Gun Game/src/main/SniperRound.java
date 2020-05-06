package main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SniperRound extends gameObject{
    private Handler handler;
    public int X = 0, Y = 0;
    private float checkX = 1, checkY = 1;
    private int start;

    public SniperRound(int x, int y, ID id, Handler handler ,int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
        for (int i = 0; i < handler.object.size(); i++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                X = tempObject.getX();
                Y = tempObject.getY();
            }
        }
        Point p = MouseInfo.getPointerInfo().getLocation();
        float mouseX = p.x - (X + 130);
        float mouseY = p.y - (Y + 73);
        this.handler = handler;
        float con = (float) (40 / Math.sqrt(Math.pow(((mouseX - x) / 10), 2) + Math.pow((mouseY - y) / 10, 2)));
        velX = Math.round(mouseX / 5 * con);
        velY = Math.round(mouseY / 5 * con);
        if(score == 2){
            Random r = new Random();
            velX = Math.round(mouseX/5* con)+ r.nextInt(25)*(r.nextBoolean() ? -1 : 1);
            velY = Math.round(mouseY/5* con)+ r.nextInt(25)*(r.nextBoolean() ? -1 : 1);
        }
        if(velY ==0) velY = 1;
        if(velX ==0) velX = 1;
        ang = Math.atan2(velY, velX);
        if((float) Math.abs(velY)/Math.abs(velX) > 1){
                checkX = 1;
                if(velY < 0) {
                    checkY = (float) -(velY / velX);
                } else{
                    checkY = (float) (velY / velX);
                }
                start = Math.round(Math.abs(velX));
        }
        if((float) Math.abs(velX)/Math.abs(velY) > 1){
            checkY = 1;
            if(velY < 0) {
                checkX = (float) -(velX / velY);
            } else{
                checkX = (float) (velX / velY);
            }
            start = Math.round(Math.abs(velY));
        }
        else{
            if(velY < 0) {
                checkX = (float) -(1);
            }
            start = Math.round(Math.abs(velX));
        }

    }

    public double ang;
    public void tick() {
        if (x == 0 && y == 0) {
            x = X;
            y = Y;
        }
        x += velX;
        y += velY;
        for (int i = 0; i < handler.object.size(); i++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Enemy1 || tempObject.getid() == ID.Enemy3 || tempObject.getid() == ID.Enemy2 ||tempObject.getid() == ID.SuperBoss ) {
                for(int j = 0; j < start; j ++) {
                    if (x + checkX*j > tempObject.getX() - 15 && y + checkY*j > tempObject.getY() - 15 && y + checkY*j < tempObject.getY() + 52 && x + checkX*j < tempObject.getX() + 58 && tempObject.getdamage() == 0) {
                        int N = tempObject.getHealth();
                        tempObject.setHealth(N - 8);
                        tempObject.setdamage(15);
                    }
                }
                if (tempObject.getHealth() < 0) {
                    handler.object.remove(tempObject);
                    for(int j = 0; j < handler.object.size(); j++){
                        gameObject tempObject1 = handler.object.get(j);
                        if(tempObject1.getid() == ID.Player){
                            tempObject1.setScore(tempObject1.getScore()+1);
                        }
                    }
                }
            }
        }
    }


    public void render(Graphics g) {

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        BufferedImage icon = LoadImage();
        at.rotate(ang, 18, 18);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(icon, at, null);
    }
    private BufferedImage LoadImage() {

        BufferedImage img = null;


        try{
            img = ImageIO.read(new File(".\\src\\GunGameSprites\\SniperBullet.png"));
        } catch (IOException ignored){
        }
        return img;
    }
}
