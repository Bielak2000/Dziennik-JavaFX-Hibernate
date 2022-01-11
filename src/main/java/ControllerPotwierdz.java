import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPotwierdz {
    DataGenerator data;
    Stage scena;
    List<Subject> listaPrzedmiotow = new ArrayList<>();
    List<Student> listaStudentow = new ArrayList<>();

    ControllerPotwierdz(DataGenerator d, Stage s){
        data = d;
        scena = s;
    }

    ControllerPotwierdz(DataGenerator d, Stage s, List<Subject> l){
        data = d;
        scena = s;
        listaPrzedmiotow=l;
    }

    ControllerPotwierdz(DataGenerator d, Stage s, List<Student> ls, int x){
        data = d;
        scena = s;
        listaStudentow=ls;
    }

    @FXML
    private Button nie;

    @FXML
    private Button tak;

    @FXML
    void niePotwierdzaj(ActionEvent event)  {
        scena.close();
    }

    @FXML
    void potwierdz(ActionEvent event) throws IOException, IllegalAccessException {
        data.serialization();
        data.serializationStudents();
        if(listaPrzedmiotow.isEmpty()){
            data.zapiszDoBazyStudentow2(listaStudentow);
        }
        else
            data.zapiszDoBazyPrzedmioty2(listaPrzedmiotow);
        scena.close();
    }

}
