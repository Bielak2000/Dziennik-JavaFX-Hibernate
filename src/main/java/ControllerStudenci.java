import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerStudenci implements Initializable {

    ObservableList<Studenci> listaStudentow = FXCollections.observableArrayList();
    List<Student> listaS = new ArrayList<>();
    ObservableList<Subject> listaPrzedmiotow = FXCollections.observableArrayList();
    Stage scena;
    DataGenerator data = new DataGenerator();
        ControllerStudenci(ObservableList<Studenci> listaStudentow1, List<Student> listaS1, ObservableList<Subject> listaPrzedmiotow1, Stage s, DataGenerator d){
            listaStudentow = listaStudentow1;
            listaS = listaS1;
            listaPrzedmiotow = listaPrzedmiotow1;
            scena = s;
            data=d;
        }

        @FXML
        private TableView<Studenci> tablica;

    private TableColumn<Studenci, String> generateTableCol(String label, PropertyValueFactory propValFactory, int width) {
        TableColumn<Studenci, String> column = new TableColumn<Studenci, String>(label);
        column.setMinWidth(width);
        column.setCellValueFactory(propValFactory);
        return column;
    }

    private ArrayList<TableColumn<Studenci, String>> kolumny1 = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Kolumna k : data.getColumns(Studenci.class)){
            TableColumn<Studenci, String> col = generateTableCol(k.getLabel(), new PropertyValueFactory<Studenci, String>(k.getName()), 100);
            kolumny1.add(col);
            tablica.getColumns().add(col);
        }

        tablica.setItems(listaStudentow);
    }

    @FXML
    private Button wroc;

    @FXML
    private Button wyjdz;

    @FXML
    void powrot(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GlowneOkienko.fxml"));
        loader.setController(new Controller(listaS, listaPrzedmiotow, scena, listaStudentow));
        AnchorPane panel = loader.load();
        Scene s1 = new Scene(panel);
        scena.setScene(s1);
        scena.show();
    }

    @FXML
    void wyjscie(ActionEvent event) {
        scena.close();
    }
}
