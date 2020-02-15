import Entity.Zamestnavatel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmplReg {
    private JButton buttonS;
    private JButton back;
    private JTextField email;
    private JTextField name;
    private JTextField city;
    private JTextField street;
    private JPasswordField password;
    private JPasswordField password2;
    private JPanel emprRegPan;

    JFrame frame;
    ArrayList<String> err = new ArrayList<String>();

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    public EmplReg() {
        back.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                back.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        MainPage mp = new MainPage();
                        mp.setMP();
                    }
                });
            }
        });
        buttonS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                err.clear();

                String emailStr = email.getText();
                char[] passwordStr = password.getPassword();
                char[] password2Str = password2.getPassword();
                String nameStr = name.getText();
                String cityStr = city.getText();
                String streetStr = street.getText();
                if(validation(emailStr, passwordStr, password2Str, nameStr, cityStr, streetStr)==0) {
                    signup(emailStr, passwordStr, nameStr, cityStr, streetStr);

                    frame.dispose();
                    EmployerLogin login = new EmployerLogin();
                    login.setPanelLogin();
                } else {
                    String errS = " "+err.toString().replaceAll("^\\[|\\]$", "");
                    errS = errS.replaceAll("\\,", "\n");
                    JOptionPane.showMessageDialog(null, errS);
                }

            }
        });
    }

    void setEmprReg() {
        frame = new JFrame("Employer registration");
        frame.setContentPane(emprRegPan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(450, 450);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    void signup(String email,char[] password, String nazev, String mesto, String street) {

        entityMgr.getTransaction().begin();

        Zamestnavatel zmvtl = new Zamestnavatel();
        try {
            zmvtl.setEmail(email.toLowerCase());
            zmvtl.setHeslo(new String(password));
            zmvtl.setMesto(mesto);
            zmvtl.setUlice(street);
            zmvtl.setNazev(nazev);
            entityMgr.persist(zmvtl);
        }catch (Exception e){
            System.err.println("Zamestnavatel save " + e);
        }

        entityMgr.getTransaction().commit();
        entityMgr.clear();
    }

    int validation(String email,char[] password,char[] password2, String nazev, String mesto, String street) {

        int errors = 0;

        JPADemo jpa = new JPADemo();
        EntityManager entityMgr = jpa.getEntityManager();
        entityMgr.getTransaction().begin();

        if(email.length() < 1){
            errors+=1;
            err.add("Email is empty");
        }
        if(nazev.length() < 1){
            errors+=1;
            err.add("Company name is empty");
        }
        if(mesto.length() < 1){
            errors+=1;
            err.add("City name is empty");
        }
        if(street.length() < 1){
            errors+=1;
            err.add("Street is empty");
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
            TypedQuery<Zamestnavatel> query =
                    entityMgr.createQuery("SELECT c FROM Zamestnavatel c WHERE lower(c.email) = '"+email.toLowerCase()+"'", Zamestnavatel.class);
            java.util.List<Zamestnavatel> results = query.getResultList();

            if(results.size() > 0){
                errors++;
                err.add("Email already taken");
            }
        }catch (Exception e1){
            System.out.println("email check " + e1);
        }
        try {
            TypedQuery<Zamestnavatel> query =
                    entityMgr.createQuery("SELECT c FROM Zamestnavatel c WHERE lower(c.nazev) = '"+nazev.toLowerCase()+"'" +
                            " AND lower(c.mesto) = '"+mesto.toLowerCase()+"'" +
                            "AND lower(c.ulice) = '"+street.toLowerCase()+"'", Zamestnavatel.class);
            java.util.List<Zamestnavatel> results = query.getResultList();

            if(results.size() > 0){
                errors++;
                err.add("This company already exists");
            }
        }catch (Exception e1){
            System.out.println("company check " + e1);
        }


        if(new String(password).length() < 3){
            errors+=1;
            if(new String(password).length() == 0) {
                err.add("Password is empty");
            }
            else if(new String(password).length() > 0){
                err.add("Password must be least 3 characters");
            }
        }

        else if(!new String(password).equals(new String(password2))){
            errors+=1;
            err.add("Passwords not same");
        }

        return errors;
    }

    public JButton getButtonS() {
        return buttonS;
    }

    public JTextField getEmail() {
        return email;
    }

    public JTextField getName() {
        return name;
    }

    public JTextField getCity() {
        return city;
    }

    public JTextField getStreet() {
        return street;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPasswordField getPassword2() {
        return password2;
    }
}
