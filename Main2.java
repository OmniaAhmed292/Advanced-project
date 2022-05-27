import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
//Apache
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {
    public static void main(String[] args) throws IOException {
        int skippedRows=0; //we need them because some rows with no useful info are skipped

        //Create a file with our wanted path
        File file = new File("C:/Users/pc/Downloads/info.xlsx");
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

            /*if(nextRow.getRowNum()<6){
            skippedRows++;
            continue;
            }*/

            //we had to do this due to the empty rows in the middle
            try{//If the type is string put in fields else put in parent
                if(nextRow.getCell(2).getStringCellValue().equals("string")){elements.add(new Field());}
                else if(nextRow.getCell(2).getStringCellValue().startsWith("object")){elements.add(new Parent());}
                //if the type isn't field or object then it is not a useful file so skip it
                else {skippedRows++; continue;}
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

        //For Gui we made an array of parents only, because we will display them
        ArrayList <Field> parents=new ArrayList<>();
        for(Field f : elements){
            if(f instanceof Parent) parents.add(f);
            else if(f.hasNOParents()){ parents.add(f);}
        }


            /*ATestbench
            for(Field p: parents){
               // p.Print();
            System.out.println("Children are : "+p.PutChildren(elements));
            System.out.println("--");

            }*/

        //Close WorkBook and the whole file
        workbook.close();
        fis.close();
    } //End of main function
} //End of main class


class Field {
    private String Field_name=null;
    private String allowed_value=null;
    private String mandatory=null;
    private String type=null;
    private String myParent="No parents"; //here we put the parent of our field (there might be none) initial value

    //No arg constructor
    public Field(){
    }
    public  Field(String field_name, String allowed_value, String mandatory, String type)
    {

    }
    //Setters and getters
    public String getField_name() {
        return this.Field_name;
    }

    public void setField_name(String field_name) {
        this.Field_name = field_name;
    }

    public String getAllowed_value() {
        return allowed_value;
    }

    public void setAllowed_value(String allowed_value) {
        this.allowed_value = allowed_value;
    }

    public void setMyParent(String myParent) {
        this.myParent = myParent;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMyParent() {
        getParent();
        return myParent;
    }

    //return true of the field has no parents and false if not
    public boolean hasNOParents(){
        return this.getField_name().startsWith("/field"); //if there's no object before the field name
    }

    public void getParent(){//this function should return the name of the fields parent ******Needs Check*******
        String [] arr;
        arr= this.getField_name().split("/"); //Split /object1/object2/field6 into [ ,object1, object2,field6]
        if(arr.length<=2) {this.setField_name(arr[arr.length-1]);}
        else{
            setField_name(arr[arr.length-1]); //Update Field name
            setMyParent(arr[arr.length-2]); }//the name before field name is the parent
        //We need to connect this parent to an object we already made not make a new one

    }
    //Print function
    public void Print(){
        System.out.println("Field name is "+ getField_name());
        System.out.println("Allowed value is"+ getAllowed_value());
        System.out.println("Mandatory is "+getMandatory());
        System.out.println("Type is "+getType());
        System.out.println("Parent is "+getMyParent());
    }
    public String PutChildren(ArrayList<Field> elements){
        return "This is field no children";
    }

} //End of Field Class

class Parent extends Field {
    //Data fields

    //private String [] children;
    ArrayList <String> Children= new ArrayList<>();
    ArrayList <String> information =new ArrayList<>();
    ArrayList <String> Mand =new ArrayList<>();
    //no arg constructor
    public Parent(){
        super();
    }
    public Parent(String field_name, String allowed_value, String mandatory, String type) {
        super(field_name, allowed_value,mandatory,type);

    }



    public void setChildren(ArrayList<String> children) {

        Children = children;
    }

    @Override
    public void Print(){ //still need to add children
        System.out.println("Field name is "+ getField_name());
        // System.out.println("Allowed value is"+ getAllowed_value()); no allowed value for objects
        System.out.println("Mandatory is "+getMandatory());
        System.out.println("Type is "+getType());
        System.out.println("Parent is "+getMyParent());
    }
    public String PutChildren(ArrayList<Field> elements){
        //we want the function to check if the parent name is th object name regardless of its type
        String s=" ";
        for(int i=0;i< elements.size();i++){
            if(elements.get(i).getMyParent().equals(super.getField_name())){
                Children.add(elements.get(i).getField_name());
                if(elements.get(i).getAllowed_value()!= ""){information.add(elements.get(i).getAllowed_value());}
                Mand.add(elements.get(i).getMandatory());
            }
        }
        for(int j=0;j<Children.size();j++){
            s = s+"\n "+Children.get(j);
            if(elements.get(j).getAllowed_value()!= ""){s= s+" Allowed_values "+information.get(j);}
            s= s+" Mandatory "+Mand.get(j);
        }

        return s;}

    //if so we want it to add it to the list of its children
    //if not then Do nothing
}


