
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
        int skippedRows=0;
        //Create a file with our wanted path
        File file = new File("C:/Users/pc/Downloads/Example.xlsx");
        //raw data from the file
        FileInputStream fis = new FileInputStream(file);
        //convert the data into workbook format
        Workbook workbook = new XSSFWorkbook(fis);
        //we want to get the first sheet of the workbook
        Sheet  firstSheet = workbook.getSheetAt(0);
        //we need an object to iterate around the sheet we have
        Iterator<Row> iterator = firstSheet.iterator();
        //we make an array list for fields and other for parents
        ArrayList <Api> elements = new ArrayList<>();

        //While iterator still has rows we will iterate on them
        while (iterator.hasNext()) {
            Row nextRow = iterator.next(); //get that row
            //we will iterate on the cells of each row
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            //we had to do this due to the empty rows in the middle
            try{
                //If the type is string put in fields else put in parent
                if(nextRow.getCell(2).getStringCellValue().equals("string")){elements.add(new Field());}
                else {elements.add(new Parent());}}
            catch (NullPointerException e){
                skippedRows++;
                continue;}


            //While there are cells
            while (cellIterator.hasNext()) {
                //iterate on next cell
                Cell cell = cellIterator.next();
                //Here we get the string value of the cell
                String Scell = cell.getStringCellValue();
                //Here we want to see what we will do on each cell(check its value and add it to classes etc...
                //if we reach our
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
                                //default: continue;
                        }


                    }



                    elements.get(nextRow.getRowNum()-skippedRows).Print();





                }

            }
            System.out.println();}

        workbook.close();
        fis.close();
    }
} //End of main class




class Api {
    private String Api_name;
    private String Field_name=null;
    private String allowed_value=null;
    private String mandatory=null;
    private String type=null;
    //No arg constructor
    public  Api(){}
    //arg constructors
    public Api(String api_name) {
        Api_name = api_name;
    }

    public Api(String field_name, String allowed_value, String mandatory, String type) {
        Field_name = field_name;
        this.allowed_value = allowed_value;
        this.mandatory = mandatory;
        this.type = type;
    }

    public String getApi_name() {
        return Api_name;
    }

    public void setApi_name(String api_name) {
        Api_name = api_name;
    }

    public String getField_name() {
        return Field_name;
    }

    public void setField_name(String field_name) {
        Field_name = field_name;
    }

    public String getAllowed_value() {
        return allowed_value;
    }

    public void setAllowed_value(String allowed_value) {
        this.allowed_value = allowed_value;
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
    public void getParent(){};
    public void setType(String type) {
        this.type = type;
    }
    public void Print(){

    }
}



class Field extends Api {
    private String myParent="No parents"; //here we put the parent of our field (there might be none) initial value

    //No arg constructor
    public Field(){
        super();
    }
    public  Field(String field_name, String allowed_value, String mandatory, String type)
    {
        super(field_name,allowed_value,mandatory,type);
    }

    public void setField_name(String field_name) {
        super.setField_name(field_name);
    }

    public void setAllowed_value(String allowed_value) {
        super.setAllowed_value(allowed_value);
    }

    public void setMandatory(String mandatory) {
        super.setMandatory(mandatory);
    }
    public void setType(String type) {
        super.setType(type);
    }

    public void setParent(String parent) {
        this.myParent = parent;
    }

    public String getField_name() {

        return super.getField_name();
    }

    public String getAllowed_value() {
        return super.getAllowed_value();
    }

    public String getMandatory() {
        return super.getMandatory();
    }

    public String getType() {
        return super.getType();
    }

    public String getMyParent() {
        getParent();
        return myParent;
    }

    //return true of the field has no parents and false if not
    public boolean hasNOParents(){
        return super.getField_name().startsWith("/field"); //if there's no object before the field name
    }

    public void getParent(){//this function should return the name of the fields parent ******Needs Check*******
        String [] arr;
        arr= super.getField_name().split("/"); //Split /object1/object2/field6 into [ ,object1, object2,field6]
        if(this.hasNOParents()) super.setField_name(arr[arr.length-1]);
        else{
            setField_name(arr[arr.length-1]); //Update Field name
            setParent(arr[arr.length-2]); }//the name before field name is the parent
        //We need to connect this parent to an object we already made not make a new one

    }
    @Override
    public void Print(){
        System.out.println("Field name is "+ getField_name());
        System.out.println("Allowed value is"+ getAllowed_value());
        System.out.println("Mandatory is "+getMandatory());
        System.out.println("Type is "+getType());
        System.out.println("Parent is "+getMyParent());
        System.out.println("--");
    }

} //End of Field Class

class Parent extends Api {
    //Data fields
    private String myParent="No parents"; //initial value
    private String [] children;
    //no arg constructor
    public Parent(){
        super();
    }
    public Parent(String field_name, String allowed_value, String mandatory, String type) {
        super(field_name, allowed_value,mandatory,type);

    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }
    public void setField_name(String field_name) {

        super.setField_name(field_name);
    }

    public void setAllowed_value(String allowed_value) {
        super.setAllowed_value(allowed_value);
    }

    public void setMandatory(String mandatory) {
        super.setMandatory(mandatory);
    }
    public void setType(String type) {
        super.setType(type);
    }

    public void setParent(String parent) {
        this.myParent = parent;
    }

    public String getField_name() {
        return super.getField_name();
    }

    public String getAllowed_value() {
        return super.getAllowed_value();
    }

    public String getMandatory() {
        return super.getMandatory();
    }

    public String getType() {
        return super.getType();
    }

    public String getMyParent() {
        getParent();
        return myParent;
    }

    public void getParent(){//this function should return the name of the fields parent ******Needs Check*******
        String [] arr;
        arr= super.getField_name().split("/"); //Split /object1/object2/field6 into [ ,object1, object2,field6]
        if(arr.length<=2) {super.setField_name(arr[arr.length-1]);}
        else {
            super.setField_name(arr[arr.length-1]); //Update Field name
            setParent(arr[arr.length-2]); }//the name before field name is the parent
        //We need to connect this parent to an object we already made not make a new one

    }
    @Override
    public void Print(){
        System.out.println("Field name is "+ getField_name());
        // System.out.println("Allowed value is"+ getAllowed_value()); no allowed value for objects
        System.out.println("Mandatory is "+getMandatory());
        System.out.println("Type is "+getType());
        System.out.println("Parent is "+getMyParent());
        System.out.println("--");
    }
}


