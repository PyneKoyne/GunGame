package main;

import java.awt.*;

public abstract class gameObject {

    protected int x, y;
    protected ID id;
    protected int velX, velY;
    protected int Health;
    protected int damage;
    protected int score;

    public gameObject(int x, int y, ID id, int Health, int damage, int score){
        this.x = x;
        this.y = y;
        this.id = id;
        this.Health = Health;
        this.damage = damage;
        this.score = score;

    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public void setId(ID id){
        this.id = id;
    }
    public ID getid(){
        return id;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }
    public void setVelY(int velY){
        this.velY = velY;
    }
    public int getVelX(){
        return velX;
    }

    public int getVelY(){
        return velY;
    }
    public void setHealth(int Health){
        this.Health = Health;
    }
    public int getHealth(){
        return Health;
    }
    public int getdamage(){
        return damage;
    }
    public void setdamage(int damage){
        this.damage = damage;
    }

    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }

}
