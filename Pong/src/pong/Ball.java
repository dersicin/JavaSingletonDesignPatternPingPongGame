package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Ball {
    
    public int x, y, genislik = 25, yükseklik = 25;
    public int motionX, motionY;
    public Random random;
    
    private Pong pong;
    
    public int vurusMiktari;
    
    public Ball(Pong pong)
    {
        this.pong = pong;
        
        this.random = new Random();
     
        spawn();
    }
    
    public void update(Paddle paddle1, Paddle paddle2)
    {
        int hiz = 5;
        
        this.x += motionX * hiz;
        this.y += motionY * hiz;
      
        
        //topun temas etmesi ve sekmesi
        if (this.y + yükseklik - motionY > pong.yükseklik || this.y + motionY <0)
        {
            if(this.motionY < 0)
            {
                this.y = 0;
                this.motionY = random.nextInt(4);
                
                if (motionY == 0)
                {
                    motionY = 1;
                }
            }
            else
            {
                this.motionY = -random.nextInt(4);
                this.y = pong.yükseklik - yükseklik;
                
                if (motionY == 0)
                {
                    motionY = -1;
                }
            }
        }
        
       if (checkCollision(paddle1) == 1)
       {
           this.motionX = 1 + (vurusMiktari / 5);
           this.motionY = -2 + random.nextInt(4);
           
           if (motionY == 0)
           {
               motionY = 1;
           }
           
           vurusMiktari++;
       }
       else if (checkCollision(paddle2) == 1)
       {
           this.motionX = -1 - (vurusMiktari / 5);
           this.motionY = -2 + random.nextInt(4);
           
           if (motionY == 0)
           {
               motionY = 1;
           }
           vurusMiktari++;
       }
       
       if (checkCollision(paddle1) == 2)
       {
           paddle2.skor++;
           spawn();
       }
       else if (checkCollision(paddle2) == 2)
       {
           paddle1.skor++;
           spawn();
       }
    }
    
    public void spawn()
    {
        this.vurusMiktari = 0;
        this.x = pong.genislik / 2 - this.genislik / 2;
        this.y = pong.yükseklik / 2 - this.yükseklik / 2;
        
        //top hareketi için
        this.motionX = -2 + random.nextInt(4);
        
        if (motionY == 0)
        {
            motionY = 1;
        }
        if (random.nextBoolean())
        {
            motionX = 1;
        }
        else
        {
            motionX = -1;
        }
        
    }
    
    public int checkCollision(Paddle paddle)
    {
        if (this.x < paddle.x + paddle.genislik && this.x + genislik  > paddle.x && this.y < paddle.y + paddle.yükseklik && this.y + yükseklik > paddle.y)
        {
            return 1; //sektirme
        }
        else if ((paddle.x > x  && paddle.paddleNumber == 1) || (paddle.x < x - genislik && paddle.paddleNumber == 2))
        {
            return 2; //skor
        }
        return 0; //hiçbir etki yok.
    }
    
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, genislik, yükseklik);
    }
}
