import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPrzedmiot implements Initializable {

    Subject przedmiot = new Subject();
    Student student = new Student();
    ObservableList<KlasaPomocnicza> listaOcen = FXCollections.observableArrayList();
    ObservableList<Subject> listaPrzedmiotow = FXCollections.observableArrayList();
    ObservableList<Subject> listaPrzedmiotowAll = FXCollections.observableArrayList();
    Stage scena;
    int index;
    double srednia;
    DataGenerator data ;
    Boolean temp10=false;
    Boolean temp11=false;

    @FXML
    private Label sredniaP;

    @FXML
    private Label stan;

    @FXML
    private TableView<KlasaPomocnicza> tablica;

    private ArrayList<TableColumn<KlasaPomocnicza, Double>> tablicaOcen = new ArrayList<>();

    @FXML
    private Label tytul;

    @FXML
    private Button Wyjście;

    @FXML
    private Button powrot;

    @FXML
    private Button wypisz;

    @FXML
    private TextField przepisz;

    @FXML
    private TextField dataPole;

    @FXML
    private TextField komm;

    @FXML
    private TextField oce;

    @FXML
    private Button przyciskKom;

    ControllerPrzedmiot(Subject przed, Student st, Stage s2, int i2, ObservableList<Subject> listaPrzedmiotow1, ObservableList<Subject> listaPrzedmiotow2, DataGenerator d2){
        przedmiot=przed;
        student=st;
        scena = s2;
        index=i2;
        listaPrzedmiotow = listaPrzedmiotow1;
        listaPrzedmiotowAll=listaPrzedmiotow2;
        for(Oceny o : przedmiot.getListaWszystkichOcen()){
            if(o.getS().getSurname().equals(student.getSurname()) && o.getS().getName().equals(student.getName())){
                listaOcen.add(new KlasaPomocnicza(o.getOcena()));
            }
        }

        double suma=0;
        for(KlasaPomocnicza i : listaOcen){
            suma+=i.getOcena();
        }
        System.out.println(suma + "/" + listaOcen.size());
        if(listaOcen.size()!=0)
            suma=suma/listaOcen.size();
        srednia=suma;
        System.out.println(suma);
        student.setPoints(suma);
        data=d2;
    }

    private TableColumn<KlasaPomocnicza, Double> generateTableCol(String label, PropertyValueFactory propValFactory, int width) {
        TableColumn<KlasaPomocnicza, Double> column = new TableColumn<KlasaPomocnicza, Double>(label);
        column.setMinWidth(width);
        column.setCellValueFactory(propValFactory);
        return column;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Kolumna k : data.getColumns(KlasaPomocnicza.class)){
            TableColumn<KlasaPomocnicza, Double> col = generateTableCol(k.getLabel(), new PropertyValueFactory<KlasaPomocnicza, Double>(k.getName()), 100);
            tablicaOcen.add(col);
            tablica.getColumns().add(col);
        }
        tablica.setItems(listaOcen);
        sredniaP.setText(Double.toString(srednia));
        stan.setText(przedmiot.getStan(index));
        tytul.setText(przedmiot.getName());
    }

    @FXML
    void wroc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Przedmioty.fxml"));
        loader.setController(new Przedmioty(listaPrzedmiotow, student, scena, index, listaPrzedmiotowAll, data));
        AnchorPane panel = loader.load();
        Scene s1 = new Scene(panel);
        scena.setScene(s1);
        scena.show();
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
        else if(temp11){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("potwierdzZmiany.fxml"));
            loader.setController(new ControllerPotwierdz(data, scena, listaPrzedmiotowAll));
            AnchorPane panel = loader.load();
            Scene s1 = new Scene(panel);
            scena.setScene(s1);
            scena.show();
        }
        else
            scena.close();
    }

    @FXML
    void wypiszSie(ActionEvent event) throws IOException {
        if(przedmiot.getStan(index).equals("Wypisany") || przedmiot.getStan(index).equals("Przepisany")){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Jestes juz wypisany z tego przedmiotu.");
            information.showAndWait();
        }
        else {
            przedmiot.setStan(index, "Wypisany");
            stan.setText(przedmiot.getStan(index));
            for(Subject s : listaPrzedmiotow){
                if(przedmiot.getName().equals(s.getName())){
                    s.setStan(index, "Wypisany");
                    break;
                }
            }
            temp10=true;
        }
    }

    @FXML
    void przepiszNaInny(ActionEvent event) {
        boolean temp=true;
        boolean temp1=false;
        if(przepisz.getText()!=null) {
            if(przedmiot.getStan(index).equals("Zapisany")) {
                for (Subject s : listaPrzedmiotowAll) {
                    if (s.getName().equals(przepisz.getText())) {
                        temp1 = true;
                        for (Student sq : s.getStudenci()) {
                            if (sq.getName().equals(student.getName()) && sq.getSurname().equals(student.getSurname()) && s.getStan(index).equals("Zapisany")) {
                                temp = false;
                            }
                        }
                        if (temp) {
                            s.addStudent(student, "Zapisany");
                            s.setStan(index, "Przepisany");
                            data.dodajStudentaDoPrzedmiotu(przepisz.getText(), student);
                            temp11=true;
                            przedmiot.setStan(index, "Przepisany");
                            stan.setText(przedmiot.getStan(index));
                            listaPrzedmiotow.add(s);
                            for(Subject s1 : listaPrzedmiotow){
                                if(przedmiot.getName().equals(s1.getName())){
                                    s1.setStan(index, "Przepisany");
                                }
                            }
                            break;
                        } else {
                            Alert information = new Alert(Alert.AlertType.INFORMATION);
                            information.setTitle("Blad!");
                            information.setHeaderText("Jestes juz zapisany na ten przedmiot.");
                            information.showAndWait();
                        }
                    }
                }
            }
            else{
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Blad!");
                information.setHeaderText("Nie jestes zapisany do tego przedmiotu.");
                information.showAndWait();
                temp1=true;
            }
        }
        if(!temp1){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Nie znaleziono takiego przedmiotu.");
            information.showAndWait();
        }
    }

    @FXML
    void dodajKomentarz(ActionEvent event) {
        if(dataPole.getText().equals("") || oce.getText().equals("")|| komm.getText().equals("")){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Blad!");
            information.setHeaderText("Zbyt malo informacji.");
            information.showAndWait();
        }
        else{
            try {
                int ocenka = Integer.parseInt(oce.getText());
                Rating r = new Rating(Integer.parseInt(oce.getText()), dataPole.getText(), komm.getText(), przedmiot.getName());
                data.zapiszDoBazyRating(r);
            }catch(Exception e){
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Blad!");
                information.setHeaderText("Zla ocena.");
                information.showAndWait();
            }
        }
    }
}
