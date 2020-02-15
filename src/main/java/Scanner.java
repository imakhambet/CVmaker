import java.io.File;
import java.io.FileNotFoundException;

public class Scanner {
    java.util.Scanner sc;

    String username;

    String email;

    void setEmail() {
        try {
            sc = new java.util.Scanner(new File("src/main/resources/loginEmpl.txt"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        while(sc.hasNext()){
            email = sc.next();
        }
    }

    String getEmail() {
        setEmail();
        return email;
    }

    void setUsername() {
        try {
            sc = new java.util.Scanner(new File("src/main/resources/login.txt"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        while(sc.hasNext()){
            username = sc.next();
        }
    }

    String getUsername() {
        setUsername();
        return username;
    }
}
