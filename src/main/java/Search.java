import Entity.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Search {
    JPanel myCv;
    private JPanel CVs;
    private JTextField positionField;
    private JButton Search;
    private JLabel Position;
    private JTextField cityField;
    private JLabel notFound;
    private JButton viewedButton;

    public static JFrame frame1;

    public static int number;

    public static int sizeG;

    JFrame frame = new JFrame();
    Scanner sc = new Scanner();

    public Search () {


        JPADemo jpa = new JPADemo();
        EntityManager entityMgr = jpa.getEntityManager();


        Search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int size = 0;

                CVs.removeAll();

                try {
                    entityMgr.getTransaction().begin();

                    String queryStr = "";
                    if(positionField.getText().length() > 0 && cityField.getText().length() == 0){
                        queryStr += "SELECT e FROM CV e where lower(e.position) = " +
                                "'"+positionField.getText().toLowerCase()+"' order by e.id desc";
                    }
                    else if(positionField.getText().length() == 0 && cityField.getText().length() > 0){
                        queryStr += "SELECT e FROM CV e where lower(e.city) = " +
                                "'"+cityField.getText().toLowerCase()+"' order by e.id desc";
                    }
                    else if (positionField.getText().length() > 0 && cityField.getText().length() > 0){
                        queryStr += "SELECT e FROM CV e where lower(e.position) = " +
                                "'"+positionField.getText().toLowerCase()+"' and lower(e.city) = " +
                                "'"+cityField.getText().toLowerCase()+"' order by e.id desc";
                    }else if (positionField.getText().length() == 0 && cityField.getText().length() == 0){
                        queryStr += "SELECT e FROM CV e order by e.id desc";
                    }

                    TypedQuery<CV> query =
                            entityMgr.createQuery(queryStr, CV.class);
                    List<CV> results = query.getResultList();

                    if(results.size() == 0){
                        notFound.setText("Not found");
                    }
                    else {
                        notFound.setText("");
                    }
                    ArrayList<Integer> idN = new ArrayList<Integer>();
                    ArrayList<String> firstN = new ArrayList<String>();
                    ArrayList<String> lastN = new ArrayList<String>();
                    ArrayList<Integer> age = new ArrayList<Integer>();
                    ArrayList<String> city = new ArrayList<String>();
                    ArrayList<String> phone = new ArrayList<String>();
                    ArrayList<String> sex = new ArrayList<String>();
                    ArrayList<String> email = new ArrayList<String>();
                    ArrayList<String> position = new ArrayList<String>();
                    ArrayList<Integer> salary = new ArrayList<Integer>();

                    for(CV cv: results){
                        idN.add(cv.getId());
                        firstN.add(cv.getFirstN());
                        lastN.add(cv.getLastN());
                        age.add(cv.getAge());
                        phone.add(cv.getPhone());
                        city.add(cv.getCity());
                        sex.add(cv.getSex());
                        email.add(cv.getEmail());
                        position.add(cv.getPosition());
                        salary.add(cv.getSalary());
                    }
                    size = results.size();

                    sizeG = size;
                    CvDraw cvd = new CvDraw();
                    cvd.searchDraw(size, idN, firstN, lastN, age, city, phone,sex, email, position, salary, CVs);


                    entityMgr.getTransaction().commit();

                    myCv.revalidate();
                    myCv.repaint();

                }catch (Exception e1){
                    System.err.println("not match");
                }
            }
        });
        viewedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = 0;

                CVs.removeAll();
                
                try {
                    entityMgr.getTransaction().begin();

                    String queryStr = "SELECT e.cv FROM Zamestnavatel e where e.email ='"+sc.getEmail()+"' ";

                    TypedQuery<CV> query =
                            entityMgr.createQuery(queryStr, CV.class);
                    List<CV> results = query.getResultList();

                    if(results.get(0) == null){
                        notFound.setText("Not viewed");
                    }
                    else {
                        notFound.setText("");
                        ArrayList<Integer> idN = new ArrayList<Integer>();
                        ArrayList<String> firstN = new ArrayList<String>();
                        ArrayList<String> lastN = new ArrayList<String>();
                        ArrayList<Integer> age = new ArrayList<Integer>();
                        ArrayList<String> city = new ArrayList<String>();
                        ArrayList<String> phone = new ArrayList<String>();
                        ArrayList<String> sex = new ArrayList<String>();
                        ArrayList<String> email = new ArrayList<String>();
                        ArrayList<String> position = new ArrayList<String>();
                        ArrayList<Integer> salary = new ArrayList<Integer>();

                        for(CV pr: results){
                            idN.add(pr.getId());
                            firstN.add(pr.getFirstN());
                            lastN.add(pr.getLastN());
                            age.add(pr.getAge());
                            phone.add(pr.getPhone());
                            city.add(pr.getCity());
                            sex.add(pr.getSex());
                            email.add(pr.getEmail());
                            position.add(pr.getPosition());
                            salary.add(pr.getSalary());
                        }
                        size = results.size();

                        CvDraw cvd = new CvDraw();
                        cvd.viewedDraw(size, idN, firstN, lastN, age, city, phone,sex, email, position, salary, CVs);



                    }
                    entityMgr.getTransaction().commit();
                    myCv.revalidate();
                    myCv.repaint();

                }catch (Exception e1){
                    System.err.println("Viewed CVs " + e1);

                }
            }
        });
    }


    //set my CVs window
    void setSearch() {
        frame = new JFrame(sc.getEmail());
        frame.setContentPane(myCv);

        JScrollPane scrollBar = new JScrollPane(myCv,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame1 = new JFrame(sc.getEmail());
        frame1.setDefaultCloseOperation(frame1.EXIT_ON_CLOSE);


        scrollBar.getVerticalScrollBar().setUnitIncrement(12);

        frame1.add(scrollBar);
        frame1.pack();
        frame1.setVisible(true);

        frame1.setSize(500, 500);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
    }

    void frame1Close(){
        frame1.dispose();
    }
    void clickViewed(){
        viewedButton.doClick();
    }

    public JTextField getPositionField() {
        return positionField;
    }

    public JButton getSearch() {
        return Search;
    }

    public JTextField getCityField() {
        return cityField;
    }
}
