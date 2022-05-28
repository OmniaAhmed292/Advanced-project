package com.company;

import java.util.ArrayList;

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
                if(elements.get(i).getAllowed_value()!=" "){information.add(elements.get(i).getAllowed_value());}
                Mand.add(elements.get(i).getMandatory());
            }
        }
        for(int j=0;j<Children.size();j++){
            s = s+"\n "+Children.get(j);
            if(elements.get(j).getAllowed_value()!=" "){s= s+"  Allowed_values      "+information.get(j);}
            s= s+"  Mandatory     "+Mand.get(j);
        }

        return s;}

    //if so we want it to add it to the list of its children
    //if not then Do nothing
}
