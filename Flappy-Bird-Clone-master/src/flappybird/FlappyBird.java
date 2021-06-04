/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

/**
 *
 * @Triputee
 */
public class FlappyBird implements ActionListener, KeyListener {
    
    public static final int FPS = 60, WIDTH = 750, HEIGHT = 480;
    
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer t;
    
    private boolean paused;
    
    public void go() {
        frame = new JFrame("Flappy Bird");
        bird = new Bird();
        rects = new ArrayList<Rectangle>();
        panel = new GamePanel(this, bird, rects);
        frame.add(panel);
        
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        paused = true;
        
        t = new Timer(1000/FPS, this);
        t.start();
    }
    public static void main(String[] args) {
        new FlappyBird().go();
    }
    
    public static class Player {
        int score;
        String name;

        public Player(int s, String str) {
            score = s;
            name = str;
        }

    }
    
    public static class SortbyScore implements Comparator<Player> {

        @Override
        public int compare(Player o1, Player o2) {
            return o2.score - o1.score;
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            bird.physics();
            if(scroll % 90 == 0) {
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));
                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            boolean game = true;
            for(Rectangle r : rects) {
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.contains(bird.x, bird.y)) {
//                    JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+time+".");
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;

            if(bird.y > HEIGHT || bird.y+bird.RAD < 0) {
                game = false;    
            }

            if(!game) {
            	ArrayList<Player> al = new ArrayList<Player>();
                rects.clear();
                bird.reset();
                String name;
                name = JOptionPane.showInputDialog("Enter Your Name");
                JOptionPane.showMessageDialog(frame, name+"\nYou lose!\n"+"Your score was: "+getScore()+"." );
                System.out.println("Name :"+name+"\nScore is "+getScore());
                try {
                    FileWriter myWriter = new FileWriter("score.txt",true);
                    myWriter.write(name+"-");
                    myWriter.write(String.valueOf(getScore()));
                    myWriter.write("\r\n");
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } 
                catch (IOException e1) {
                    System.out.println("An error occurred.");
                }
                
                try{
                    BufferedReader in = new BufferedReader(new FileReader("score.txt"));
                    String s = in.readLine();
                    while(s!=null){
                        String[] strScores = s.split("-");
                        int ab = Integer.parseInt(strScores[1]);
                        // System.out.println(ab);
                        al.add(new Player(ab, strScores[0]));
                        s=in.readLine();
                    }
                }
                catch (IOException e1) {
                    System.out.println("An error occurred.");
                }
                
                try {
                    new FileWriter("score1.txt", false).close();
                } catch (IOException e1) {
                    //TODO: handle exception
                    System.out.println("An error occurred.");
                }
                
                Collections.sort(al, new SortbyScore());
                
                for (int i = 0; i < al.size(); i++) {
                    System.out.println("Name is "+al.get(i).name + "\nScore : " + al.get(i).score);
                     try {
                        FileWriter myWriter = new FileWriter("score1.txt",true);
                        myWriter.write(al.get(i).name+"-");
                        myWriter.write(String.valueOf(al.get(i).score));
                        myWriter.write("\r\n");                    
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    }
                    catch (IOException e1) {
                        System.out.println("An error occurred.");
                    }
                    
                }
                
                //For Creating Table
                Font font1 = new Font("Product Sans", Font.BOLD, 18);
                JFrame frame1 = new JFrame("ScoreBoard");
                JLabel l = new JLabel("Scoreboard");
                l.setFont(font1);
                l.setBounds(150, 10, 100, 30);
                frame1.add(l);
                String data[][]=new String[al.size()][2];
                for(int i=0;i<al.size();i++) {
                	data[i][0] = al.get(i).name;
                	data[i][1] = String.valueOf(al.get(i).score);
                }
                String column[]= {"Name","Score"};
                JTable jt = new JTable(data,column);
//                jt.setBounds(10,60,200,300);
                JScrollPane sp = new JScrollPane(jt);
                sp.setBounds(30, 60, 300, 200);
                frame1.add(sp);
                
//				For Simple Text                
//                JLabel[] label = new JLabel[al.size()];
//                for (int i = 0; i < al.size(); i++) {
////                    System.out.println("Name is "+al.get(i).name + "\nScore : " + al.get(i).score);
//                    label[i] = new JLabel(al.get(i).name+"     "+al.get(i).score);
//                    label[i].setBounds(50, ((i+1)*30)+30 , 100, 20);
//                }
////                JLabel l1 = new JLabel("Label Text 1");
////                l1.setBounds(50, 100, 100, 30);
//                for(int i = 0; i < al.size(); i++) {
//                	frame1.add(label[i]);
//                }
                frame1.setSize(400,400);
                frame1.setLayout(null);
                frame1.setVisible(true);
                
                scroll = 0;
                paused = true;
                time = 0;
                
            }
        }
        else {
            
        }
    }
    
    public int getScore() {
        return time;
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            bird.jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
            paused = false;
        }
    }
    public void keyReleased(KeyEvent e) {
        
    }
    public void keyTyped(KeyEvent e) {
        
    }
    
    public boolean paused() {
        return paused;
    }
}
