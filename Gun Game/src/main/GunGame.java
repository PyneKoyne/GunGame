
package main;
//Modules
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class GunGame extends Canvas implements Runnable{
    //Dimensions
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1366, HEIGHT = 768;

    //Variables
    private Thread thread;
    //If we are running
    private boolean running = false;
    //Our gameObject handler
    private final Handler handler;
    //Random Variable
    private Random r;
    //Weapon changer
    private int Weapon = 0;
    //time For DoomsDay
    private long timeDoom = 0;


    //To start and stop the loop
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //Main Class
    public GunGame(){
        handler = new Handler();
        //Starts you with a Pistol
        handler.addObject(new Pistol(0, 0, ID.Pistol, 0, 1, 0, System.currentTimeMillis(), handler));
        //Adds KeyInputs
        this.addKeyListener(new KeyInput(handler));
        //Mouse Events
        this.addMouseWheelListener(e -> {
            //Swaps your weapon every time you scroll with your scroll wheel
            //Weapon goes up every time you scroll but it uses the player score to see if it has to come back down to zero
            Weapon ++;
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player && tempObject.getScore() > 0 && Weapon > 4) {
                    Weapon = 0;
                }else if(tempObject.getid() == ID.Player && tempObject.getScore() > 0 && Weapon > 3 && tempObject.getScore() < 0) {
                    Weapon = 0;
                } else if(tempObject.getid() == ID.Player && tempObject.getScore() > 0 && Weapon > 1 && tempObject.getScore() < 0){
                    Weapon = 0;
                } else if(tempObject.getid() == ID.Player && tempObject.getScore() < 0 && Weapon > 0){
                    Weapon = 0;
                }
            }
            //Removes all the weapons just in case
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Pistol){
                    handler.removeObject(tempObject);
                }
                if(tempObject.getid() == ID.Rifle){
                    handler.removeObject(tempObject);
                }
                if(tempObject.getid() == ID.Sniper){
                    handler.removeObject(tempObject);
                }
                if(tempObject.getid() == ID.Taser){
                    handler.removeObject(tempObject);
                }
                if(tempObject.getid() == ID.Player){
                    tempObject.setdamage(Weapon);
                }
                if(tempObject.getid() == ID.DoomsDay){
                    handler.removeObject(tempObject);
                }
            }
            //Adds your weapon
            if(Weapon == 0){
                handler.addObject(new Pistol(0, 0, ID.Pistol, 0, 1, 0, System.currentTimeMillis(), handler));
            }else if(Weapon == 1){
                handler.addObject(new Rifle(0, 0, ID.Rifle, 0, 1, 0, System.currentTimeMillis()- 200, handler));
            }else if(Weapon == 2){
                handler.addObject(new Sniper(0, 0, ID.Sniper, 0, 1, 0, System.currentTimeMillis()- 1000, handler));
            }else if(Weapon == 3){
                handler.addObject(new Taser(0, 0, ID.Taser, 0, 1, 0, System.currentTimeMillis()- 300, handler));
            }else if(Weapon == 4){
                handler.addObject(new DoomsDay(0, 0, ID.DoomsDay, 0, 0, 0, timeDoom, handler));
            }
        });
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }
            //If pressed it sets a boolean to true so it continuously knows if the mouse is pressed
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    for(int i = 0; i < handler.object.size(); i ++) {
                        gameObject tempObject = handler.object.get(i);
                        if (tempObject.getid() == ID.Pistol) {
                            tempObject.setScore(1);
                        }
                        if (tempObject.getid() == ID.Rifle) {
                            tempObject.setScore(1);
                        }
                        if (tempObject.getid() == ID.Sniper) {
                            tempObject.setScore(1);
                        }
                        if (tempObject.getid() == ID.Taser) {
                            tempObject.setScore(1);
                        }
                        if (tempObject.getid() == ID.StunGun) {
                            tempObject.setScore(1);
                        }
                        if (tempObject.getid() == ID.DoomsDay) {
                            handler.removeObject(tempObject);
                            handler.addObject(new DoomsDay(0, 0, ID.DoomsDay, 0, 0, 1, timeDoom, handler));
                        }
                    }
                }
            }

            //If pressed it sets a boolean to false so it knows when the mouse is not pressed
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    for (int i = 0; i < handler.object.size(); i++) {
                        gameObject tempObject = handler.object.get(i);
                        if (tempObject.getid() == ID.Pistol) {
                            tempObject.setScore(0);
                        }
                        if (tempObject.getid() == ID.Rifle) {
                            tempObject.setScore(0);
                        }
                        if (tempObject.getid() == ID.Sniper) {
                            tempObject.setScore(0);
                        }
                        if (tempObject.getid() == ID.Taser) {
                            tempObject.setScore(0);
                        }
                        if (tempObject.getid() == ID.StunGun) {
                            tempObject.setScore(0);
                        }
                        if (tempObject.getid() == ID.DoomsDay) {
                            tempObject.setScore(0);
                        }
                    }
                }
                //If you have a Taser or StunGun it swaps to the other one
                if(e.getButton() == MouseEvent.BUTTON3){
                    for (int i = 0; i < handler.object.size(); i++) {
                        gameObject tempObject = handler.object.get(i);
                        if(tempObject.getid() == ID.Taser){
                            //Removes the Taser First
                            handler.removeObject(tempObject);
                            //Adds the StunGun
                            handler.addObject(new StunGun(0, 0, ID.StunGun, 0, 1, 0, System.currentTimeMillis()- 500, handler));
                            //Changes the weapon Image
                            for(int j = 0; j < handler.object.size(); j ++){
                                gameObject PlayerObject = handler.object.get(j);
                                if(PlayerObject.getid() == ID.Player){
                                    PlayerObject.setdamage(10);
                                }
                                //There was a bug where if you scroll and left click at teh same time you can get a pistol StunGun
                                if(PlayerObject.getid() == ID.Pistol){
                                    handler.removeObject(PlayerObject);
                                }
                            }
                            break;

                        }else if(tempObject.getid() == ID.StunGun){
                            //Removes the StunGun First
                            handler.removeObject(tempObject);
                            //Adds the Taser
                            handler.addObject(new Taser(0, 0, ID.Taser, 0, 1, 0, System.currentTimeMillis()- 500, handler));
                            //Changes the weapon Image
                            for(int j = 0; j < handler.object.size(); j ++){
                                gameObject PlayerObject = handler.object.get(j);
                                if(PlayerObject.getid() == ID.Player){
                                    PlayerObject.setdamage(3);
                                }
                                //There was a bug where if you scroll and left click at teh same time you can get a pistol StunGun
                                if(PlayerObject.getid() == ID.Pistol){
                                    handler.removeObject(PlayerObject);
                                }
                            }
                            break;
                        }
                    }
                }

            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
        //Starts the Window
        new Window(WIDTH, HEIGHT, "Defend ~ Kenny", this);
        //Places the Player
        handler.addObject(new Player(WIDTH/8, HEIGHT/2-30, ID.Player, handler,1000, 0, 0));

    }

    //Game Loop
    public void run(){
        //SpawnMob is to spawn the Boss
        int SpawnMob = 0;
        //These three are to give the Player a new Weapon when they reach a certain score
        int rifleInit = 1;
        int sniperInit = 1;
        int DoomsDayInit = 1;
        //There are variables for the Game Loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        //These three are the chances for the Mobs
        int time = 140;
        int time1 = 130;
        int time2 = 125;
        //This is for the mob spawn cycle
        int total = 3;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            //Deletes any game Objects that go offScreen
            for(int i = 0; i < handler.object.size(); i ++) {
                gameObject tempObject = handler.object.get(i);
                if (tempObject.getX() > 1400 || tempObject.getY() > 800 || tempObject.getY() < 0 || tempObject.getX() < 0) {
                    handler.object.remove(tempObject);
                }
                //If the Player score is 50 or greater it gives the Player a Rifle
                if(tempObject.getid() == ID.Player && tempObject.getScore() > 49 && rifleInit == 1){
                    handler.addObject(new Rifle(0, 0, ID.Rifle, 0, 0, 0, System.currentTimeMillis(), handler));
                    tempObject.setdamage(1);
                    rifleInit = 0;
                }
                //If a Player has a score of 100 or greater it gives the Player a Sniper
                if(tempObject.getid() == ID.Player && tempObject.getScore() > 99 && sniperInit == 1){
                    handler.addObject(new Sniper(0, 0, ID.Sniper, 0, 0, 0, System.currentTimeMillis(), handler));
                    tempObject.setdamage(2);
                    sniperInit = 0;
                }
                //Spawns the Boss
                if(tempObject.getid() == ID.Player && tempObject.getScore() > 199 && SpawnMob == 0){
                    handler.addObject(new SuperBoss(r.nextInt(1366), r.nextInt(768), ID.SuperBoss, handler, 35, 0, 0));
                    SpawnMob = 1;
                }
                //Resets the timer for DoomsDay
                if(tempObject.getid() == ID.DoomsDay && tempObject.getdamage() == 1){
                    timeDoom = System.currentTimeMillis();
                    tempObject.setdamage(0);
                }
            }

            if(System.currentTimeMillis()-timer > 1000) {
                //Spawn Mobs
                timer += 1000;
                r = new Random();
                //The Mob Cycle
                if (total == 3) {
                    for(int j = 0; j < handler.object.size(); j ++){
                        gameObject tempObject = handler.object.get(j);
                        if(tempObject.getid() == ID.Player && tempObject.getHealth() < 1000){
                            tempObject.setHealth(tempObject.getHealth()+4);
                        }
                    }
                    //The chance for an Enemy1 to Spawn
                    for (int i = 0; i < r.nextInt((2 + (time / 70))); )
                        handler.addObject(new Enemy1(r.nextInt(1366), r.nextInt(768), ID.Enemy1, handler, 1, 0, 0));
                    //The Chance for a Enemy3 to spawn
                    for (int i = 0; i < r.nextInt(1 + (time1 / 70)); )
                        handler.addObject(new Enemy3(r.nextInt(1366), r.nextInt(768), ID.Enemy3, handler, 3, 0, 0));
                    //The Chance for an Enemy 2 to spawn
                    for (int i = 0; i < r.nextInt(1 + (time2 / 70)); )
                        handler.addObject(new Enemy2(r.nextInt(1366), r.nextInt(768), ID.Enemy2, handler, 6, 0, 0));
                    //Resets the loop and adds one seventieth to the mobs chance
                    total = 0;
                    time++;
                    time1++;
                    time2++;
                    if(time2 < 140){
                        time1 --;
                        time --;
                    }
                }
                //Prints the FPS into the console
                System.out.println("FPS: " + frames);
                frames = 0;

                total++;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }
    private void render(){
        // To render the Screen every frame
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        //Sets the background for the game
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //Renders the Game
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public static void main(){
        new GunGame();

    }

}