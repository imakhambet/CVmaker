package Entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "zamestnavatel")
public class Zamestnavatel {


    @ManyToMany
    @JoinTable(name = "prohlizeni",
            joinColumns = @JoinColumn(name = "zamestnavatel", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "zivotopis", referencedColumnName = "id")
    )
    private Collection<CV> cv;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "email")
    String email;

    @Column(name = "heslo")
    String heslo;

    @Column(name = "mesto")
    String mesto;

    @Column(name = "ulice")
    String ulice;

    @Column(name = "nazev")
    String nazev;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHeslo() {
        return heslo;
    }

    public String getMesto() {
        return mesto;
    }

    public String getUlice() {
        return ulice;
    }

    public String getNazev() {
        return nazev;
    }

    public Collection<CV> getCv() {
        return cv;
    }

    public void setCv(Collection<CV> cv) {
        this.cv = cv;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
