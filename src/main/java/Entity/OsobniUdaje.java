package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="osobniudaje")
public class OsobniUdaje {

    @Id
    @Column(name="zamestnanec")
    private int zmc_id;

    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private  Zamestnanec id;

    @Column(name="email")
    private String email;

    @Column(name="jmeno")
    private String firstName;

    @Column(name="prijmeni")
    private String lastName;

    @Column(name="phone")
    private String phone;


    public void setId(Zamestnanec zamestnanec) {
        this.id = id;
        this.zmc_id = zamestnanec.getId();
    }

    public int getId() {
        return zmc_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
