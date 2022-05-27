package com.company;
import javafx.application.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {
	public void display(){

	}
	@Override
	public void start(Stage primaryStage) {

		try {
			//Arrylist<parent> p=new Arrylist<parent>();
			ArrayList<Field> f=new ArrayList<Field>();
			ArrayList<HBox> root = new ArrayList<HBox>() ;
			ArrayList<Scene> scene = new ArrayList<Scene>();
			ArrayList<Stage> object = new ArrayList<Stage>();
			ArrayList<Pane> pane = new ArrayList<Pane>();

			for(int i=0;i<f.size();i++) //obj loop
			{
				root.add(new HBox());
			    scene.add(new Scene(root.get(i),300,300));
				pane.add(new Pane());
			    //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			     object.add(new Stage());
			     object.get(i).setTitle(f.get(i).getField_name());
			     object.get(i).setScene(scene.get(i));
			     String b = f.get(i).PutChildren(e);
				 pane.get(i).getChildren().add(new Text(b));
			     //root.get(i).getChildren().add();
			     object.get(i).show();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
