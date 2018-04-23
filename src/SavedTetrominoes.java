
import java.awt.Graphics;
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
public class SavedTetrominoes extends JPanel {
    
    private Shape savedTetrominoes;
    private final int NUM_COLS_ROWS = 4;

    
    public SavedTetrominoes(){
        super();
        savedTetrominoes = new Shape(Tetrominoes.NoShape);

    }
    
    public void swapTetrominoes(Shape shape){
        Shape aux = savedTetrominoes;
        savedTetrominoes = shape;
        shape = aux;

        repaint();
    }
    
    public Shape getSavedTetrominoes(){
        return savedTetrominoes;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        savedTetrominoes.draw(g, 1 , 1, squareWidth(),squareHeight());
        
    }
    
    private int squareWidth() {
        return getWidth() / NUM_COLS_ROWS;
    }

    private int squareHeight() {
        return getHeight() / NUM_COLS_ROWS;
    }
}
