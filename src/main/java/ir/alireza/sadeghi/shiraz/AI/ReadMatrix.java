package ir.alireza.sadeghi.shiraz.AI;

import java.io.*;

public class ReadMatrix {

    public final static boolean DEBUG = true;
    public final static String COMMENT = "//";

    //! n is the number of weights per mode
    private static int n = 8;
    //! m is the number of modes
    private static int m = 5;

    public static int gen;
    public static int FileNumber;

    public static String Slash;


    public static double[][] ReadIn( String File ) {

        double[][] WeightMatrix = new double[m][n];

        if( File.length() < 1 ) {
            System.out.println("Error! No filename specified.");
            System.exit(0);
        }

        String inputfile = File;

        int name = 0;
        int gen = 0;


        try 	{
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

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
                    System.out.println("Error! Malformed weight line: "+record);
                    System.exit(0);
                }

                for( int i=0; i<n; i++){
                    WeightMatrix[row][i] = Double.parseDouble( data[i]);
                }
                row++;
            }

            String surplus = br.readLine();
            if( surplus != null ) {
                if( surplus.length() >= n ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last weight: '"+surplus+"'");
            }

        }
        catch (IOException ex) {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file "+inputfile);

            System.exit(0);
        }
        return WeightMatrix;
    }

    public static void ReadOut(double[][] weights, String folder){
        FileNumber++;
        File doc = new File(System.getProperty("user.dir")+Slash+ "ir/alireza/sadeghi/shiraz/AI" +Slash+folder+Slash+"AInumber"+gen+"_"+FileNumber+".txt");

        System.out.println("Path : " + doc.getAbsolutePath());
        try{
            FileWriter fw = new FileWriter(doc,true);
            fw.write("AInumber: "+FileNumber);
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
            System.err.println("Couldn't log this");
        }
    }
}