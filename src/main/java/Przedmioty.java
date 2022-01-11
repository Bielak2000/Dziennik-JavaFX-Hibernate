import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Przedmioty implements Initializable {

    Student student = new Student();
    ObservableList<Subject> listaPrzedmiotow;
    ObservableList<Subject> listaPrzedmiotowAll;
    List<Student> listaStudentow = new ArrayList<>();
    Stage scena;
    int index;
    ObservableList<String> listaPrzedmiotow5 = FXCollections.observableArrayList();
    DataGenerator data = new DataGenerator();
    Boolean temp10 = false;

    Przedmioty(ObservableList<Subject> listaPrzedmiotow1, Student st, Stage s2, int i, List<Student> listaStudentow1, ObservableList<Subject> listaPrzedmiotowAll1, DataGenerator d2){
        listaPrzedmiotow=listaPrzedmiotow1;
        listaPrzedmiotowAll=listaPrzedmiotowAll1;
        student=st;
        scena=s2;
        index=i;
        for(Subject c : listaPrzedmiotow)
            listaPrzedmiotow5.add(c.getName());
        listaStudentow=listaStudentow1;
        data=d2;
    }

    Przedmioty(ObservableList<Subject> listaPrzedmiotow1, Student st, Stage s2, int i, ObservableList<Subject> listaPrzedmiotow2, DataGenerator d2){
        listaPrzedmiotow=listaPrzedmiotow1;
        listaPrzedmiotowAll=listaPrzedmiotow2;
        student=st;
        scena=s2;
        index=i;
        data=d2;
        for(Subject c : listaPrzedmiotow)
            listaPrzedmiotow5.add(c.getName());
    }

    Przedmioty(ObservableList<Subject> listaPrzedmiotow1, Student st, Stage s2, int i, ObservableList<Subject> listaPrzedmiotow2){
        listaPrzedmiotow=listaPrzedmiotow1;
        listaPrzedmiotowAll=listaPrzedmiotow2;
        student=st;
        scena=s2;
        index=i;
        for(Subject c : listaPrzedmiotow)
            listaPrzedmiotow5.add(c.getName());
    }

    private TableColumn<Subject, String> generateTableCol(String label, PropertyValueFactory propValFactory, int width) {
        TableColumn<Subject, String> column = new TableColumn<Subject, String>(label);
        column.setMinWidth(width);
        column.setCellValueFactory(propValFactory);
        return column;
    }


    private ArrayList<TableColumn<Subject, String>> kolumny1 = new ArrayList<>();

    @FXML
    private TableView<Subject> tabelaPrzedmiotow;

    @FXML
    private Label tyt1;

    @FXML
    private ComboBox<String> box;

    @FXML
    private Button Wyjście;

    @FXML
    private TextField zapisz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Kolumna k : data.getColumns(Subject.class)){
            TableColumn<Subject, String> col = generateTableCol(k.getLabel(), new PropertyValueFactory<Subject, String>(k.getName()), 100);
            kolumny1.add(col);
            tabelaPrzedmiotow.getColumns().add(col);
        }

        tabelaPrzedmiotow.setItems(listaPrzedmiotow);
        box.setItems(listaPrzedmiotow5);

        kolumny1.get(0).setCellFactory(
                column ->{
                    return new TableCell<Subject, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty){
                            super.updateItem(item, empty);
                            List<Double> temp = new ArrayList<>();
                            double temp1 =0;
                            String stan = "X";
                            for(Subject s : listaPrzedmiotow){
                                if(s.getName().equals(item)) {
                                    for (Oceny o : s.getListaWszystkichOcen()) {
                                        if(student.getName().equals(o.getS().getName()) && student.getSurname().equals(o.getS().getSurname()))
                                            temp.add(o.getOcena());
                                    }
                                    for (double d : temp) {
                                        temp1 += d;
                                    }
                                    temp1 = temp1 / temp.size();
                                    stan=s.getStan(index);
                                    break;
                                }
                            }
                            setText(item);
                            setTooltip(new Tooltip("Srednia: " + temp1 + "\nStan Studenta: " + stan +"\nOceny: " + temp));
                        }
                    };
                }
        );
    }

    @FXML
    private Button dodaj;

    @FXML
    private TextField nazwa1;

    @FXML
    private Button sredniaWszystkich;

    @FXML
    private TextField sredniaMniejsza;

    @FXML
    void dodajPrzedmiot(ActionEvent event) throws IOException {
        if(nazwa1.getText()!=null) {
            boolean temp=false;
            for (Subject s2 : listaPrzedmiotow) {
                if (s2.getName().equals(nazwa1.getText())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmiot.fxml"));
                    loader.setController(new ControllerPrzedmiot(s2, student, scena, index, listaPrzedmiotow, listaPrzedmiotowAll, data));
                    AnchorPane panel = loader.load();
                    Scene s1 = new Scene(panel);
                    scena.setScene(s1);
                    scena.show();
                    temp=true;
                    break;
                }
            }
            if(!temp){
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Blad!");
                information.setHeaderText("Nazwa przedmiotu jest niepoprawna.");
                information.showAndWait();
            }
        }
        else{
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Nie podales nazwy przedmiotu.");
        }
    }

    @FXML
    void pokazPrzedmiot(MouseEvent event) throws IOException {
        Subject s = tabelaPrzedmiotow.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmiot.fxml"));
        loader.setController(new ControllerPrzedmiot(s, student, scena, index, listaPrzedmiotow, listaPrzedmiotowAll, data));
        AnchorPane panel = loader.load();
        Scene s1 = new Scene(panel);
        scena.setScene(s1);
        scena.show();
    }

    @FXML
    void pokazBox(ActionEvent event) throws IOException {
        String x = box.getSelectionModel().getSelectedItem();
        for(Subject c : listaPrzedmiotow){
            if(c.getName().equals(x)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmiot.fxml"));
                loader.setController(new ControllerPrzedmiot(c, student, scena, index, listaPrzedmiotow, listaPrzedmiotowAll, data));
                AnchorPane panel = loader.load();
                Scene s1 = new Scene(panel);
                scena.setScene(s1);
                scena.show();
            }
        }
    }

    @FXML
    void Powroc(ActionEvent event) throws IOException {
        if(temp10){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("potwierdzZmiany.fxml"));
            loader.setController(new ControllerPotwierdz(data, scena, listaPrzedmiotow));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        else
            scena.close();
    }

    @FXML
    void pokazSredniaWszystkich(ActionEvent event) {
        double temp1 = 0;
        List<Double> temp = new ArrayList<>();
        List<Double> tempSr = new ArrayList<>();
        for(Subject s : listaPrzedmiotow){
            for(Oceny o : s.getListaWszystkichOcen()){
                if(student.getName().equals(o.getS().getName()) && student.getSurname().equals(o.getS().getSurname()))
                    temp.add(o.getOcena());
            }
            for(double d : temp){
                temp1+=d;
            }
            temp1 = temp1/temp.size();
            tempSr.add(temp1);
            temp1=0;
            temp.clear();
        }
        double suma=0;
        for(double d : tempSr){
            suma+=d;
        }
        suma = suma/tempSr.size();
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        information.setTitle("Srednia ocen ze wszystkich ocen");
        information.setHeaderText("Twoja ocena ze wszystkich przedmiotow wynosi " + suma + ".");
        information.showAndWait();
    }

    @FXML
    void pokazPrzedmiotZMniejszaSrednia(ActionEvent event) throws IOException {

        try {
            double n = Double.parseDouble(sredniaMniejsza.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrzedmioZMniejszaSrednia.fxml"));
            loader.setController(new ControllerPrzedmiotyWedleSredniej(student, listaPrzedmiotow, scena, index, n, listaPrzedmiotowAll));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        catch (Exception e){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Niepoprawne dane o sredniej.");
            information.showAndWait();
        }
    }

    @FXML
    void zapiszSie(ActionEvent event) {
        boolean temp = false;
        boolean temp1 = true;
        for(Subject sc : listaPrzedmiotowAll){
            if(sc.getName().equals(zapisz.getText())){
                temp = true;
                for(Subject sk : listaPrzedmiotow){
                    if(sk.getName().equals(zapisz.getText())){
                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("Blad!");
                        information.setHeaderText("Jestes juz zapisany na ten przedmiot.");
                        information.showAndWait();
                        temp1=false;
                        break;
                    }
                }
                if(temp1){
                    temp10=true;
                    sc.addStudent(student, "Oczekuje na zapisanie");
                    data.dodajStudentaDoPrzedmiotu(zapisz.getText(), student);
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Powiodlo sie!");
                    information.setHeaderText("Oczekujesz na zapisanie.");
                    information.showAndWait();
                    listaPrzedmiotow.add(sc);
                    break;
                }
            }
        }
        if(!temp){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Nie znaleziono takiego przedmiotu.");
            information.showAndWait();
        }
    }

}
