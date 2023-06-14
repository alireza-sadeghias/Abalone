package ir.alireza.sadeghi.shiraz.ai.weight.optimisation;

import ir.alireza.sadeghi.shiraz.Move;
import ir.alireza.sadeghi.shiraz.ai.AutomaticGamePlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameEnvironment {

    private final static Logger logger = LogManager.getLogger(GameEnvironment.class);
    public static int limit = 500;
    public static double[][] player1;
    public static double[][] player2;

    public static int[] prepare(double[][] p1, double[][] p2, int Trainer) {
        int[] result;
        player1 = p1;
        player2 = p2;

        //important note:
        //this works together with our visual representation
        //the boolean 'need' in src/ir.alireza.sadeghi.shiraz.Move.java should be set to true
        //can loop through them as well when you play multiple games, but the AI needs to be set BEFORE you run the automatic game play
        //ALSO before you run this, you NEED to have pressed the "do AI move" button if you haven't done a move!!
        //which seems annoying but making this automatic does not work, I have tried it, believe me
        //it will set the initial board, so it is possible to clone it
        //after this, when you run the playGame methods, everything should run automatically

        result = AutomaticGamePlay.playGame(Move.initialBoard);

        result[1] = result[1] * Trainer;
        logger.trace("Results:  Moves: "+result[0]+"  Score: "+result[1]);
        return result;
    }
}
