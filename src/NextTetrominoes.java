

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu53788313f
 */
public class NextTetrominoes extends JPanel {
    
    private Shape nextShape;
    
    private static final int NUM_COLS_ROWS = 4;

    public NextTetrominoes() {
        super();
        nextShape = Shape.getRandomShape(); 
        this.setVisible(false);
    }
    
    public void generateNextTetrominoes(){
        this.setVisible(true);
        nextShape = Shape.getRandomShape();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        nextShape.draw(g, 1 , 1, squareWidth(),squareHeight());
        
    }
    

    private int squareWidth() {
        return getWidth() / NUM_COLS_ROWS;
    }

    private int squareHeight() {
        return getHeight() / NUM_COLS_ROWS;
    }

    public Shape getNextTetrominoes(){
        return nextShape ;
    }
    
}
