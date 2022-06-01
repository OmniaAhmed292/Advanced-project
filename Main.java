package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
//Apache
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//java fx
import javafx.application.*;
import javafx.scene.text.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws IOException{

        int skippedRows = 0; //we need them because some rows with no useful info are skipped
        String ApiName = "not yet";
        //Create a file with our wanted path
        File file = new File("C:/Users/pc/Downloads/basic.xlsx");
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
            try{
                //If the type is string put in fields else put in parent
                if(nextRow.getCell(0).getStringCellValue().startsWith("REST Operation Mapping")) {
                ApiName=nextRow.getCell(0).getStringCellValue().substring(22);
                skippedRows++;
                }
                else if(nextRow.getCell(2).getStringCellValue().equals("string")){
                    elements.add(new Field());
                }
                else if(nextRow.getCell(2).getStringCellValue().startsWith("object")){
                    elements.add(new Parent());
                }
                //if the type isn't field or object then it is not a useful file so skip it
                else {
                    skippedRows++;
                    continue;
                }
            }
            catch (NullPointerException e)
            {
                //Sometimes the row is completely empty and thus it's trated as null and cause error
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
                        elements.get((nextRow.getRowNum())-skippedRows).setApiName(ApiName);

                        switch(wantedCell.getColumnIndex()){
                            case 1:
                                elements.get((nextRow.getRowNum())-skippedRows).setField_name(WantedSCell);
                            case 2:
                                elements.get((nextRow.getRowNum())-skippedRows).setType(WantedSCell);
                            case 3:
                                elements.get((nextRow.getRowNum())-skippedRows).setAllowed_value(WantedSCell);
                            case 4:
                                elements.get((nextRow.getRowNum())-skippedRows).setMandatory(WantedSCell);
                        } //End of Switch

                    } //End of Cell iterator while
                }  //End of our wanted Cells

            } //End of the default Cell iterator
        } //End of the Row iterator

        //For Gui we made an array of parents only, because we will display them
        ArrayList <Field> parents = new ArrayList<>();

        for(Field f : elements){
            if(f instanceof Parent){
                parents.add(f);
            }
            else if(f.hasNOParents()){
                parents.add(f);
            }
        }

        //Close WorkBook and the whole file
        workbook.close();
        fis.close();
        try {
            //Elements we need to add before the loop
            ArrayList<VBox> root = new ArrayList<VBox>() ;
            ArrayList<Scene> scene = new ArrayList<Scene>();
            ArrayList<Stage> stage = new ArrayList<Stage>();
            ArrayList <Text> Headline1 = new ArrayList<Text>();
            ArrayList <Text> Headline2 = new ArrayList<Text>();
            ArrayList<Pane> topPane = new ArrayList<>();

            //integers we need for some calculations
            int j = 0;int V = 0,H = 0;
            //initializations
            stage.add(new Stage());
            topPane.add(new GridPane());
            scene.add(new Scene(topPane.get(0),500,500));

            //Our MAIN LOOP
            for(int i = 0; i < parents.size() ; i++)
            {   //Things to be created each loop
                root.add(new VBox());
                Headline1.add(new Text());
                Headline2.add(new Text());

                //To manage having more than one Api
                if(i!=0){
                    if(parents.get(i).getApiName() != parents.get(i-1).getApiName()){
                        stage.add(new Stage()); //add new stage only if the Api name is different
                        stage.get(j).setScene(scene.get(j));
                        topPane.add(new Pane());
                        System.out.println("new Api");
                        j++; //A flag to get the indexes of our stages
                        V = 0;
                        H = 0;
                        scene.add(new Scene(topPane.get(j),500,500));
                    }
                    else{ //Api are equal
                        stage.get(j).setTitle(parents.get(i).getApiName());
                        stage.get(j).setScene(scene.get(j));
                        System.out.println("Api is equal");
                    }
                }
                else{
                    System.out.println("else is invoked");
                    stage.get(j).setTitle(parents.get(i).getApiName());
                    stage.get(j).setScene(scene.get(j));
                }

                String b = parents.get(i).PutChildren(elements); //To get the children of each element

                Headline1.get(i).setText(parents.get(i).getField_name()); //name of the current object
                Headline2.get(i).setText(b); //Put Children here
                //Properties of the two Texts"
                Headline1.get(i).setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 24));
                Headline1.get(i).setFill(Color.BLUEVIOLET);
                Headline1.get(i).setFont(new Font("Times New Roman",16));

                root.get(i).getChildren().add(Headline1.get(i));
                root.get(i).getChildren().add(Headline2.get(i));

                //Put the panes in a larger/Top pane
                topPane.get(j).getChildren().add(root.get(i));
                //How does each small pane look
                root.get(i).setAlignment(Pos.TOP_LEFT);
                root.get(i).setPadding(new Insets(V*200,0,0, H*300));
                V++;
                if(V==3) {
                    V=0;H++;
                }
                if(H==3){
                    H=0; V++;
                }

                for(int k = 0; k < stage.size(); k++){
                    stage.get(k).show();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}








