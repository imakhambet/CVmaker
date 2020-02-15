import Entity.Zamestnanec;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class changePassword {
    private JPasswordField newPassword2;
    private JButton changeBut;
    private JButton back;
    private JPasswordField newPassword;
    private JLabel error;
    private JPanel changePassword;

    JFrame frame;

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    //set login window
    void setPanelcCP() {

        frame = new JFrame("Login");
        frame.setContentPane(changePassword);
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(360, 320);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    public changePassword() {
        changeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new String(newPassword.getPassword()).equals(new String(newPassword2.getPassword()))) {
                    error.setText("");
                    try {
                        Scanner sc = new Scanner();

                        entityMgr.getTransaction().begin();
                        TypedQuery<Zamestnanec> query =
                                entityMgr.createQuery("SELECT e FROM Zamestnanec e " +
                                        "WHERE e.username = '" + sc.getUsername() + "' ", Zamestnanec.class);
                        List<Zamestnanec> resultsID = query.getResultList();

                        resultsID.get(0).setPassword(new String(newPassword.getPassword()));
                        entityMgr.getTransaction().commit();

                        frame.dispose();
                    } catch (Exception e1) {
                        System.out.println(e1 + "change password");
                    }
                }else{
                    error.setText("Passwords don't match");
                }

            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public JPasswordField getNewPassword2() {
        return newPassword2;
    }

    public JButton getChangeBut() {
        return changeBut;
    }

    public JPasswordField getNewPassword() {
        return newPassword;
    }
}
