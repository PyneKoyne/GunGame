package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    public static void main(String[] args){
        new Main().setVisible(true);

    }
    public static int x = 1366;
    public static int y = 768;
    private Main(){
        //Properties
        super("Defend Start Screen~ Kenny");
        setSize(x, y); //1024x768, 1600/900
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        //Start Button
        JButton button = new JButton("Start");
        button.addActionListener(this);
        button.setBounds(x/2 - 110, y/2 - 15, 220, 30);

        //Panel
        JPanel pnlButton = new JPanel();
        pnlButton.setBounds(x/5, y/5, 3*x/5, 3*y/5);
        pnlButton.setLayout(null);

        //adding button
        pnlButton.add(button);
        add(pnlButton);

    }






    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if (name.equals("Start")){
            GunGame.main();{
            }
            this.dispose();
        }

    }

}
