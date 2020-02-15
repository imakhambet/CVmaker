import Entity.OsobniUdaje;
import Entity.Zamestnanec;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private JTextField usernameS;
    private JButton buttonS;
    private JPanel regPanel;
    private JPasswordField passwordS;
    private JTextField emailS;
    private JPasswordField password2S;
    private JButton back;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField telNumber;
    private JButton employerReg;

    JFrame frame;
    ArrayList<String> err = new ArrayList<String>();


    void setRegistration() {
        frame = new JFrame("Sign up");
        frame.setContentPane(regPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        frame.setSize(450, 450);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    int validation(String username,char[] password, char[] password2, String email) {

        int errors = 0;

        JPADemo jpad = new JPADemo();
        EntityManager entityMgr = jpad.getEntityManager();
        entityMgr.getTransaction().begin();
        if(email.length() < 1){
            errors+=1;
            err.add("Email is empty");
        }
        if(email.length() > 0){
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                errors+=1;
                err.add("Email is not valid");
            }
        }
        try {
            TypedQuery<Zamestnanec> query =
                    entityMgr.createQuery("SELECT c FROM Zamestnanec c WHERE c.username = '"+username+"'", Zamestnanec.class);
            java.util.List<Zamestnanec> results = query.getResultList();

            if(results.size() > 0){
                errors++;
                err.add("Username already taken");
            }
        }catch (Exception e1){
            System.out.println(e1);
        }
        if(username.length() < 3){
            errors+=1;
            if(username.length() == 0) {
                err.add("Username is empty");
            }
            else if(username.length() > 0){
                err.add("Username must be least 3 characters");
            }

        }

        if(new String(password).length() < 6){
            errors+=1;
            if(new String(password).length() == 0) {
                err.add("Password is empty");
            }
            else if(new String(password).length() > 0){
                err.add("Password must be least 6 characters");
            }
        }

        else if(!new String(password).equals(new String(password2))){
            errors+=1;
            err.add("Passwords not same");
        }

        return errors;
    }

    void signup(String username,char[] password, String email) {
        JPADemo jpa = new JPADemo();
        EntityManager entityMgr = jpa.getEntityManager();
        entityMgr.getTransaction().begin();

        Zamestnanec zamc = new Zamestnanec();
        OsobniUdaje ou = new OsobniUdaje();
        try {
            zamc.setUsername(username.toLowerCase());
            zamc.setPassword(new String(password));
            entityMgr.persist(zamc);
        }catch (Exception e){
            System.err.println("Zamestnanec save");
        }
        try {
            TypedQuery<Zamestnanec> query =
                    entityMgr.createQuery("SELECT c FROM Zamestnanec c " +
                            "WHERE lower(c.username) = '"+username.toLowerCase()+"'", Zamestnanec.class);

            java.util.List<Zamestnanec> results = query.getResultList();
            for(Zamestnanec zamestnanec : results) {
                ou.setId(zamestnanec);
                ou.setEmail(email.toLowerCase());
                if(firstName.getText().length()>0) {
                    ou.setFirstName(firstName.getText().substring(0, 1).toUpperCase()
                            + firstName.getText().substring(1));
                }
                if(lastName.getText().length()>0) {
                    ou.setLastName(lastName.getText().substring(0, 1).toUpperCase()
                            + lastName.getText().substring(1));
                }
                if(firstName.getText().length()>0) {
                    ou.setPhone(telNumber.getText());
                }
            }
        }catch (Exception e1){
            System.err.println("Osobni udaje save" + e1);
        }

        entityMgr.persist(ou);
        entityMgr.getTransaction().commit();
        entityMgr.clear();
    }

    public App() {
        buttonS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = usernameS.getText();
                char[] password = passwordS.getPassword();
                char[] password2 = password2S.getPassword();
                String email = emailS.getText();

                err.clear();

                if(validation(username, password, password2, email)==0) {
                    ok = true;
                    if(req==0) {
                        signup(username, password, email);
                    }
                    frame.dispose();
                    Login login = new Login();
                    login.setPanelLogin();
                } else {
                    if(req == 0) {

                        String errS = " " + err.toString().replaceAll("^\\[|\\]$", "");
                        errS = errS.replaceAll("\\,", "\n");
                        JOptionPane.showMessageDialog(null, errS);
                    }                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPage mp = new MainPage();
                mp.setMP();
                frame.dispose();
            }
        });
        employerReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmplReg er = new EmplReg();
                er.setEmprReg();
                frame.dispose();
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

    public JTextField getUsernameS() {
        return usernameS;
    }

    public JButton getButtonS() {
        return buttonS;
    }

    public JPasswordField getPasswordS() {
        return passwordS;
    }

    public JTextField getEmailS() {
        return emailS;
    }

    public JPasswordField getPassword2S() {
        return password2S;
    }

    public JTextField getFirstName() {
        return firstName;
    }

    public JTextField getLastName() {
        return lastName;
    }

    public JTextField getTelNumber() {
        return telNumber;
    }
}
