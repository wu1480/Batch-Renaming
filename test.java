import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class test extends JPanel implements ActionListener {
    JFileChooser chooser;
    JFrame frame;
    JPanel panel;
    public test() {
        frame = new JFrame();
        panel = new JPanel();
        JButton sef = new JButton("Select a File");
        sef.addActionListener(this);
        add(sef);
        JLabel label = new JLabel();
        label.setText("bye");

        panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200 ,200));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(sef);
        panel.add(label);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Renamer");
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Pick a File");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getSelectedFile());
        } else {
            System.out.println("Exit");
        }
    }

    public static void main(String[] args) {
        test t = new test();
    }
}
