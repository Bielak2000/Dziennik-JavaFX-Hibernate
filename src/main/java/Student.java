import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Studenci")
public class Student implements Comparable<Student>, Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="numer_albumu")
    private String numberOfAlbum;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    //@DontSerialize
    @Enumerated(EnumType.STRING)
    @Column(name = "stan")
    private StudentCondition condition;
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @Column(name = "points")
    private double points = 0.0;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "studenci")
    List<Subject> przedmioty = new ArrayList<>();

    public List<Subject> getPrzedmioty(){
        return przedmioty;
    }

    public void setPrzedmioty(List<Subject> p1){
        przedmioty=p1;
    }

    Student() {}

    Student(String nr, String n, String s, StudentCondition c, int y, double p) {
        numberOfAlbum = nr;
        name = n;
        surname = s;
        condition = c;
        yearOfBirth = y;
        points = p;
    }
    Student(String nr, String n, String s, int y, double p) {
        numberOfAlbum = nr;
        name = n;
        surname = s;
        yearOfBirth = y;
        points = p;
    }

    Student(String nr, String n, String s, StudentCondition c, int y) {
        numberOfAlbum = nr;
        name = n;
        surname = s;
        condition = c;
        yearOfBirth = y;
    }

    Student(String n, String s, String p) {
        name = n;
        surname = s;
        points = Double.parseDouble(p);
    }

    Student(String n){
        this.surname = n;
    }

    @Override
    public int compareTo(Student s) {
        return this.surname.compareTo(s.surname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    void addPoints(double p){
        this.points += p;
    }

    void removePoints(double p){
        this.points -= p;
    }

    void setCondition(StudentCondition c){
        this.condition = c;
    }

    void setCondition(String s){
        this.condition = StudentCondition.valueOf(s);
    }

    public int getId() {return this.id;}

    public void setId(int id1) {this.id=id1;}

    double getPoints(){
        return this.points;
    }

    String getName(){
        return this.name;
    }

    String getSurname(){
        return this.surname;
    }

    StudentCondition getCondition(){
        return this.condition;
    }

    void setPoints(double x) { this.points=x;}

    void print() {
        System.out.println("Student data:");
        System.out.println("Number of album: " + this.numberOfAlbum);
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Student condition: " + this.condition);
        System.out.println("Year of bith: " + this.yearOfBirth);
        System.out.println("Points: " + this.points);
    }

    public int getYearOfBirth(){
        return yearOfBirth;
    }

    public String getNumberOfAlbum(){
        return numberOfAlbum;
    }

    public void setName(String s){
        name=s;
    }

    public void setSurame(String s){
        surname=s;
    }

    public void setNumberOfAlbum(String a){
        numberOfAlbum=a;
    }

    public void setYearOfBirth(Integer a){
        yearOfBirth = a;
    }
}
