
package pong;

import java.awt.Color;
import java.awt.Graphics;


public class Paddle {
    public int paddleNumber;
    //paddle boyutunu ayarlıyor.
    public int x, y, genislik = 50, yükseklik = 250;
    public int skor;
    
    public Paddle(Pong pong, int paddleNumber){
        this.paddleNumber = paddleNumber;
        
        if (paddleNumber == 1){
            this.x = 0;
            
        }
        if (paddleNumber == 2){
            this.x = pong.genislik - genislik;
            
        }
        this.y = pong.yükseklik / 2 - this.yükseklik / 2 ;
    }
    
    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, genislik, yükseklik);
        
    }

    public void hareketEt(boolean yukari) {
        
        //çubuğun hızını ayarlama
        int hiz = 55;
        
        if (yukari)
        {
            if (y - hiz > 0){
                y -= hiz;
            }
            else
            {
                y = 0;
            }
        }
        else
        {
            if(y + yükseklik + hiz < Pong.instance().yükseklik)
            {
                y += hiz;
            }
            else
            {
                y = Pong.instance().yükseklik - yükseklik ;
            }
        }
        
    }
}
