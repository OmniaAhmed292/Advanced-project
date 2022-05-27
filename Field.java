package com.company;

import java.util.ArrayList;

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
