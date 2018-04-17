

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
        drawCurrentShape(g);
        
    }
    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

     private int squareWidth() {
        return getWidth() / NUM_COLS_ROWS;
    }

    private int squareHeight() {
        return getHeight() / NUM_COLS_ROWS;
    }

    private void drawCurrentShape(Graphics g) {
        int[][] squaresArray = nextShape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            drawSquare(g, 1 + squaresArray[point][1], 1 + squaresArray[point][0], nextShape.getShape());

        }
    }
    public Shape getNextTetrominoes(){
        return nextShape ;
    }
    
}
