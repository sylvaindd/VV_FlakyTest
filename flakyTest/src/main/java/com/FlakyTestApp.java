package com;

import com.models.Params;
import com.models.TestingParams;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Class to display window with fileChooser, checkboxes and start button
 */
public class FlakyTestApp {

    private JTextField mJTextField;
    private JFrame mJFrame;
    private JPanel mJPanelMain;

    private JCheckBox mCheckBoxDataInstance;
    private JCheckBox mCheckBoxNetworkCalls;
    private JCheckBox mCheckBoxCheckTestAnnotation;
    private JCheckBox mCheckBoxCheckFile;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new FlakyTestApp().displayWindowApp();
    }

    private void createWindow() {
        mJFrame = new JFrame();

        mJFrame.setTitle("Flaky Test App");
        mJFrame.setSize(400, 300);
        mJFrame.setMinimumSize(new Dimension(400, 255));
        mJFrame.setResizable(true);
        mJFrame.setLocationRelativeTo(null);
        mJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mJPanelMain = new JPanel();
        mJPanelMain.setLayout(new BorderLayout());
        mJPanelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        mJFrame.getContentPane().add(mJPanelMain);
    }

    private void addFolderChooser() {
        JLabel labelSelectFolder = new JLabel("Selectionner le dossier contenant les classes à tester");
        labelSelectFolder.setBorder(new EmptyBorder(0, 0, 10, 0));
        mJTextField = new JTextField(new File(System.getProperty("user.home")).getPath());
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            File file = new File(System.getProperty("user.home"));
            if (mJTextField.getText().length() > 0) {
                file = new File(mJTextField.getText());
            }
            String filePath = file.getPath();
            if (SystemUtils.IS_OS_WINDOWS) {
                JFileChooser fileChooser = new JFileChooser(file);
                fileChooser.setDialogTitle("Selectionner le dossier");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(true);
                if (fileChooser.showSaveDialog(mJFrame) == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getPath();
                }
            } else if (SystemUtils.IS_OS_MAC_OSX) {
                System.setProperty("apple.awt.fileDialogForDirectories", "true");
                FileDialog fileDialog = new FileDialog(mJFrame);
                fileDialog.setTitle("Selectionner votre dossier");
                fileDialog.setMode(FileDialog.LOAD);
                fileDialog.setDirectory(file.getAbsolutePath());
                fileDialog.setVisible(true);
                System.setProperty("apple.awt.fileDialogForDirectories", "false");
                if (fileDialog.getDirectory() != null && fileDialog.getFile() != null)
                    filePath = fileDialog.getDirectory() + fileDialog.getFile();
            }
            mJTextField.setText(filePath);
        });

        JPanel headerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridwidth = 5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        headerPanel.add(labelSelectFolder, constraints);

        constraints.weightx = 1;
        constraints.gridwidth = 4;
        constraints.gridy = 1;
        headerPanel.add(mJTextField, constraints);

        constraints.weightx = 0.1;
        constraints.gridwidth = 1;
        constraints.gridx = 5;
        headerPanel.add(browseButton, constraints);

        mJPanelMain.add(headerPanel, BorderLayout.NORTH);
    }

    private void addCheckboxes() {
        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        centerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        mCheckBoxDataInstance = new JCheckBox("Analyser l'utilisation des dates", true);
        centerPanel.add(mCheckBoxDataInstance);
        mCheckBoxNetworkCalls = new JCheckBox("Analyser l'utilisation des web services", true);
        centerPanel.add(mCheckBoxNetworkCalls);
        mCheckBoxCheckFile = new JCheckBox("Analyser l'utilisation des fichiers", true);
        centerPanel.add(mCheckBoxCheckFile);
        mCheckBoxCheckTestAnnotation = new JCheckBox("Verifier si les annotations \"@Test\" sont présentes", true);
        centerPanel.add(mCheckBoxCheckTestAnnotation);

        mJPanelMain.add(centerPanel, BorderLayout.CENTER);
    }

    private void addStartButton() {
        JButton startButton = new JButton("Démarrer l'analyse");
        startButton.addActionListener(e -> {
            TestingParams testingParams = new TestingParams();
            testingParams.put(Params.DATE, mCheckBoxDataInstance.isSelected());
            testingParams.put(Params.NETWORK, mCheckBoxNetworkCalls.isSelected());
            testingParams.put(Params.FILE, mCheckBoxCheckFile.isSelected());
            testingParams.put(Params.ANNOTATIONS, mCheckBoxCheckTestAnnotation.isSelected());

            new App(mJTextField.getText(), testingParams).start();
        });

        mJPanelMain.add(startButton, BorderLayout.SOUTH);
    }

    private void displayWindowApp() {
        createWindow();
        addFolderChooser();
        addCheckboxes();
        addStartButton();
        mJFrame.setVisible(true);
    }
}
