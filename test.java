import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class test extends JPanel {
    JFileChooser chooser;
    JFrame frame;
    JPanel panel;
    public test() {
        frame = new JFrame();
        panel = new JPanel();
        JButton sef = new JButton("Select a File");
        sef.addActionListener(new action1());
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

    public test(boolean b) {
        JButton accept = new JButton();
    }

    private class action1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Pick a File");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                System.out.println(chooser.getSelectedFile());
                test second = new test(true);

            } else {
                System.out.println("Exit");
            }
        }
    }

    private class action2 implements ActionListener {
        public void actionPerformed (ActionEvent e) {

        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        test t = new test();
    }
}
