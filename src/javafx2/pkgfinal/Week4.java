
package javafx2.pkgfinal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Week4 extends Application implements EventHandler<ActionEvent>{

    ComboBox<String> id;
    TextField name;
    RadioButton male;
    RadioButton female;
    ToggleGroup tg;
    ListView<String> preferedPL;
    ListView<String> selectedPL;
    String[] pl = {"Java", "Python", "JS", "PHP", "Scala"};
    Button save;
    Button arrow;
    CheckBox c1, c2, c3;
    RadioButton r1, r2, r3;
    ComboBox<Integer> cb;
    ColorPicker cp;
    ListView<String> studentList;
    BorderPane root;
    Label title;
    Label message;
    ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws ClassNotFoundException  {

        // Student controls
        id = new ComboBox<>();
        id.setPromptText("Select ID");
        id.setPrefWidth(200);

        name = new TextField();
        name.setPromptText("Enter student name");
        name.setPrefWidth(200);

        male = new RadioButton("Male");
        female = new RadioButton("Female");
        tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);

        ObservableList<String> ol = FXCollections.observableArrayList(pl);
        preferedPL = new ListView<>(ol);
        selectedPL = new ListView<>();
        preferedPL.setPrefSize(150, 120);
        selectedPL.setPrefSize(150, 120);
        
        preferedPL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        arrow=new Button("..");
        save = new Button("Save");
        save.setPrefWidth(100);
        message = new Label("");
        message.setStyle("-fx-text-fill: red;");
   
        // Design controls
  
        c1 = new CheckBox("Normal");
        c2 = new CheckBox("Bold");
        c3 = new CheckBox("Italic");

        r1 = new RadioButton("Red");
        r2 = new RadioButton("Green");
        r3 = new RadioButton("Blue");

        ToggleGroup tg2 = new ToggleGroup();
        r1.setToggleGroup(tg2);
        r2.setToggleGroup(tg2);
        r3.setToggleGroup(tg2);

        cb = new ComboBox<>();
        cb.getItems().addAll(5,10, 15,20);
        cb.setValue(10);
        cb.setPrefWidth(120);

        cp = new ColorPicker();
        cp.setValue(Color.WHITE);

        // Right side list
        studentList = new ListView<>();
        studentList.setPrefSize(250, 350);
        // Left panel
        Label designTitle = new Label("Design Tools");

        VBox leftBox = new VBox(12);
        leftBox.setPadding(new Insets(20));
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPrefWidth(180);

        leftBox.getChildren().addAll(
                designTitle,
                new Label("Style"),
                c1, c2, c3,
                new Label("Color"),
                r1, r2, r3,
                new Label("Font Size"),
                cb,
                new Label("Custom Color"),
                cp
        );

        // Center panel
        title = new Label("Student Registration System");

        HBox genderBox = new HBox(15, male, female);
        genderBox.setAlignment(Pos.CENTER_LEFT);

        HBox plBox = new HBox(20, preferedPL, arrow,selectedPL);
        plBox.setAlignment(Pos.CENTER_LEFT);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(18);
        formGrid.setAlignment(Pos.TOP_LEFT);
        formGrid.setPadding(new Insets(20));

        formGrid.add(title, 0, 0, 2, 1);

        formGrid.add(new Label("ID:"), 0, 1);
        formGrid.add(id, 1, 1);

        formGrid.add(new Label("Name:"), 0, 2);
        formGrid.add(name, 1, 2);

        formGrid.add(new Label("Gender:"), 0, 3);
        formGrid.add(genderBox, 1, 3);

        formGrid.add(new Label("PL:"), 0, 4);
        formGrid.add(plBox, 1, 4);

        formGrid.add(save, 1, 5);
        formGrid.add(message, 1, 6);

        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.getChildren().add(formGrid);

        // Right panel
        Label savedTitle = new Label("Saved Students");

        VBox rightBox = new VBox(15);
        rightBox.setPadding(new Insets(20));
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.setPrefWidth(280);
        rightBox.getChildren().addAll(savedTitle, studentList);

        // Main layout
 
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setLeft(leftBox);
        root.setCenter(centerBox);
        root.setRight(rightBox);
       
        // ids
       
        leftBox.setId("left-panel");
        centerBox.setId("center-panel");
        rightBox.setId("right-panel");
        formGrid.setId("form-grid");
        save.setId("save-btn");
        designTitle.setId("section-title");
        title.setId("main-title");
        savedTitle.setId("section-title");
        
      
        // Registering an Events
      
        
        c2.setOnAction(this);
        c3.setOnAction(this);
        r1.setOnAction(this);
        r2.setOnAction(this);
        r3.setOnAction(this);
        cb.setOnAction(this);
        cp.setOnAction(this);
        arrow.setOnAction(this);
        save.setOnAction(this);
        id.setOnAction(this);
        
        // Read From File
        readFromFile();
      
        // Scene
       
        
        Scene scene = new Scene(root, 1000, 500);

        // use this only if style.css exists correctly
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Student Registration System");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
            saveToFile();
        });
        stage.setAlwaysOnTop(true);
    }

    @Override
    public void handle(ActionEvent t) {
        String backgroundColor = "white";
        String gender ="";
        if(r1.isSelected()){
            backgroundColor="red";
        }
        if(r2.isSelected()){
            backgroundColor="green";
        }
        if(r3.isSelected()){
            backgroundColor="blue";
        }
        if(t.getSource() == cp){
            String color = cp.getValue().toString().substring(2,8);
            save.setStyle("-fx-background-color:#"+color);
        }
        if(t.getSource() == arrow){
            ObservableList<String> ol = FXCollections.observableArrayList(preferedPL.getSelectionModel().getSelectedItems());
            preferedPL.getItems().removeAll(ol);
            selectedPL.getItems().addAll(ol);
            
        }
        if(male.isSelected()){
            gender="male";
        }
        if(female.isSelected()){
            gender = "female";
        }
        if(t.getSource() == save){
            if(validate()){
                Student s =new Student(name.getText(), gender);
                s.setPl(new ArrayList<>(selectedPL.getItems()));
                students.add(s);
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
                clear();
            }else{
                message.setText("Name and gender are required!");
            }
            
            
        }
        
        
     // Load student data when an ID is selected from the ComboBox.
     // This block implements the ID ComboBox functionality.
     // When the user selects a student ID, the program searches for the matching student
     // in the students ArrayList, then loads and displays the student's name, gender,
     // and selected programming languages in the form.   
        if (t.getSource() == id) {

            String selectedId = id.getValue();

            if (selectedId == null) {
                return;
            }

            clear();

            Student stu = null;

            for (Student s : students) {
                if (s.getId().equals(selectedId)) {
                    stu = s;
                    break;
                }
            }

            if (stu == null) {
                return;
            }

            name.setText(stu.getName());

            if (stu.getGender().equals("male")) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }

            ObservableList<String> selectedLanguages =
                    FXCollections.observableArrayList(stu.getPl());

            preferedPL.getItems().removeAll(selectedLanguages);
            selectedPL.getItems().addAll(selectedLanguages);
        }

        root.setStyle("-fx-font-weight:"+(c2.isSelected()?"bold":"normal") +";"+
                        "-fx-font-style:"+(c3.isSelected()?"italic":"normal")+";"+
                        "-fx-background-color:"+backgroundColor+";"+
                        "-fx-font-size:"+cb.getValue()+"px;");

    }
    
    public boolean validate(){
        if(name.getText().isBlank() ||tg.getSelectedToggle() == null){
            return false;
        }
        return true;
    }
    

    public void clear(){
        name.setText("");
        male.setSelected(false);
        female.setSelected(false);
        selectedPL.getItems().clear();
        preferedPL.getItems().setAll(pl);
        message.setText("");
    }

    

    public void saveToFile(){
        
        try(ObjectOutputStream oos =new ObjectOutputStream(
                new FileOutputStream("src/javafx2/pkgfinal/students.dat"))){
            oos.writeObject(students);
        }catch(IOException e){
            System.out.println(e);
        }
        
        
        
    }

    public void readFromFile() throws ClassNotFoundException{
        File f = new File("src/javafx2/pkgfinal/students.dat");
        if(!f.exists()){
            System.out.println("No File yet");
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            students = (ArrayList<Student>)ois.readObject();
            for(Student s:students){
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
            }
            if (!students.isEmpty()) {
                int stdId = Integer.parseInt(students.get(students.size() - 1).getId().substring(5));
                Student.counter = stdId + 1;
            }
        }catch(IOException e){
            System.out.println(e);
        }
        
        
    }
    

   
    
}