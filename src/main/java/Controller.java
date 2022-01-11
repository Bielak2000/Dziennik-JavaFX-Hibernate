import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<Student> listaStudentow = new ArrayList<>();
    ObservableList<Subject> listaPrzedmiotow= FXCollections.observableArrayList();
    Stage scena;
    Student st = new Student();
    Boolean b;
    DataGenerator d2;
    ObservableList<Studenci> listaStudentow3 = FXCollections.observableArrayList();


    Controller(){}

    Controller(List<Student> listaStudentow1, ObservableList<Subject> listaPrzedmiotow1, Stage s1, ObservableList<Studenci> listaStudentow2){
        listaStudentow3 = listaStudentow2;
        scena=s1;
        DataGenerator d = new DataGenerator();
        if(listaStudentow1.isEmpty() || listaPrzedmiotow1.isEmpty()){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Baza jest pusta.");
            information.showAndWait();
            b=false;
            scena.close();
        }
        listaStudentow=listaStudentow1;
        listaPrzedmiotow=listaPrzedmiotow1;
        b=true;
        d2=d;
    }

    @FXML
    private Button grupy;

    @FXML
    private Button przedmioty;

    @FXML
    private Label tyt;

    @FXML
    private Button wyjscie;

    @FXML
    void grupyON(ActionEvent event) throws IOException {
        boolean temp=false;
        Student s2 = new Student();
        for(Student s : listaStudentow){
            if(s.getName().equals(imie.getText()) && s.getSurname().equals(nazwisko.getText())){
                s2 = s;
                //System.out.println("xxx");
                temp = true;
                break;
            }
        }
        if(temp){
            st=s2;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Informacje.fxml"));
            loader.setController(new ControllerInfo(st, scena, d2, listaStudentow));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        else{
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Nie znaleziono takiego studenta.");
            information.showAndWait();
        }
    }

    @FXML
    private TextField imie;

    @FXML
    private TextField nazwisko;

    @FXML
    void przedmiotyON(ActionEvent event) throws IOException {
        boolean temp=false;
        Student s2 = new Student();
        for(Student s : listaStudentow){
            if(s.getName().equals(imie.getText()) && s.getSurname().equals(nazwisko.getText())){
                s2 = s;
                //System.out.println("xxx");
                temp = true;
                break;
            }
        }
        if(temp){
            st=s2;

            ObservableList<Subject> listaPrzedmiotow2 = FXCollections.observableArrayList();
            int index=0;
            for(Subject s : listaPrzedmiotow){
                index=0;
                for(Student student : s.getStudenci()){
                    if(student.getName().equals(s2.getName()) && student.getSurname().equals(s2.getSurname()) ){
                        listaPrzedmiotow2.add(s);
                        break;
                    }
                    index++;
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmioty.fxml"));
            loader.setController(new Przedmioty(listaPrzedmiotow2, s2, scena, index, listaStudentow, listaPrzedmiotow, d2));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        else{
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Nie znaleziono takiego studenta.");
            information.showAndWait();
        }

    }

    @FXML
    void wyjdzZProgramu(ActionEvent event) {
        scena.close();
    }

    @FXML
    private Button studenci;

    @FXML
    void pokazStudentow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Studenci.fxml"));
        loader.setController(new ControllerStudenci(listaStudentow3, listaStudentow, listaPrzedmiotow, scena, d2));
        AnchorPane panel = loader.load();
        Scene sc = new Scene(panel);
        scena.setScene(sc);
        scena.show();
    }

}
