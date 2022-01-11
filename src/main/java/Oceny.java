import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Oceny")
public class Oceny implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="ocena")
    private double ocena;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Student")
    private Student s = new Student();

    public Oceny() {
    }

    Oceny(double o, Student s1){
        s = s1;
        ocena = o;
    }

    public Student getS(){
        return s;
    }

    public void setS(Student s1) {this.s=s1;}

    public int getId() {return this.id;}

    public void setId(int id1) {this.id=id1;}

    public Double getOcena() {
        return ocena;
    }

    public void setOcena(int o) {this.ocena=o;}

}
