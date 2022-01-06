import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class test extends JPanel {
    JFileChooser chooser;
    JFrame frame;
    JPanel panel;
    JPanel panel2;
    String selectedFileName;
    public test() {
        frame = new JFrame();
        panel = new JPanel();
        JButton sef = new JButton("Select a File");
        sef.addActionListener(new action1());
        add(sef);

        panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200 ,200));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(sef);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Renamer");
        frame.pack();
        frame.setVisible(true);
    }

    public test(File file) {
        selectedFileName = file.getPath();

        frame = new JFrame();
        panel2 = new JPanel();
        JButton accept = new JButton("Accept changes");
        accept.addActionListener(new action2());
        add(accept);

        String inside[] = file.list();

        for (String s : inside) {
            JCheckBox temp = new JCheckBox(s);
            temp.setSelected(true);
            panel2.add(temp);
        }

        panel2.setBorder(BorderFactory.createEmptyBorder(200, 200, 200 ,200));
        panel2.setLayout(new GridLayout(0, 1));
        panel2.add(accept);

        frame.add(panel2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Folders to Rename");
        frame.pack();
        frame.setVisible(true);
    }

    private class action1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Pick a File");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                new test(chooser.getSelectedFile());
            }
        }
    }

    private class action2 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            for (Component c : panel2.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) c;
                    if (check.isSelected()) {
                        File old = new File(selectedFileName + "\\" + check.getText());
                        String[] newName = old.getName().split("] ", 2);
                        if (newName.length > 1) {
                            File rename = new File(selectedFileName + "\\" + newName[1]);
                            old.renameTo(rename);
                        }
                    }
                }
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
