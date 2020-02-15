import Entity.*;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UpdateCV {
    private JTextField firstName;
    private JComboBox sexChoose;
    private JTextField lastName;
    private JTextField city;
    private JTextField position;
    private JTextField salary;
    private JPanel cvCreator;
    private JSpinner age;
    private JButton updateButton;
    private JTextField email;
    private JButton backButton;
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
        frame = new JFrame("Update CV");
        frame.setContentPane(cvCreator);

        //set scrollbars
        JScrollPane scrollBar = new JScrollPane(cvCreator,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame1 = new JFrame("Update CV");
        frame1.add(scrollBar);

        scrollBar.getVerticalScrollBar().setUnitIncrement(12);

        frame1.pack();
        frame1.setVisible(true);
        frame1.setSize(615, 615);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //back to menu and close this menu
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.dispose();
                Menu menu = new Menu();
                menu.setMenu();
            }
        });

        //set window to center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
    }


    public UpdateCV(int cv) {
        int cvN = cv;
        System.out.println(cvN);

        CV cvFind = entityMgr.find(CV.class, cvN);
        email.setText(cvFind.getEmail());
        firstName.setText(cvFind.getFirstN());
        lastName.setText(cvFind.getLastN());
        phone.setText(cvFind.getPhone());
        age.setValue(cvFind.getAge());
        sexChoose.setSelectedItem(cvFind.getSex());
        position.setText(cvFind.getPosition());
        salary.setText(cvFind.getSalary().toString());
        city.setText(cvFind.getCity());

        //action listener "Submit" button
        updateButton.addActionListener(new ActionListener() {
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

                    CV cv = entityMgr.find(CV.class, cvN);

                    try {
                        cvUpdate(cv, positionN, salaryN, firstN, lastN, ageN, sex, cityN, emailN, phoneN);

                       // JOptionPane.showMessageDialog(null, "Updated!");

                        entityMgr.getTransaction().commit();

                        frame1.dispose();
                        Menu menu = new Menu();
                        menu.setMenu();
                    } catch (Exception e1) {
                        System.err.println("CV save " + e1);
                    }
                }else{
                    String errS = " "+err.toString().replaceAll("^\\[|\\]$", "");
                    errS = errS.replaceAll("\\,", "\n");

                    entityMgr.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, errS);
                }
            }
        });
    }


    void cvUpdate(CV cv, String position, int salary, String firstN, String lastN, int ageN,
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

    public JTextField getFirstName() {
        return firstName;
    }

    public JSpinner getAge() {
        return age;
    }

    public JButton getUpdateButton() {
        return updateButton;
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

    public JTextField getEmail() {
        return email;
    }

    public JTextField getPhone() {
        return phone;
    }
}
