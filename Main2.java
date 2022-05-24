
import java.io.*;
import java.lang.reflect.Array;
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
        //While iterator still has rows we will iterate on them
        ArrayList <Field> fields = new ArrayList<>();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next(); //get that row
            //we will iterate on the cells of each row
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            //make an object for each row
            fields.add(new Field());//this is wrong we want to make a uniqe name each time
            //While there are cells
            while (cellIterator.hasNext()) {
                //iterate on next cell
                Cell cell = cellIterator.next();
                //Here we get the string value of the cell
                String Scell = cell.getStringCellValue();
                //Here we want to see what we will do on each cell(check its value and add it to classes etc...
                //if we reach our
                if(Scell.equals("I")|| Scell.equals("O")){
                    while (cellIterator.hasNext()) {
                        Cell wantedCell = cellIterator.next();
                        String WantedSCell = wantedCell.getStringCellValue();
                        switch(wantedCell.getColumnIndex()){

                            case 1: fields.get(nextRow.getRowNum()).setIO(WantedSCell);
                            case 2: fields.get(nextRow.getRowNum()).setField_name(WantedSCell);
                            case 3: fields.get(nextRow.getRowNum()).setType(WantedSCell);
                            case 4: fields.get(nextRow.getRowNum()).setAllowed_value(WantedSCell);
                            case 5: fields.get(nextRow.getRowNum()).setMandatory(WantedSCell);

                        }
                    //System.out.println("we print here");
                }
                    System.out.print(fields.get(nextRow.getRowNum()).getIO());
                    System.out.print("   ");
                    System.out.print(fields.get(nextRow.getRowNum()).getField_name());
                    System.out.print("   ");
                    System.out.print(fields.get(nextRow.getRowNum()).getType());
                    System.out.print("   ");
                    System.out.print(fields.get(nextRow.getRowNum()).getAllowed_value());
                    System.out.print("     ");
                    System.out.print(fields.get(nextRow.getRowNum()).getMandatory());
                    System.out.print("     ");



                   //Field field = new Field(//All cells in the wanted row);

                }

            }
            System.out.println();
        }

        workbook.close();
        fis.close();
    }
}
 class Field {
    private String Field_name=null;
    //private String obj_name;
    private String allowed_value=null;
    private String mandatory=null;
    private String type=null;
    private String IO=null;
    //No arg constructor
    public  Field()
    {
    }

    public void setField_name(String field_name) {
        Field_name = field_name;
    }

    public void setAllowed_value(String allowed_value) {
        this.allowed_value = allowed_value;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setIO(String IO) {
        this.IO = IO;
    }

     public String getField_name() {
         return Field_name;
     }

     public String getAllowed_value() {
         return allowed_value;
     }

     public String getMandatory() {
         return mandatory;
     }

     public String getType() {
         return type;
     }

     public String getIO() {
         return IO;
     }
 }

