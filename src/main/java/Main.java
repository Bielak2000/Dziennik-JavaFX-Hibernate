import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) throws IOException {

        //zapis danych do bazy
        /*DataGenerator d = new DataGenerator();
        d.uzupelnijListeStudentow2();
        d.zapiszDoBazyStudentow();
        d.uzupelnijListePrzedmiotow();
        d.zapiszDoBazyPrzedmioty();*/

        //wstawienie komentarzy
        //DataGenerator d = new DataGenerator();
        //Rating r1 = new Rating(4, "19.12.2021", "XXXX", "Analiza");
        //Rating r2 = new Rating(3, "19.11.2021", "XXXXYY", "Analiza2");
       // Rating r3 = new Rating(3, "19.10.2021", "XXXXZZ", "Java");
       // Rating r4 = new Rating(2, "19.12.2020", "XXXX111", "Algebra");
        //d.zapiszDoBazyRating(r1);
        //d.zapiszDoBazyRating(r2);
        //d.zapiszDoBazyRating(r3);
        //d.zapiszDoBazyRating(r4);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataGenerator dataGen = new DataGenerator();
        List<Subject> listaPrzedmiotow = new ArrayList<>();
        listaPrzedmiotow = dataGen.wczytajDaneZBazy();
        List<Student> listaStudentow = new ArrayList<>();
        listaStudentow = dataGen.wczytajDaneZBazyStudenci();
        ObservableList<Subject> listaPrzedmiotoww = FXCollections.observableArrayList();
        for(Subject s : listaPrzedmiotow){
            listaPrzedmiotoww.add(s);
        }

        List<Student> listaStudentowCriteria = dataGen.wczytajDaneZBazyStudenci2();

        ObservableList<Studenci> listaStudentowDoCriteria2 = FXCollections.observableArrayList();

        Boolean temp;
        for(Subject s : listaPrzedmiotow){
            for(Student s1 : listaStudentowCriteria){
                temp=false;
                for(Studenci s2 : listaStudentowDoCriteria2){
                    if(s2.getImie().equals(s1.getName()) && s2.getNazwisko().equals(s1.getSurname()))
                        temp = true;
                }
                if(!temp){
                    listaStudentowDoCriteria2.add(new Studenci(s1.getName(), s1.getSurname(), s1.getNumberOfAlbum()));
                }
            }
        }

        System.out.println("----------------Zapis przedmiotow do CSV----------------");
        FileWriter csvFile = new FileWriter("Subject.csv");
        BufferedWriter out = new BufferedWriter(csvFile);
        FileWriter csvFile1 = new FileWriter("Oceny.csv");
        BufferedWriter out1 = new BufferedWriter(csvFile1);
        FileWriter csvFile2 = new FileWriter("Stany.csv");
        BufferedWriter out2 = new BufferedWriter(csvFile2);
        FileWriter csvFile3 = new FileWriter("Studenci.csv");
        BufferedWriter out3 = new BufferedWriter(csvFile3);

        for(Subject s : listaPrzedmiotow){
            s.saveToCSV(out,out1,out2,out3);
        }

        out.close();
        out1.close();
        out2.close();
        out3.close();

        System.out.println("----------------Wczytanie przedmiotow z CSV----------------");
        System.out.println("Przedmioty przed wczytaniem:");
        for(Subject s : listaPrzedmiotow)
            s.printAll();

        List<Subject> listaP = dataGen.wczytanieCSVSubject();

        System.out.println("Przedmioty po wczytaniu:");
        for(Subject s : listaP)
            s.printAll();


        System.out.println("---------------------Przed serializacja---------------------");
        /*for(Subject s : listaPrzedmiotow) {
            s.printAll();
        }*/

        //serializacja
        //dataGen.serialization();
        //dataGen.serializationStudents();

        System.out.println("---------------------Po serializacji---------------------");
        //wczytanie przedmiotow
        /*List<Subject> listaTempSubject = dataGen.deseralization();
        ObservableList<Subject> listaPrzedmiotoww = FXCollections.observableArrayList();
        for(Subject s : listaTempSubject){
        //    System.out.println("xasxas");
        //s.printAll();
            listaPrzedmiotoww.add(s);
        }*/

        //wczytanie studentow
        //dataGen.serializationStudents();
        /*List<Student> listaSS = new ArrayList<>();
        listaSS=dataGen.deseralizationStudents();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GlowneOkienko.fxml"));
        Controller c = new Controller(listaStudentow, listaPrzedmiotoww, primaryStage, listaStudentowDoCriteria2);
        loader.setController(c);
        if(c.b){
            AnchorPane panel = loader.load();
            Scene s = new Scene(panel);
            primaryStage.setScene(s);
            primaryStage.show();
        }
    }
}
