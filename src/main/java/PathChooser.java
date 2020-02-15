import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class PathChooser extends JPanel {
    JFileChooser chooser;
    String choosertitle;
    JFrame frame;
    int id;

    public PathChooser() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("Users"));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            FileWriter fileWriter = new FileWriter();
            fileWriter.fileWrite(chooser.getSelectedFile().getPath());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }

    void setChooser(){
        frame = new JFrame("");
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}