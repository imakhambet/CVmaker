import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    private JButton signUpButton;
    private JButton loginButton;
    private JPanel mainPage;
    private JLabel logo;


    public JFrame frame;

    void setMP() {
        frame = new JFrame("Jobs");
        frame.setContentPane(mainPage);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(300, 300);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    MainPage() {
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App app = new App();
                app.setRegistration();
                frame.dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setPanelLogin();
                frame.dispose();
            }
        });

    }
}
