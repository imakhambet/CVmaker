import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Menu {
    private JButton myCVsButton;
    private JPanel panel1;
    private JButton createNewCVButton;
    private JLabel usernameLog;
    private JButton changePswrd;
    JFrame frame;

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    public Menu() {
        Scanner sc = new Scanner();
        usernameLog.setText(sc.getUsername()); //set username label

        EmptyBorder border1 = new EmptyBorder(0, 0, 0, 10);
        usernameLog.setBorder(border1);
        EmptyBorder border = new EmptyBorder(10, 0,40,0);
        panel1.setBorder(border);

        //set action for create new Entity.CV button
        createNewCVButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateCV ccv = new CreateCV();
                ccv.setCVCreator();
            }
        });

        //set action for my CVs button
        myCVsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myCVs my = new myCVs();
                my.setMyCVs();
            }
        });
        changePswrd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword cp = new changePassword();
                cp.setPanelcCP();
            }
        });
    }

    //set menu window
    void setMenu() {
        frame = new JFrame("Menu");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(300, 300);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2- frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
    }
}
