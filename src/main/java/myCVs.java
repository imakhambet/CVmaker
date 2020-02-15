import Entity.Autor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class myCVs {
    JPanel myCv;
    private JButton backButton;
    private JButton createNewCVButton;
    private JPanel CVs;
    JFrame frame;
    public static JFrame frame1;

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    public  static  int number;

    public myCVs () {
        entityMgr.getTransaction().begin();

        constructCV();
    }

    void constructCV(){
        int size = 0;


            Scanner sc = new Scanner();

            TypedQuery<Autor> query =
                    entityMgr.createQuery("SELECT e FROM Autor e " +
                            "WHERE e.zamestnanec.username = '"+ sc.getUsername()+"' order by e.cv.id desc " , Autor.class);

            List<Autor> resultsID = query.getResultList();

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

           for(Autor autor: resultsID) {
               idN.add(autor.getCv().getId());

               position.add(autor.getCv().getPosition());
               salary.add(autor.getCv().getSalary());
               try {

                   firstN.add(autor.getCv().getFirstN());
                   lastN.add(autor.getCv().getLastN() + autor.getCv().getId());
                   age.add(autor.getCv().getAge());
                   phone.add(autor.getCv().getPhone());
                   city.add(autor.getCv().getCity());
                   sex.add(autor.getCv().getSex());
                   email.add(autor.getCv().getEmail());
                   }catch (Exception e1){
                       System.err.println(e1);
                   }
               }

            size = resultsID.size();
            CvDraw draw = new CvDraw();
            draw.mydraw(size, idN, firstN, lastN, age, city, phone,sex, email, position, salary, CVs, frame1);

    }
    //set my CVs window
    void setMyCVs() {

        frame = new JFrame("Menu");
        frame.setContentPane(myCv);

        JScrollPane scrollBar = new JScrollPane(myCv,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame1 = new JFrame("My CVs");
        frame1.add(scrollBar);
        frame1.pack();
        frame1.setVisible(true);

        scrollBar.getVerticalScrollBar().setUnitIncrement(12);


        frame1.setSize(500, 500);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.dispose();
            }
        });

        createNewCVButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateCV createCV = new CreateCV();
                createCV.setCVCreator();
                frame1.dispose();
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
    }

    void setNumber(int id){
        number = id;
    }

    int getNumber(){
        return number;
    }

    void closeframe(){
        frame1.dispose();
    }

}
