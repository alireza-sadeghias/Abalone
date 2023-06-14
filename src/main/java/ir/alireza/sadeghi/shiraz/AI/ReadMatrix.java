package ir.alireza.sadeghi.shiraz.ai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class ReadMatrix {

    private final static Logger logger = LogManager.getLogger(ReadMatrix.class);
    public final static String COMMENT = "//";

    //! n is the number of weights per mode
    private static int n = 8;
    //! m is the number of modes
    private static int m = 5;

    public static int gen;
    public static int fileNumber;

    public static String slash;


    public static double[][] readIn(String filePath ) {

        double[][] WeightMatrix = new double[m][n];

        if(filePath==null || filePath.length() < 1 ) {
            logger.error("Error! No filename specified.");
            System.exit(0);
        }

        int name = 0;
        int gen = 0;


        try 	{
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String record;

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = br.readLine()) != null)
            {
                if( record.startsWith("//") ) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if( record.startsWith("AInumber: ")) {
                name = Integer.parseInt( record.substring(10) );
            }

            record = br.readLine();

            if( record.startsWith("Gen: ")) {
                gen = Integer.parseInt( record.substring(5) );
            }

            int row =0;
            for( int d=0; d<m; d++) {
                record = br.readLine();
                String data[] = record.split(" ");
                if( data.length != n ) {
                    logger.error("Error! Malformed weight line: "+record);
                    System.exit(0);
                }

                for( int i=0; i<n; i++){
                    WeightMatrix[row][i] = Double.parseDouble( data[i]);
                }
                row++;
            }

            String surplus = br.readLine();
            if( surplus != null ) {
                if( surplus.length() >= n ) {
                    logger.trace(COMMENT + " Warning: there appeared to be data in your file after the last weight: '" + surplus + "'");
                }
            }

        } catch (IOException ex) {
            logger.error(ex);
            // catch possible io errors from readLine()
            logger.error("Error! Problem reading file {}", filePath);
            System.exit(0);
        }
        return WeightMatrix;
    }

    public static void readOut(double[][] weights, String folder){
        fileNumber++;
        File doc = new File(System.getProperty("user.dir")+ slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + slash +folder+ slash +"AInumber"+gen+"_"+ fileNumber +".txt");

        logger.trace("Path : " + doc.getAbsolutePath());
        try{
            FileWriter fw = new FileWriter(doc,true);
            fw.write("AInumber: "+ fileNumber);
            fw.write(System.lineSeparator());
            fw.write("Gen: "+gen);
            fw.write(System.lineSeparator());
            for (int k=0; k<m; k++){
                for (int l=0; l<n; l++){
                    fw.write(weights[k][l]+" ");
                }
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException ex){
            logger.error("Couldn't log this");
        }
    }
}