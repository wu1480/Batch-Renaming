import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Pattern;

public class test extends JPanel {
    JFileChooser chooser;
    JFrame frame;
    JPanel panel;
    JPanel panel2;
    String selectedFileName;
    JTextField regexTextField;
    public test() {
        frame = new JFrame();
        panel = new JPanel();
        JButton sef = new JButton("Select a File");
        sef.addActionListener(new actionSelectFile());
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
        accept.addActionListener(new actionAcceptRename());
        add(accept);

        JPanel panel3 = new JPanel();

        JButton selectAll = new JButton("Select All");
        selectAll.addActionListener(new actionSelectAll());
        add(selectAll);

        JButton deselectAll = new JButton("Deselect All");
        deselectAll.addActionListener(new actionDeselectAll());
        add(deselectAll);

        JPanel regexPanel = new JPanel();
        regexTextField = new JTextField("\\[Keep\\]");
        regexTextField.setPreferredSize(new Dimension(200, 30));
        JButton updateRegex = new JButton("Update Regex");
        updateRegex.addActionListener(new actionUpdateRegex());
        regexPanel.add(new JLabel("Regex:"));
        regexPanel.add(regexTextField);
        regexPanel.add(updateRegex);



        String[] inside = file.list();

        for (String s : inside) {
            JCheckBox temp = new JCheckBox(s);
            updateCheckboxText(temp, s, regexTextField.getText());
            temp.setSelected(true);
            panel2.add(temp);
        }

        panel2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10,20));
        panel2.setLayout(new GridLayout(0, 1));
        panel2.add(accept);

        panel3.add(regexPanel);
        panel3.add(selectAll);
        panel3.add(deselectAll);
        panel3.setLayout(new GridLayout(1, 0));

        JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 1080)); // Adjust the dimensions as needed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel3, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Folders to Rename");
        frame.pack();
        frame.setVisible(true);
    }

    private void updateCheckboxText(JCheckBox checkBox, String folderName, String regex) {
        String oldName = folderName;
        String newName = applyRegexToFolderName(oldName, regex);
        checkBox.setText("<html><body>Old: " + oldName + "<br>New: " + newName + "</body></html>");
    }

    private String applyRegexToFolderName(String folderName, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(folderName).replaceAll("");
        } catch (Exception e) {
            e.printStackTrace();
            return folderName; // Return original name if regex is invalid
        }
    }

    private class actionSelectFile implements ActionListener {
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

    private class actionAcceptRename implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String regex = regexTextField.getText();
            for (Component c : panel2.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) c;
                    if (check.isSelected()) {
                        String oldName = check.getText().split("<br>")[0].substring(17); // Extract Old: line
                        File old = new File(selectedFileName + "\\" + oldName);
                        String newName = applyRegexToFolderName(oldName, regex);
                        File rename = new File(selectedFileName + "\\" + newName);
                        old.renameTo(rename);
                    }
                }
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private class actionDeselectAll implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            for (Component c : panel2.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) c;
                    check.setSelected(false);
                }
            }
        }
    }

    private class actionSelectAll implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            for (Component c : panel2.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) c;
                    check.setSelected(true);
                }
            }
        }
    }

    private class actionUpdateRegex implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String newRegex = regexTextField.getText();
            for (Component c : panel2.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) c;
                    if (check.isSelected()) {
                        String oldName = check.getText().split("<br>")[0].substring(17);
                        updateCheckboxText(check, oldName, newRegex);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        test t = new test();
    }
}