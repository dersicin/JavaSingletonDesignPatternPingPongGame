/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Pong implements ActionListener, KeyListener{

   private static volatile Pong pong =null;
   public int genislik = 900, yükseklik = 750;
   
   public Renderer renderer;
   
   public Paddle player1;
   public Paddle player2; 
   
   public Ball ball;
   
   //tuş ayarları için boolean degerler
   public boolean w, s, up, down;
   
   public int gameStatus = 0; //1- durdurma, 0-oyun beklemede 2-oyunbasliyor
   
   private Pong(){
       
       Timer timer = new Timer(20, this);
       JFrame jframe = new JFrame ("Ping-Pong");
       
       renderer = new Renderer();
       
       jframe.setSize(genislik + 18, yükseklik + 47);
       jframe.setVisible(true);
       jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jframe.add(renderer);
       jframe.addKeyListener(this);
       
       start();
       
       timer.start();      
 
   }
   
   public static Pong instance()
 {
  if(pong == null)
  {
      pong = new Pong();
      
  }
  return pong;
 } 
   
   public void start(){
       
       player1 = new Paddle(this, 1);
       player2 = new Paddle(this, 2);
       ball = new Ball(this);
   }
   
   public void update(){
       //
       if (w)
       {
           player1.hareketEt(true);  
       }
       if (s)
       {
           player1.hareketEt(false);  
       }
       if (up)
       {
           player2.hareketEt(true);  
       }
        if (down)
       {
           player2.hareketEt(false);  
       }
        
        ball.update(player1, player2);
   }
   
   public void render(Graphics2D g){
       g.setColor(Color.BLUE);
       g.fillRect(0, 0, genislik, yükseklik);
       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
       //Oyun yeni basladiginda gamestatus=0 
       if (gameStatus == 0)
       {
           g.setColor(Color.WHITE);
           //Pong yazisinin font ayarı.
           g.setFont(new Font("Times New Roman", 1, 50));
           g.drawString("PONG", genislik /2 - 75, 50);
           
           //oynamak için tuşa basin font ayari.
           g.setFont(new Font("Times New Roman", 1, 30));
           g.drawString("Oynamak için SPACE tuşuna basın.", genislik /2 - 300, yükseklik / 2 - 25);
           g.drawString("Sol Çubuk W-S, Sağ Çubuk YukarıAşağıOk Tuşları ile Oynatılır!", genislik /2 - 420, yükseklik / 2 + 25);
       }
       
       if (gameStatus == 2 || gameStatus == 1)
       {
        g.setColor(Color.WHITE);
        // orta çizgiyi daha da kalınlaştırdı.
        g.setStroke(new BasicStroke(5f));
        g.drawLine(genislik / 2, 0, genislik / 2, yükseklik);
        
        //ortadaki çember.
        g.setStroke(new BasicStroke(2f));
        g.drawOval(genislik / 2 - 150, yükseklik / 2 - 150, 300, 300);
        
        g.setFont(new Font("Times New Roman", 1, 50));
        g.drawString(String.valueOf(player1.skor), genislik /2 - 90, 50);
        g.drawString(String.valueOf(player2.skor), genislik /2 + 65, 50);
         
        player1.render(g);
        player2.render(g);
        ball.render(g);
       }
       
       //oyun durdurulunca ekranda belirecek text.
       if (gameStatus == 1)
       {
           g.setColor(Color.WHITE);
           g.setFont(new Font("Times New Roman", 1, 50));
           g.drawString("DURAKLATILDI", genislik / 2 - 182, yükseklik / 2 - 200);
       }
       
    }
  
   //Aynı anda tek uygulama penceresini garanti eder.
   public static void uygulamayiBaslat()
           {
                    java.awt.EventQueue.invokeLater(() -> {
                    ServerSocket once;
      try
      {
          once = new ServerSocket(1334);
          pong = Pong.instance();
          
      } catch (IOException ex) {
          JOptionPane.showMessageDialog(null, "Uygulama Açık!");
      }
  });
           }
   
   @Override
   public void actionPerformed(ActionEvent e){
       if (gameStatus == 2)
       {
           update();
       }
       renderer.repaint();
       
   }
    public static void main(String[] args) {   
        uygulamayiBaslat();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
      
    }

    @Override
    public void keyPressed(KeyEvent ke) {
       int id = ke.getKeyCode();
       
       if (id == KeyEvent.VK_W){
           w = true;
       }
        if (id == KeyEvent.VK_S){
           s = true;
       }
         if (id == KeyEvent.VK_UP){
           up = true;
       }
         if (id == KeyEvent.VK_DOWN){
           down = true;
       }
         
         if (id == KeyEvent.VK_SPACE)
         {
             if (gameStatus == 0)
             {
                 gameStatus = 2;
                
             }
            else if( gameStatus == 1)
             {
                 gameStatus = 2;
             }
            else if( gameStatus == 2)
             {
                 gameStatus = 1;
             }
         }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
          int id = ke.getKeyCode();
       
       if (id == KeyEvent.VK_W){
           w = false;
       }
        if (id == KeyEvent.VK_S){
           s = false;
       }
         if (id == KeyEvent.VK_UP){
           up = false;
       }
         if (id == KeyEvent.VK_DOWN){
           down = false;
       }
         
    }
    
    
}
