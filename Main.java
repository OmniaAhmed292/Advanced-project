package com.company;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        //CSV = Comma-Separated Values
        //   text file that uses a comma to separate values

        String file = "C:/Users/DELL 3550/Downloads/ENG.Rana/Second Year/Second Term/Java OOP/Example.csv";
        BufferedReader reader = null;
        var line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null) {

                //String[] row = line.split(",");
                //use this if your values already contain commas
                String[] row = line.split(",(?=([^\"]\"[^\"]\")[^\"]$)");

                for(String index : row) {
                    System.out.printf("%-10s", index);
                }
                System.out.println();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
