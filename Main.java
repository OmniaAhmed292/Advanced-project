package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
//Apache
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.application.*;
import java.awt.*;
import javafx.scene.layout.*;



public class Main extends Application {

    public void start(Stage primaryStage) throws IOException{
        int skippedRows=0; //we need them because some rows with no useful info are skipped

        //Create a file with our wanted path
        File file = new File("C:/Users/DELL 3550/Downloads/ENG.Rana/Second Year/Second Term/Java OOP/Example.xlsx");
        //raw data from the file
        FileInputStream fis = new FileInputStream(file);
        //convert the data into workbook format
        Workbook workbook = new XSSFWorkbook(fis);
        //we want to get the first sheet of the workbook
        Sheet  firstSheet = workbook.getSheetAt(0);
        //we need an object to iterate around the sheet we have
        Iterator<Row> iterator = firstSheet.iterator();
        //we make an array list for fields and other for parents
        ArrayList <Field> elements = new ArrayList<>();

        //While iterator still has rows we will iterate on them
        while (iterator.hasNext()) {
            //get the current row we are in
            Row nextRow = iterator.next();
            //we will iterate on the cells of each row
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            //we had to do this due to the empty rows in the middle
            try{//If the type is string put in fields else put in parent
                if(nextRow.getCell(2).getStringCellValue().equals("string")){elements.add(new Field());}
                else if(nextRow.getCell(2).getStringCellValue().startsWith("object")){elements.add(new Parent());}
                //if the type isn't field or object then it is not a useful file so skip it
                else {skippedRows++; continue;}
            }
            catch (NullPointerException e)
            {
                //Sometimes the row is completely empty, and thus it's treated as null and cause error
                //We have no choice but to skip it.
                skippedRows++;
                continue;
            }


            //While there are cells we want to iterate on them
            while (cellIterator.hasNext())
            {
                //Get next cell
                Cell cell = cellIterator.next();
                //Here we get the string value of the cell
                String Scell = cell.getStringCellValue();
                //Here we want to see what we will do on each cell(check its value and add it to classes etc...)
                //if we reach our wanted Rows
                if(Scell.equals("I")|| Scell.equals("O")) {
                    while (cellIterator.hasNext()) {
                        Cell wantedCell = cellIterator.next();
                        String WantedSCell = wantedCell.getStringCellValue();

                        //since cells positions is fixed we will depend on it to store wanted elements
                        switch(wantedCell.getColumnIndex()){
                            case 1: elements.get((nextRow.getRowNum())-skippedRows).setField_name(WantedSCell);
                            case 2: elements.get((nextRow.getRowNum())-skippedRows).setType(WantedSCell);
                            case 3: elements.get((nextRow.getRowNum())-skippedRows).setAllowed_value(WantedSCell);
                            case 4: elements.get((nextRow.getRowNum())-skippedRows).setMandatory(WantedSCell);

                        } //End of Switch

                    } //End of Cell iterator while
                }  //End of our wanted Cells

            } //End of the default Cell iterator
        } //End of the Row iterator

        //For Gui, we made an array of parents only, because we will display them
        ArrayList <Field> parents = new ArrayList<>();
        for(Field f : elements){
            if(f instanceof Parent) parents.add(f);
            else if(f.hasNOParents()){ parents.add(f);}
        }

        //Close WorkBook and the whole file
        workbook.close();
        fis.close();
        try {
            //Arraylist<parent> p=new Arraylist<parent>();
            ArrayList<VBox> root = new ArrayList<>() ;
            ArrayList<Scene> scene = new ArrayList<>();
            ArrayList<Stage> stage = new ArrayList<>();
            ArrayList <Text> Headline1 = new ArrayList<>();
            ArrayList <Text> Headline2 = new ArrayList<>();
            ArrayList<Pane> pane = new ArrayList<>();
            for(int i = 0; i<parents.size(); i++) //obj loop
            {
                root.add(new VBox());
                scene.add(new Scene(root.get(i),300,300));
                pane.add(new Pane());
                Headline1.add(new Text());
                Headline2.add(new Text());
                //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                stage.add(new Stage());
                stage.get(i).setTitle(parents.get(i).getField_name());
                stage.get(i).setScene(scene.get(i));
                String b = parents.get(i).PutChildren(elements);

                Font font =new Font("Georgia", Font.BOLD,20);

                Headline1.get(i).setText(parents.get(i).getField_name());
                Headline2.get(i).setText(b);
                Headline1.get(i).setFont(new javafx.scene.text.Font("Georgia",20));
                Headline1.get(i).setFont(new javafx.scene.text.Font("Times new roman",16));
                //Headline1.get(i).fontProperty();




                root.get(i).getChildren().add(Headline1.get(i));
                root.get(i).getChildren().add(Headline2.get(i));

                //root.get(i).getChildren().add();
                stage.get(i).show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //End of main class

    }

    public static void main(String[] args){
        launch(args);
    }
}






