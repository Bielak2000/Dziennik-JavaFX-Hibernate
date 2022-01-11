import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Rating")
public class Rating implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="Przedmiot")
    private String przedmiot;

    @Column(name="Ocena")
    private int ocena;

    @Column(name="Data")
    private String data;

    @Column(name="Komentarz")
    @NotNull
    private String kom;

    public Rating() {
    }

    Rating(int o, String d, String k, String p){
        ocena=o;
        data=d;
        kom=k;
        przedmiot=p;
    }

    public int getId() {
        return id;
    }

    public int getOcena() {
        return ocena;
    }

    public String getData() {
        return data;
    }

    public String getKom() {
        return kom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setKom(String kom) {
        this.kom = kom;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }
}
