
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */


public class ScoreBoard extends JLabel implements IncrementScore{
    
    private int score;
    
    public ScoreBoard() {
        super();
        score = 0;
        this.setText("Score : " + score);
    }
    
    public void increment(int points) {
        score += points;
        this.setText("Score : " + score);
    }
    
    public void reset() {
        score = 0;
        this.setText("Score : " + score);
    }
    
}
