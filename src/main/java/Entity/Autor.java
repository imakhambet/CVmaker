package Entity;

import javax.persistence.*;

@Entity
@Table(name="autor")
public class Autor {

    @ManyToOne
    @JoinColumn(unique = true, name = "zamestnanec")
    private Zamestnanec zamestnanec;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true, name = "zivotopis")
    private CV cv;


    public Zamestnanec getZamestnanec() {
        return zamestnanec;
    }

    public CV getCv() {
        return cv;
    }

    public void setZamestnanec(Zamestnanec zamestnanec) {
        this.zamestnanec = zamestnanec;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }
}
