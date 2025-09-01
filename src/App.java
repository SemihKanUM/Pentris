import javax.swing.*;
import PentrisGame.*;
import java.awt.event.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;
import java.io.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws IOException {

        
        
        Bot bot = new Bot();
        bot.addParameter(Bot.NUM_HOLES);
        bot.addParameter(Bot.CENTER_HEIGHT);
        bot.addParameter(Bot.ROW_TRANSITIONS);
        bot.addParameter(Bot.COLUMN_TRANSITIONS);
        bot.addParameter(Bot.DEEPEST_WELL);
        bot.addParameter(Bot.MAX_HEIGHT);
        bot.setGenotype(new double[] {-5.0, -5.0, -4.21327562580109, -0.5663311444791244, -4.872798075149198, 1.8944890993796275});

        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JButton Bot = new JButton("BOT");
        panel.add(Bot);

        JButton player = new JButton("PLAYER");
        panel.add(player);
        JLabel arrow = new JLabel("==>");
        panel.add(arrow);

        JTextField name = new JTextField("Please enter your name!");
        panel.add(name);
        panel.setLayout(new FlowLayout());

        frame.add(panel);
        frame.setLocation(500, 300);
        frame.setSize(400,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(!(label.getText().equals("Bot") || label.getText().equals("Player"))){
            Bot.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    label.setText("Bot");
                    name.setText("Bot");
                }
            });

            player.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    label.setText("Player");
                }
            });
        }
         
        if(label.getText().equals("Bot")){
            frame.setVisible(false);
            Main main = new Main(bot , name.getText());
            main.run();
        }

        else if(label.getText().equals("Player")){
            frame.setVisible(false);
            Main main = new Main(null, name.getText());
            main.run();

        }  
    }
}
