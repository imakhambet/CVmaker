import Entity.Zamestnanec;

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

public class Login {
    private JButton loginButton;
    private JTextField usernameL;
    private JPasswordField passwordL;
    private JPanel panelLogin;
    private JButton back;
    private JButton employerReg;

   // JFrame frame;
    JFrame frame;
    ArrayList<String> err = new ArrayList<String>();//array of errors
    String username;

    //set login window
    void setPanelLogin() {

        frame = new JFrame("Login");
        frame.setContentPane(panelLogin);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(320, 320);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2- frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
    }

    //validation
    int validation(String username, char[] password){

        //set jpa
        JPADemo jpa = new JPADemo();
        EntityManager entityMgr = jpa.getEntityManager();
        entityMgr.getTransaction().begin();

        int errors = 0;
        if(username.length() < 1){
            errors+=1;
            err.add("Username is empty");
        }
        if(new String(password).length() < 1){
            errors+=1;
            err.add("Password is empty");
        }
        try {
            TypedQuery<Zamestnanec> query =
                    entityMgr.createQuery("SELECT c.password FROM Zamestnanec c WHERE c.username = '" + username + "'", Zamestnanec.class);
            java.util.List<Zamestnanec> results = query.getResultList();

            if (!results.toString().substring(1, results.toString().length() - 1).equals(new String(password))) {
                errors++;
                err.add("Not correct login/password");
            }
        }catch (Exception e2){
            System.err.println("not match");
        }
        entityMgr.getTransaction().commit();

        return errors;

    }

    public Login() {
        //action listener for Login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                username = usernameL.getText();
                char[] password = passwordL.getPassword();

                err.clear();

                if(validation(username, password) == 0){
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("src/main/resources/login.txt", StandardCharsets.UTF_8);
                    } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    assert writer != null;
                    writer.print(username); //write to file Username
                    writer.close();

                    ok = true;

                    //set menu and close this window
                    frame.dispose();
                    Menu menu = new Menu();
                    menu.setMenu();
                } else {
                    if(req == 0) {

                        String errS = err.toString().replaceAll("^\\[|\\]$", "");
                        errS = errS.replaceAll("\\,", "\n");
                        JOptionPane.showMessageDialog(null, errS);
                    }
                }
            }
        });

        employerReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                EmployerLogin el = new EmployerLogin();
                el.setPanelLogin();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MainPage mp = new MainPage();
                mp.setMP();
            }
        });
    }

    boolean ok;
    public static int req = 0;

    public static void setReq(int r) {
        req = r;
    }

    public boolean isOk() {
        return ok;
    }
    public JTextField getUsernameL() {
        return usernameL;
    }

    public JPasswordField getPasswordL() {
        return passwordL;
    }
    public JButton getLoginButton() {
        return loginButton;
    }
}
