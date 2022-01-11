import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPrzedmiotyWedleSredniej implements Initializable {

    Student student = new Student();
    ObservableList<Subject> listaPrzedmiotow;
    ObservableList<Subject> listaPrzedmiotowAll;
    ObservableList<KlasaPomocniczna2> listaPom = FXCollections.observableArrayList();
    Stage scena;
    int index;
    double sredniaPonizej;

    ControllerPrzedmiotyWedleSredniej(Student s, ObservableList<Subject> listaPrzedmiotow1, Stage st, int i, double sr,ObservableList<Subject> listaPrzedmiotow2){
        student = s;
        listaPrzedmiotow = listaPrzedmiotow1;
        scena = st;
        index = i;
        sredniaPonizej=sr;
        listaPrzedmiotowAll=listaPrzedmiotow2;
        List<Double> temp = new ArrayList<>();
        double temp1=0;
        for(Subject s2 : listaPrzedmiotow){
            for(Oceny o : s2.getListaWszystkichOcen()){
                temp.add(o.getOcena());
            }
            for(double d : temp){
                temp1+=d;
            }
            temp1 = temp1/temp.size();
            if(temp1<sr){
                listaPom.add(new KlasaPomocniczna2(s2.getName(), temp1));
            }
        }

    }

    @FXML
    private TableColumn<KlasaPomocniczna2, String> przedmiot;

    @FXML
    private TableColumn<KlasaPomocniczna2, String> srednia;

    @FXML
    private TableView<KlasaPomocniczna2> tablica;

    @FXML
    private Button Wyjscie;

    @FXML
    private Button powrot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //for(KlasaPomocniczna2 k : listaPom){
        //    System.out.println(k.nazwa + k.srednia);
        //}
        przedmiot.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNazwa()));
        srednia.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSrednia())));
        przedmiot.setSortType(TableColumn.SortType.ASCENDING);
        srednia.setSortType(TableColumn.SortType.ASCENDING);
        tablica.setItems(listaPom);
    }

    @FXML
    void wroc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmioty.fxml"));
        loader.setController(new Przedmioty(listaPrzedmiotow, student, scena, index, listaPrzedmiotowAll));
        AnchorPane panel = loader.load();
        Scene s1 = new Scene(panel);
        scena.setScene(s1);
        scena.show();
    }

    @FXML
    void wyjdz(ActionEvent event) {
        scena.close();
    }
}
