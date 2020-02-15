package Entity;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name="zivotopis")
public class CV {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cv")
    private Collection<Zamestnavatel> zamestnavatel;

    public void setZamestnavatel(Collection<Zamestnavatel> zamestnavatel) {
        this.zamestnavatel = zamestnavatel;
    }

    @OneToOne(mappedBy = "cv")
    private Autor autor;

    @Column(name="pozice")
    private String position;

    @Column(name="plat")
    private Integer salary;

    @Column(name="jmeno")
    private String firstN;

    @Column(name="prijmeni")
    private String lastN;

    @Column(name="vek")
    private Integer age;

    @Column(name="pohlavi")
    private String sex;

    @Column(name="mesto")
    private String city;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    public int getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public Integer getSalary() {
        return salary;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getFirstN() {
        return firstN;
    }

    public String getLastN() {
        return lastN;
    }

    public Integer getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setFirstN(String firstN) {
        this.firstN = firstN;
    }

    public void setLastN(String lastN) {
        this.lastN = lastN;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}