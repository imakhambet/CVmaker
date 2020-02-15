import Entity.CV;
import Entity.Zamestnavatel;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CvDraw {

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();



    void mydraw(int size, ArrayList<Integer> idN, ArrayList<String> firstN, ArrayList<String> lastN,
              ArrayList<Integer> age, ArrayList<String> city, ArrayList<String> phone,
              ArrayList<String>sex, ArrayList<String> email, ArrayList<String> position, ArrayList<Integer> salary,
              JPanel CVs, JFrame frame1) {

        Integer[] idArr = new Integer[size];
        String[] firstNArr = new String[size];
        String[] lastNArr = new String[size];
        Integer[] ageArr = new Integer[size];
        String[] cityArr = new String[size];
        String[] phoneArr = new String[size];
        String[] sexArr = new String[size];
        String[] emailArr = new String[size];
        String[] posArr = new String[size];
        Integer[] salArr = new Integer[size];

        idArr = idN.toArray(idArr);
        firstNArr = firstN.toArray(firstNArr);
        lastNArr = lastN.toArray(lastNArr);
        ageArr = age.toArray(ageArr);
        cityArr = city.toArray(cityArr);
        phoneArr = phone.toArray(phoneArr);
        sexArr = sex.toArray(sexArr);
        emailArr = email.toArray(emailArr);
        posArr = position.toArray(posArr);
        salArr = salary.toArray(salArr);

        CVs.setLayout(new BoxLayout(CVs, BoxLayout.Y_AXIS));

        JLabel[] nameLb = new JLabel[firstNArr.length];
        JLabel[] ageLb = new JLabel[ageArr.length];
        JLabel[] cityLb = new JLabel[cityArr.length];
        JLabel[] sexLb = new JLabel[sexArr.length];
        JLabel[] emailLb = new JLabel[emailArr.length];
        JLabel[] phoneLb = new JLabel[phoneArr.length];
        JLabel[] posLb = new JLabel[posArr.length];
        JLabel[] salLb = new JLabel[salArr.length];

        for (int i = 0; i < size; i++) {

            JPanel jp = new JPanel();

            setLabels(i, jp, CVs, nameLb, ageLb, sexLb, cityLb, emailLb, phoneLb, posLb, salLb,
                    firstNArr, lastNArr, ageArr, sexArr, cityArr, emailArr, phoneArr, posArr, salArr, Color.lightGray);

            JButton but = new JButton();
            but.setSize(100,100);
            but.setText("Create PDF file");
            but.setName(idArr[i].toString());
            jp.add(Box.createVerticalStrut(5));
            jp.add(but);
            CVs.add(Box.createVerticalStrut(3));

            JButton delBut = new JButton();
            delBut.setSize(100,100);
            delBut.setText("Delete CV");
            delBut.setName(idArr[i].toString());
            jp.add(Box.createVerticalStrut(5));
            jp.add(delBut);

            JButton updateBut = new JButton();
            updateBut.setSize(100,100);
            updateBut.setText("Update CV");
            updateBut.setName(idArr[i].toString());
            jp.add(Box.createVerticalStrut(5));
            jp.add(updateBut);
            CVs.add(Box.createVerticalStrut(10));

            but.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    myCVs my = new myCVs();

                    System.out.println(but.getName());
                    my.setNumber(Integer.parseInt(but.getName()));
                    PathChooser pathChooser = new PathChooser();
                    pathChooser.setChooser();
                }
            });

            delBut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    entityMgr.getTransaction().begin();
                    System.out.println(delBut.getName());
                    CV cv = entityMgr.find(CV.class, Integer.parseInt(delBut.getName()));
                    entityMgr.remove(cv);
                    entityMgr.getTransaction().commit();
                    myCVs my = new myCVs();
                    my.closeframe();
                }
            });

            updateBut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    myCVs my = new myCVs();
                    my.closeframe();
//                    frame1.dispose();
                    UpdateCV ucv = new UpdateCV(Integer.parseInt(updateBut.getName()));
                    ucv.setCVCreator();

                }
            });

        }
    }
    void searchDraw(int size, ArrayList<Integer> idN, ArrayList<String> firstN, ArrayList<String> lastN,
              ArrayList<Integer> age, ArrayList<String> city, ArrayList<String> phone,
              ArrayList<String>sex, ArrayList<String> email, ArrayList<String> position, ArrayList<Integer> salary,
              JPanel CVs) {

        Integer[] idArr = new Integer[size];
        String[] firstNArr = new String[size];
        String[] lastNArr = new String[size];
        Integer[] ageArr = new Integer[size];
        String[] cityArr = new String[size];
        String[] phoneArr = new String[size];
        String[] sexArr = new String[size];
        String[] emailArr = new String[size];
        String[] posArr = new String[size];
        Integer[] salArr = new Integer[size];

        idArr = idN.toArray(idArr);
        firstNArr = firstN.toArray(firstNArr);
        lastNArr = lastN.toArray(lastNArr);
        ageArr = age.toArray(ageArr);
        cityArr = city.toArray(cityArr);
        phoneArr = phone.toArray(phoneArr);
        sexArr = sex.toArray(sexArr);
        emailArr = email.toArray(emailArr);
        posArr = position.toArray(posArr);
        salArr = salary.toArray(salArr);

        CVs.setLayout(new BoxLayout(CVs, BoxLayout.Y_AXIS));

        JLabel[] nameLb = new JLabel[firstNArr.length];
        JLabel[] ageLb = new JLabel[ageArr.length];
        JLabel[] cityLb = new JLabel[cityArr.length];
        JLabel[] sexLb = new JLabel[sexArr.length];
        JLabel[] emailLb = new JLabel[emailArr.length];
        JLabel[] phoneLb = new JLabel[phoneArr.length];
        JLabel[] posLb = new JLabel[posArr.length];
        JLabel[] salLb = new JLabel[salArr.length];

        for (int i = 0; i < size; i++) {

            JPanel jp = new JPanel();
            setLabels(i, jp, CVs, nameLb, ageLb, sexLb, cityLb, emailLb, phoneLb, posLb, salLb,
                    firstNArr, lastNArr, ageArr, sexArr, cityArr, emailArr, phoneArr, posArr, salArr, Color.lightGray);

            JButton but = new JButton();
            but.setSize(100, 100);
            but.setText("Create PDF file");
            but.setName(idArr[i].toString());
            jp.add(Box.createVerticalStrut(5));
            jp.add(but);
            CVs.add(Box.createVerticalStrut(10));

            but.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    entityMgr.getTransaction().begin();
                    myCVs my = new myCVs();

                    my.setNumber(Integer.parseInt(but.getName()));

                    PathChooser pathChooser = new PathChooser();
                    pathChooser.setChooser();

                    Scanner sc = new Scanner();

                    TypedQuery<Zamestnavatel> query =
                            entityMgr.createQuery("SELECT e FROM Zamestnavatel e " +
                                    "WHERE e.email = '" + sc.getEmail() + "' ", Zamestnavatel.class);

                    List<Zamestnavatel> resultsID = query.getResultList();


                    try {
                        CV cv = entityMgr.find(CV.class, my.getNumber());

                        resultsID.get(0).getCv().add(cv);

                        entityMgr.persist(resultsID.get(0));

                        entityMgr.getTransaction().commit();
                    }catch (Exception e1) {
                        System.out.println(e1);
                    }

                }
            });
        }
    }

    //draw CVs that was viewed by Employer
    void viewedDraw(int size, ArrayList<Integer> idN, ArrayList<String> firstN, ArrayList<String> lastN,
              ArrayList<Integer> age, ArrayList<String> city, ArrayList<String> phone,
              ArrayList<String>sex, ArrayList<String> email, ArrayList<String> position, ArrayList<Integer> salary,
              JPanel CVs) {

        Integer[] idArr = new Integer[size];
        String[] firstNArr = new String[size];
        String[] lastNArr = new String[size];
        Integer[] ageArr = new Integer[size];
        String[] cityArr = new String[size];
        String[] phoneArr = new String[size];
        String[] sexArr = new String[size];
        String[] emailArr = new String[size];
        String[] posArr = new String[size];
        Integer[] salArr = new Integer[size];

        idArr = idN.toArray(idArr);
        firstNArr = firstN.toArray(firstNArr);
        lastNArr = lastN.toArray(lastNArr);
        ageArr = age.toArray(ageArr);
        cityArr = city.toArray(cityArr);
        phoneArr = phone.toArray(phoneArr);
        sexArr = sex.toArray(sexArr);
        emailArr = email.toArray(emailArr);
        posArr = position.toArray(posArr);
        salArr = salary.toArray(salArr);

        CVs.setLayout(new BoxLayout(CVs, BoxLayout.Y_AXIS));

        JLabel[] nameLb = new JLabel[firstNArr.length];
        JLabel[] ageLb = new JLabel[ageArr.length];
        JLabel[] cityLb = new JLabel[cityArr.length];
        JLabel[] sexLb = new JLabel[sexArr.length];
        JLabel[] emailLb = new JLabel[emailArr.length];
        JLabel[] phoneLb = new JLabel[phoneArr.length];
        JLabel[] posLb = new JLabel[posArr.length];
        JLabel[] salLb = new JLabel[salArr.length];

        for (int i = 0; i < size; i++) {

            JPanel jp = new JPanel();
            setLabels(i, jp, CVs, nameLb, ageLb, sexLb, cityLb, emailLb, phoneLb, posLb, salLb,
                    firstNArr, lastNArr, ageArr, sexArr, cityArr, emailArr, phoneArr, posArr, salArr, Color.orange);


            //set delete button for viewed CV
            JButton delBut = new JButton();
            delBut.setSize(100,100);
            delBut.setText("Delete CV");
            delBut.setName(idArr[i].toString());
            jp.add(Box.createVerticalStrut(5));
            jp.add(delBut);
            CVs.add(Box.createVerticalStrut(10));

            //delete viewed CV from table
            delBut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    entityMgr.getTransaction().begin();
                    try {

                        CV cv = entityMgr.find(CV.class, Integer.parseInt(delBut.getName()));
                        Scanner sc =new Scanner();
                        String queryStr = "SELECT e FROM Zamestnavatel e where e.email ='"+sc.getEmail()+"'";

                        TypedQuery<Zamestnavatel> query = entityMgr.createQuery(
                                    queryStr, Zamestnavatel.class);

                        List<Zamestnavatel> results = query.getResultList();

                        results.get(0).getCv().remove(cv);
                        entityMgr.persist(results.get(0));
                        entityMgr.getTransaction().commit();


                        Search s = new Search();
                        CVs.removeAll();
                        s.frame1Close();
                        s.setSearch();
                        s.clickViewed();
                    }catch (Exception e1){
                        System.err.println(e1);
                    }
                }
            });
        }
    }

    //create labels from arrays
    void setLabels(int i, JPanel jp, JPanel CVs, JLabel[] nameLb, JLabel[] ageLb, JLabel[] sexLb, JLabel[] cityLb,
                   JLabel[] emailLb, JLabel[] phoneLb, JLabel[] posLb, JLabel[] salLb, String[] firstNArr,
                   String[] lastNArr, Integer[] ageArr, String[] sexArr, String[] cityArr, String[] emailArr,
                   String[] phoneArr, String[] posArr, Integer[] salArr, Color color) {

        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        Border border = jp.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);

        jp.setBorder(new CompoundBorder(border, margin));
        jp.setBackground(color);

        nameLb[i] = new JLabel();
        nameLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Name: </span> " +
                "<span style=\"font-size:11.5px;\">" + ' ' + firstNArr[i] + ' ' + lastNArr[i] + "</span></html>");

        ageLb[i] = new JLabel();
        ageLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Age: </span> " +
                "<span style=\"font-size:11.5px;\">" + ' ' + ageArr[i] + "</span></html>");

        sexLb[i] = new JLabel();
        sexLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Sex: </span> " +
                "<span style=\"font-size:11px;\">" + ' ' + sexArr[i] + "</span></html>");

        cityLb[i] = new JLabel();
        cityLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">City: </span> " +
                "<span style=\"font-size:11.5px;\">" + cityArr[i] + "</span></html>");

        emailLb[i] = new JLabel();
        emailLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Email: </span> " +
                "<span style=\"font-size:11.5px;\">" + emailArr[i] + "</span></html>");

        phoneLb[i] = new JLabel();
        phoneLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Phone: </span> " +
                "<span style=\"font-size:11.5px;\">" + phoneArr[i] + "</span></html>");

        posLb[i] = new JLabel();
        posLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Prefered position: </span> " +
                "<span style=\"font-size:11.5px;\">" + posArr[i] + "</span></html>");

        salLb[i] = new JLabel();
        salLb[i].setText("<html><span style=\"font-size:11.5px; font-weight:bold;\">Prefered salary: </span> " +
                "<span style=\"font-size:11.5px;\">" + salArr[i] + "</span></html>");


        CVs.add(jp, BorderLayout.CENTER);

        setLabel(jp, nameLb[i]);
        setLabel(jp, ageLb[i]);
        setLabel(jp, sexLb[i]);
        setLabel(jp, cityLb[i]);
        setLabel(jp, emailLb[i]);
        setLabel(jp, phoneLb[i]);
        setLabel(jp, posLb[i]);
        setLabel(jp, salLb[i]);

        jp.add(Box.createVerticalStrut(5));

    }

    //add label to panel
    void setLabel(JPanel jp,JLabel label){
        jp.add(label);
        jp.add(Box.createVerticalStrut(5));
    }
}
