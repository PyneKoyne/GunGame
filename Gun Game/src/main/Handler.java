package main;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    //A list of all the gameObjects
    LinkedList<gameObject> object = new LinkedList<gameObject>();

    public void tick(){
        for(int i = 0; i < object.size(); i ++){
            gameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }
    public void render(Graphics g){
        for(int i = 0; i < object.size(); i ++) {
            gameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }
    //Adds a gameObject to the list
    public void addObject(gameObject object){
        this.object.add(object);
    }
    //Removes a gameObject from the list
    public void removeObject(gameObject object){
        this.object.remove(object);
    }
}
