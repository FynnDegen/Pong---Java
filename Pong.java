import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A very ugly implementation of the game "Pong" I found on my computer.
 * I tried to comment it a bit.
 * Also this game is a bit buggy.
 * This game should probably work if you put this in BlueJ.
 * 
 * @author Fynn Degen
 * @version 1.1
 */
public class Pong extends JFrame implements KeyListener, Runnable
{
    // Arrays //
    
    int[] panel1Pos = new int[2];
    int[] panel2Pos = new int[2];
    int[] ballPos   = new int[2];
    int[] speed     = new int[2];
    int[] points    = new int[2];
    
    // Attributes //
    
    double random;
    
    //Double-Buffering
    
    private Image dbImage;
    private Graphics dbg;
    
    /**
     * Creates the game
     * 
     * @param String[] args
     */
    public static void main(String[] args)
    {
        new Pong();
    }
    
    /**
     * Creates the JFrame, initializes the arrays, adds the KeyListener and runs the game
     */
    public Pong()
    {
        // JFrame //
        
        setSize(700,500); // Size of the window
        setTitle("Pong"); // Name of the window
        setResizable(false);
        setVisible(true);
        
        // Arrays //
        
        panel1Pos[0] = 20;  // x-Position
        panel1Pos[1] = 150; // y-Position
        
        panel2Pos[0] = 680; // x-Position
        panel2Pos[1] = 150; // y-Position
        
        ballPos[0] = 325;   // x-Position
        ballPos[1] = 225;   // y-Position
        
        speed[0] = 5;  
        speed[1] = 5;
        
        points[0] = 0;      // Points of player 1
        points[1] = 0;      // Points of player 2
        
        // Else //
        
        addKeyListener(this);
        
        run();
    }
    
    /**
     * Double-Buffering for smooth transitions
     * 
     * @param Graphics g
     */
    public void paint(Graphics g)
    {
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }
    
    /**
     * Paints components
     * 
     * @param Graphics g
     */
    public void paintComponent(Graphics g)
    {
        super.paint(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0,panel1Pos[1],20,100);
        g.fillRect(680,panel2Pos[1],700,100);
        
        g.fillOval(ballPos[0],ballPos[1],50,50);
        
        g.drawString(points[0] + " : " + points[1],345, 70);
    }
    
    /**
     * No need to use
     * 
     * @param KeyEvent k
     */
    public void keyTyped(KeyEvent k)
    {
    
    }
    
    /**
     * Moves the panels of the players
     * 
     * @param KeyEvent k
     */
    public void keyPressed(KeyEvent k) 
    {
        if(k.getKeyCode() == KeyEvent.VK_W && (panel1Pos[1] >= 0))
        {
            panel1Pos[1] -= 20;
        }
        else if(k.getKeyCode() == KeyEvent.VK_S && (panel1Pos[1] <= 400))
        {
            panel1Pos[1] += 20;
        }
        else if(k.getKeyCode() == KeyEvent.VK_UP && (panel2Pos[1] >= 0))
        {
            panel2Pos[1] -= 20;
        }
        else if(k.getKeyCode() == KeyEvent.VK_DOWN && (panel2Pos[1] <= 400))
        {
            panel2Pos[1] += 20;
        }
    }
    
    /**
     * No need to use
     * 
     * @param KeyEvent k
     */
    public void keyReleased(KeyEvent k)
    {
        
    }
    
    /**
     * This method is called every frame (I think)
     */
    public void run()
    {
        while(true) 
        {
            try
            {
                ballPos[0] += speed[0];
                ballPos[1] += speed[1];
                
                random = Math.random()*2;
                random = random*2+0.5;
                random = Math.round(random);
                
                
                if(ballPos[0] >= 650)
                {
                    ballPos[0] = 325;
                    ballPos[1] = 225;
                    points[0]++;
                    speed[0] = 5;
                    speed[1] = 5;
                }
                else if(ballPos[0] <= 0)
                {
                    ballPos[0] = 325;
                    ballPos[1] = 225;
                    points[1]++;
                    speed[0] = 5;
                    speed[1] = 5;
                }
                
                if(ballPos[1] >= 450)
                {
                    speed[1] *= -1;
                }
                else if (ballPos[1] <= 0)
                {
                    speed[1] *= -1;
                }
                
                if((ballPos[0] <= panel1Pos[0]+20) && (ballPos[0] >= panel1Pos[0]) && (panel1Pos[1] <= ballPos[1]) && (panel1Pos[1]+100 >= ballPos[1])) // Hitbox of the panel
                {
                    speed[0] *= -1;
                    if(random == 2)
                    {
                        speed[1] *= -1;
                    }
                    speed[0]++;
                    speed[1]++;
                }
                else if((ballPos[0]+50 <= panel2Pos[0]) && (ballPos[0]+50 >= panel2Pos[0]-20) && (panel2Pos[1] <= ballPos[1]) && (panel2Pos[1]+100 >= ballPos[1])) // Hitbox of the panel
                {
                    speed[0] *= -1;
                    if(random == 1)
                    {
                        speed[1] *= -1;
                    }
                    speed[0]++;
                    speed[1]++;
                }
                
                Thread.currentThread().sleep(50); // Pauses the game for 50ms so the method doesn't run every frame (More FPS -> The game runs faster)
                
                repaint(); // repaints every component
                
            }
            catch(InterruptedException ie)
            {
        
            }
        }
    }
}