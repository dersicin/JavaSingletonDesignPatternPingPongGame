/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author lenovo
 */
public class Renderer extends JPanel {
    
    private static final long serialVersionUID = 10;
    
    @Override
    protected void paintComponent(Graphics g){
      
        super.paintComponent(g);
        
        Pong.instance().render((Graphics2D) g);
        
        
    }
    
}
