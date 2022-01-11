import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Subject")
public class Subject implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    //@AsTableColumn(text="Name")
    //@Id
    //@Column(name="id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @AsTableColumn(text="Name")
    @Column(name="name")
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "PrzedmiotID")
    @JoinTable(name = "Studenci5", joinColumns=@JoinColumn(name="id_studenta"), inverseJoinColumns = @JoinColumn(name = "id_przedmiotu"))
    private List<Student> studenci = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PrzedmiotID")
    private List<Oceny> listaWszystkichOcen = new ArrayList<>();

    //private String stanyDB;
    @ElementCollection
    private List<String> StanyStudenta = new ArrayList<>();

    Subject(String n){
        name = n;
    }

    Subject(){}

    public void addStudent(Student s, String st){
        studenci.add(s);
        StanyStudenta.add(st);
    }

    public void addStudent(Student s){
        studenci.add(s);
    }

    public void addStan(String s){
        StanyStudenta.add(s);
    }

    public void addOcena(double o, Student s){
        listaWszystkichOcen.add(new Oceny(o,s));
    }

    public String getName(){
        return name;
    }

    public List<Oceny> getListaWszystkichOcen(){
        return listaWszystkichOcen;
    }

    public List<Student> getStudenci(){
        return studenci;
    }

    public String getStan(int i){
        return StanyStudenta.get(i);
    }

    public void setStan(int i, String s){
        StanyStudenta.set(i,s);
    }

    public void setId(int id1) {this.id=id1;}

    public int getId() {return this.id;}

    //@ElementCollection
    //@CollectionTable(name="Stany", joinColumns=@JoinColumn(name="id"))
    //@Column(name="stan")
    public List<String> getStanyStudenta(){
        return StanyStudenta;
    }

    public void saveToCSV(BufferedWriter out, BufferedWriter out1, BufferedWriter out2, BufferedWriter out3) throws IOException {
        out.append(name + "\n");
        for(Student s : studenci) {
            out3.append(s.getNumberOfAlbum() + ";" + s.getName() + ";" + s.getSurname() + ";" + s.getCondition().toString() + ";" + s.getYearOfBirth() + ";" + s.getPoints());
        }
        out3.append("\n");
        int i;
        i=0;
        for(Oceny o : listaWszystkichOcen) {
            out1.append(o.getS().getName() + ";" + o.getS().getSurname() + ";" + o.getOcena());
            if(i!=(listaWszystkichOcen.size()-1))
                out1.append(";");
            i++;
        }
        out1.append("\n");
        i =0;
        for(String s : StanyStudenta) {
            out2.append(s);
            if(i!=(StanyStudenta.size()-1))
                out2.append(";");
            i++;
        }
        out2.append("\n");
    }

    public void printAll(){
        System.out.println("Nazwa: " + name);
        System.out.println("Studenci: ");
        int i=0;
        for(Student s : studenci) {
            System.out.println("Nazwisko: " + s.getSurname() + ", stan ze studenta: " + s.getCondition() + ", stan z przedmiotu: " + getStan(i));
            //System.out.println();getStan(i);
            i++;
        }
        System.out.println("Oceny: ");
        for(Oceny o : listaWszystkichOcen)
            System.out.println(o.getOcena() + "," + o.getS().getSurname());
    }

}
