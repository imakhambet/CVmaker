package Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="zamestnanec")
public class Zamestnanec {

    @OneToMany(mappedBy = "zamestnanec", cascade=CascadeType.PERSIST )
    private List<Autor> autor;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="heslo")
    private String password;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}