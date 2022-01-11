import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerInfo implements Initializable {

    Student s = new Student();
    Stage scena;
    DataGenerator data;
    Boolean temp10= false;
    List<Student> listaStudentow = new ArrayList<>();

    ControllerInfo(Student st, Stage sc, DataGenerator d, List<Student> listaStudentow1){
        s=st;
        scena = sc;
        data=d;
        listaStudentow=listaStudentow1;
    }

    @FXML
    private Label album;

    @FXML
    private TextField dane;

    @FXML
    private Button im;

    @FXML
    private Label imie;

    @FXML
    private Button na;

    @FXML
    private Label naziwsko;

    @FXML
    private Button nr;

    @FXML
    private Label rok;

    @FXML
    private Button rokUr;

    @FXML
    private Label tytul;

    @FXML
    private Button wyjscie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imie.setText(s.getName());
        naziwsko.setText(s.getSurname());
        rok.setText(String.valueOf(s.getYearOfBirth()));
        album.setText(s.getNumberOfAlbum());
        tytul.setText(s.getName()+" "+s.getSurname());
    }

    @FXML
    void zmienImie(ActionEvent event) {
        s.setName(dane.getText());

        temp10=true;
        for(Student s1 : listaStudentow){
            if(s1.getSurname().equals(s.getSurname())&&s1.getName().equals(s.getName()))
                s1.setName(dane.getText());
        }
        imie.setText(dane.getText());
    }

    @FXML
    void zmienNazwisko(ActionEvent event) {
        s.setSurame(dane.getText());
        naziwsko.setText(dane.getText());
        temp10=true;
        for(Student s1 : listaStudentow){
            if(s1.getSurname().equals(s.getSurname())&&s1.getName().equals(s.getName()))
                s1.setSurame(dane.getText());
        }
    }

    @FXML
    void zmienNrAlbumu(ActionEvent event) {
        s.setNumberOfAlbum(dane.getText());
        album.setText(dane.getText());
        temp10=true;
        for(Student s1 : listaStudentow){
            if(s1.getSurname().equals(s.getSurname())&&s1.getName().equals(s.getName()))
                s1.setNumberOfAlbum(dane.getText());
        }
    }

    @FXML
    void zmienRok(ActionEvent event) {
        try{
            s.setYearOfBirth(Integer.valueOf(dane.getText()));
            rok.setText(String.valueOf(dane.getText()));
            temp10=true;
            for(Student s1 : listaStudentow){
                if(s1.getSurname().equals(s.getSurname())&&s1.getName().equals(s.getName()))
                    s1.setYearOfBirth(Integer.valueOf(dane.getText()));
            }
        }
        catch (Exception e){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Zle dane.");
            information.showAndWait();
        }
    }

    @FXML
    void wyjdz(ActionEvent event) throws IOException {
        if(temp10){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("potwierdzZmiany.fxml"));
            loader.setController(new ControllerPotwierdz(data, scena, listaStudentow, 1));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        else
            scena.close();
    }
}
