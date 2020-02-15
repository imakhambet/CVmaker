import Entity.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateCV {
    private JTextField firstName;
    private JComboBox sexChoose;
    private JTextField lastName;
    private JTextField city;
    private JTextField position;
    private JTextField salary;
    private JPanel cvCreator;
    private JSpinner age;
    private JButton submitButton;
    private JTextField email;
    private JButton backButton;
    private JButton myCVsButton;
    private JTextField phone;

    JTextArea xp;
    JTextArea edu;
    JTextPane skills;
    JTextPane langs;

    JFrame frame;
    JFrame frame1;

    ArrayList<String> err = new ArrayList<String>();

    //jpa set
    JPADemo jpad = new JPADemo();
    EntityManager entityMgr = jpad.getEntityManager();

    //set window
    void setCVCreator() {
        frame = new JFrame("Create your CV");
        frame.setContentPane(cvCreator);

        //set scrollbars
        JScrollPane scrollBar = new JScrollPane(cvCreator,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame1 = new JFrame("Create your CV");
        frame1.add(scrollBar);

        scrollBar.getVerticalScrollBar().setUnitIncrement(12);

        frame1.pack();
        frame1.setVisible(true);
        frame1.setSize(615, 615);


        //back to menu and close this menu
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.dispose();
            }
        });

        //close this window and go to window with Entity.CV
        myCVsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myCVs myCVs = new myCVs();
                myCVs.setMyCVs();
                frame1.dispose();
            }
        });

        //set window to center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
    }


    public CreateCV() {
        Scanner sc = new Scanner();

        //query to users tables
        TypedQuery<Zamestnanec> query = entityMgr.createQuery(
                "SELECT e FROM Zamestnanec e WHERE e.username = " +
                        "'"+sc.getUsername()+"'" , Zamestnanec.class);

        List<Zamestnanec> results = query.getResultList();

        //set fields if this information exists in the user information
        for(Zamestnanec user : results){
            TypedQuery<OsobniUdaje> queryID = entityMgr.createQuery(
                    "SELECT e FROM OsobniUdaje e WHERE e.zmc_id = "+user.getId()+"" , OsobniUdaje.class);
            List<OsobniUdaje> resultsOU = queryID.getResultList();
            for(OsobniUdaje ou: resultsOU ) {
                email.setText(ou.getEmail());
                firstName.setText(ou.getFirstName());
                lastName.setText(ou.getLastName());
                phone.setText(ou.getPhone());
            }
        }

        //action listener "Submit" button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                err.clear();

                entityMgr.getTransaction().begin();

                if(validation(email.getText(), phone.getText(), city.getText(),
                        position.getText(), salary.getText(), age.getValue().toString()) == 0) {

                    //parse information from fields
                    String positionN = position.getText().substring(0,1).toUpperCase()
                        + position.getText().substring(1);

                    int salaryN = Integer.parseInt(salary.getText());
                    String firstN = firstName.getText().substring(0,1).toUpperCase() + firstName.getText().substring(1);
                    String lastN = lastName.getText().substring(0,1).toUpperCase() + lastName.getText().substring(1);
                    String sex = sexChoose.getSelectedItem().toString();
                    String cityN = city.getText().substring(0,1).toUpperCase() + city.getText().substring(1);

                    int ageN = Integer.parseInt(age.getValue().toString());
                    String emailN = email.getText();
                    String phoneN = phone.getText();

                    CV cv = new CV();

                    try {
                        if (req == 0) {

                            cvSet(cv, positionN, salaryN, firstN, lastN, ageN, sex, cityN, emailN, phoneN);

                            Scanner sc = new Scanner();
                            TypedQuery<Zamestnanec> query =
                                    entityMgr.createQuery("SELECT e FROM Zamestnanec e WHERE e.username = '" + sc.getUsername() + "'", Zamestnanec.class);

                            List<Zamestnanec> results = query.getResultList();

                            for (Zamestnanec zamestnanec : results) {
                                Autor autor = new Autor();
                                autor.setCv(cv);
                                autor.setZamestnanec(zamestnanec);
                                entityMgr.persist(autor);
                            }
                        }
                        ok = true;

                        if(req == 0) {
                          //  JOptionPane.showMessageDialog(null, "Saved!");
                        }
                        entityMgr.getTransaction().commit();

                        frame1.dispose();

                    } catch (Exception e1) {
                        System.err.println("CV save " + e1);
                    }
                }else{
                    if(req == 0) {

                        String errS = " " + err.toString().replaceAll("^\\[|\\]$", "");
                        errS = errS.replaceAll("\\,", "\n");

                        entityMgr.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, errS);
                    }
                }
            }
        });
    }


    void cvSet(CV cv, String position, int salary, String firstN, String lastN, int ageN,
               String sex, String cityN, String emailN, String phoneN){
        try {
            cv.setPosition(position);
            cv.setSalary(salary);
            cv.setFirstN(firstN);
            cv.setLastN(lastN);
            cv.setAge(ageN);
            cv.setSex(sex);
            cv.setCity(cityN);
            cv.setEmail(emailN);
            cv.setPhone(phoneN);
            entityMgr.persist(cv);
        }catch (Exception e1){
            System.err.println("CV set " + e1);

        }

    }


    int validation(String email, String phone, String city, String position, String salary, String age) {

        int errors = 0;

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

        if(phone.length() == 0){
            errors+=1;
            err.add("Phone number is empty");
        }

        if(phone.length() != 9){
            errors+=1;
            err.add("Phone number isn't correct");
        }

        if(city.length() < 1){
            errors+=1;
            err.add("City is empty");
        }

        if(position.length() < 1){
            errors+=1;
            err.add("Prefered position is empty");
        }

        if(!isInt(age) || Integer.parseInt(age)==0){
            errors+=1;
            err.add("Age isn't correct");
        }

        if(!isInt(salary) || salary.length() < 1){
            errors+=1;
            err.add("Salary isn't correct");
        }

        return errors;
    }

    static boolean isInt(String s)  // assuming integer is in decimal number system
    {
        for(int a=0;a<s.length();a++)
        {
            if(a==0 && s.charAt(a) == '-') continue;
            if( !Character.isDigit(s.charAt(a)) ) return false;
        }
        return true;
    }

    boolean ok;
    public static int req = 0;

    public static void setReq(int r) {
        req = r;
    }


    public boolean isOk() {
        return ok;
    }

    public JTextField getFirstName() {
        return firstName;
    }

    public JComboBox getSexChoose() {
        return sexChoose;
    }

    public JTextField getLastName() {
        return lastName;
    }

    public JTextField getCity() {
        return city;
    }

    public JTextField getPosition() {
        return position;
    }

    public JTextField getSalary() {
        return salary;
    }

    public JSpinner getAge() {
        return age;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JTextField getEmail() {
        return email;
    }

    public JButton getMyCVsButton() {
        return myCVsButton;
    }

    public JTextField getPhone() {
        return phone;
    }

}
