package ir.alireza.sadeghi.shiraz.ai.weight.optimisation;

import ir.alireza.sadeghi.shiraz.ai.EvaluationFunction;
import ir.alireza.sadeghi.shiraz.ai.ReadMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EvolutionaryAlgo {

    private final static Logger logger = LogManager.getLogger(EvaluationFunction.class);

    public static int initialGenerationSize = 10;          // should be >10
    public static int modes = 5;
    public static int amountWeights = 8;
    private static int amountGames = 2;

    public static void initialGeneration() {

        ReadMatrix.fileNumber = 0;

        double[][] randWeightMatrix = new double[modes][amountWeights];

        for (int i=0; i<initialGenerationSize; i++) {


            for (int j = 0; j<modes; j++) {
                for (int k = 0; k < amountWeights; k++) {
                    randWeightMatrix[j][k] = Math.random();

                }
            }
            //Normalisation:
            for (int l = 0; l<modes; l++) {
                double sum = 0;
                for (int m = 0; m<amountWeights; m++) {

                    sum += randWeightMatrix[l][m];
                }
                for (int n = 0; n<amountWeights; n++) {
                    randWeightMatrix[l][n] = (randWeightMatrix[l][n]) / sum;
                }
            }
            ReadMatrix.readOut(randWeightMatrix,"Matrices");
            ReadResult.readOut();
        }
    }


    public static void selection(){
        for(int i=1; i<=initialGenerationSize; i++) {
            for(int j=1; j<4; j++){

                logger.trace("calling read in with :{}",System.getProperty("user.dir"));
                double[][] player1 =   ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" +ReadMatrix.slash +"Trainers"+ReadMatrix.slash + "AInumber" + ReadMatrix.gen +"_" +j + ".txt");
                double[][] player2 =   ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" +ReadMatrix.slash +"Matrices"+ReadMatrix.slash + "AInumber" + ReadMatrix.gen +"_" +i + ".txt");
                int Trainer=-1;

                for(int k=0; k<amountGames; k++){
                    if(k % 2 != 0){
                        double[][] placeholder = player1;
                        player1 = player2;
                        player2 = placeholder;
                        Trainer = Trainer*(-1);
                    }
                    int[] result = GameEnvironment.prepare(player1,player2,Trainer);
                    ReadResult.addResult(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" +ReadMatrix.slash +"Results"+ReadMatrix.slash + "AIResult" + ReadMatrix.gen +"_" +i +"_" +j+ ".txt",result);
                }
            }
        }
    }
}
