
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor
 */
public class Board extends JPanel implements ActionListener {

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:

                    if (canMoveTo(currentShape, currentRow, currentCol - 1) && !pause) {
                        currentCol--;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMoveTo(currentShape, currentRow, currentCol + 1) && !pause) {
                        currentCol++;
                    }
                    break;
                case KeyEvent.VK_UP:
                    Shape rotShape = currentShape.rotateRight();
                    if (canMoveTo(rotShape, currentRow, currentCol) && !pause) {
                        currentShape = rotShape;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMoveTo(currentShape, currentRow + 1, currentCol) && !pause) {
                        currentRow++;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!pause) {
                        pause = true;
                        timer.stop();
                    } else {
                        pause = false;
                        timer.start();
                    }
                    break;
                default:
                    break;

            }
            repaint();
        }

    }

    public IncrementScore scoreDelegete;

    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int INIT_ROW = -2;

    private Tetrominoes[][] matrix;
    private int deltaTime;

    private Shape currentShape;

    private int currentRow;
    private int currentCol;

    private Timer timer;
    private boolean pause;
    public boolean hardMode;
    public boolean easyMode;
    public boolean mediumMode;
    
    private NextTetrominoes nextTetrominoes;

    MyKeyAdapter keyAdepter;

    final JFrame frame = new JFrame("Tetris Game");

    public Board() {
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();
        timer = new Timer(deltaTime, this);
        keyAdepter = new MyKeyAdapter();
    }
    
    public void setNextTetrominoes(NextTetrominoes nextTetrominoes){
        this.nextTetrominoes=nextTetrominoes;
    }

    public void setScore(IncrementScore scorer) {
        this.scoreDelegete = scorer;
    }
    
    public void decrementDeltaTime(){
        if(easyMode){
            deltaTime = deltaTime -1; 
        }    
        if(mediumMode){
            deltaTime = deltaTime -5;
        }    
        if(hardMode){
            deltaTime = deltaTime -10;
        }
    }

    public void initValues() {
        setFocusable(true);
        cleanBoard();
        deltaTime = 500;
        currentShape = null;
        currentRow = INIT_ROW;
        currentCol = NUM_COLS / 2;
        pause = false;
    }

    public void initGame() {
        initValues();
        currentShape = new Shape();
        addKeyListener(keyAdepter);
        timer.start();
        scoreDelegete.reset();
    }

    public void cleanBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }

    private boolean canMoveTo(Shape shape, int newRow, int newCol) {
        if ((newCol + shape.getXmin() < 0)
                || (newCol + shape.getXmax() >= NUM_COLS)
                || (newRow + shape.getYmax() >= NUM_ROWS) || hitWithMatrix(currentShape, newRow, newCol)) {
            return false;
        }
        return true;
    }

    private boolean hitWithMatrix(Shape shape, int newRow, int newCol) {
        int[][] squaresArray = shape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            int row = newRow + squaresArray[point][1];
            int col = newCol + squaresArray[point][0];
            if (row >= 0) {
                if (matrix[row][col] != Tetrominoes.NoShape) {
                    return true;
                }
            }
        }
        return false;
    }

    private void completeLine() {
        for (int i = 0; i < NUM_ROWS; i++) {
            int counter = 0;
            for (int j = 0; j < NUM_COLS; j++) {
                if (matrix[i][j] == Tetrominoes.NoShape) {
                    counter++;
                }

            }
            if (counter == 0) {
                removeLine(i);
            }
        }
    }

    private void removeLine(int i) {
        for (int row = i; row >= 0; row--) {
            if (row == 0) {
                for (int col = 0; col < NUM_COLS; col++) {
                    matrix[row][col] = Tetrominoes.NoShape;
                }
            } else {
                for (int col = 0; col < NUM_COLS; col++) {
                    matrix[row][col] = matrix[row - 1][col];
                }
            }
        }
        scoreDelegete.increment(100);
        repaint();
    }

    public void gameOver() {
        String message = "Youre Score is " + ScoreBoard.getScore() +" !!! " + '\n' + "Do you want To play Aagain?";
        String title = "Game Over";
        int reply = JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new Tetris();
            initGame();
        } else {
            System.exit(1);
        }
    }

    // Game Main Loop
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (canMoveTo(currentShape, currentRow + 1, currentCol)) {
            currentRow++;
            repaint();
            if(ScoreBoard.getScore() % 3 == 0){
                decrementDeltaTime();
            }
        } else {
            if (currentRow + currentShape.getYmin() < 0) {
                gameOver();
            } else {
                moveCurrentShapeToMatrix();
                currentShape = nextTetrominoes.getNextTetrominoes();
                nextTetrominoes.generateNextTetrominoes();
                currentRow = INIT_ROW;
                currentCol = NUM_COLS / 2;
                completeLine();
            }

        }

    }

    private void moveCurrentShapeToMatrix() {
        int[][] squaresArray = currentShape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            int row = currentRow + squaresArray[point][1];
            int col = currentCol + squaresArray[point][0];
            matrix[row][col] = currentShape.getShape();

        }
    }

    public void drawBoard(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                drawSquare(g, row, col, matrix[row][col]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (currentShape != null) {
            drawCurrentShape(g);
        }
        drawBorder(g);
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), (NUM_ROWS * squareHeight()) + 1);
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
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    private void drawCurrentShape(Graphics g) {
        int[][] squaresArray = currentShape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            drawSquare(g, currentRow + squaresArray[point][1], currentCol + squaresArray[point][0], currentShape.getShape());

        }
    }
    public Timer getTimer(){
        return timer;
    }

}
