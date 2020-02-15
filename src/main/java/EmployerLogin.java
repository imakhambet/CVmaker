import Entity.Zamestnavatel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EmployerLogin {
    private JPasswordField password;
    private JTextField email;
    private JButton loginButton;
    private JButton back;
    private JPanel login;
    JFrame frame;

    String emailStr;

    ArrayList<String> err = new ArrayList<String>();//array of errors


    public EmployerLogin() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailStr = email.getText();
                char[] passwordCh = EmployerLogin.this.password.getPassword();

                err.clear();

                if(validation(emailStr, passwordCh) == 0){
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("src/main/resources/loginEmpl.txt", StandardCharsets.UTF_8);
                    } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    assert writer != null;
                    writer.print(emailStr); //write to file Username
                    writer.close();

                    //set menu and close this window
                    Search search = new Search();
                    search.setSearch();
                    frame.dispose();
                } else {
                    String errS = err.toString().replaceAll("^\\[|\\]$", "");
                    errS = errS.replaceAll("\\,", "\n");
                    JOptionPane.showMessageDialog(null, errS);
                }
            }
        });
        //set action for back button
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPage mp = new MainPage();
                mp.setMP();
                frame.dispose();
            }
        });
    }

    void setPanelLogin() {
        frame = new JFrame("Login");
        frame.setContentPane(login);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(320, 320);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    //validation
    int validation(String email, char[] password){

        //set jpa
        JPADemo jpa = new JPADemo();
        EntityManager entityMgr = jpa.getEntityManager();
        entityMgr.getTransaction().begin();

        int errors = 0;
        if(email.length() < 1){
            errors+=1;
            err.add("Email is empty");
        }
        if(new String(password).length() < 1){
            errors+=1;
            err.add("Password is empty");
        }
        try {
            TypedQuery<Zamestnavatel> query =
                    entityMgr.createQuery("SELECT c.heslo FROM Zamestnavatel c " +
                            "WHERE lower(c.email) = '" + email.toLowerCase() + "'", Zamestnavatel.class);
            java.util.List<Zamestnavatel> results = query.getResultList();

            if (!results.toString().substring(1, results.toString().length() - 1).equals(new String(password))) {
                errors++;
                err.add("Not correct login/password");
            }
        }catch (Exception e2){
            System.err.println("not match");
        }
        return errors;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JTextField getEmail() {
        return email;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
