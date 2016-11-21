import models.Params;
import models.TestingParams;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Class to display window with fileChooser, checkboxes and start button
 */
public class FlakyTestApp {

    private String mFileID;
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
        JLabel labelSelectFolder = new JLabel("Selectionner votre dossier contenant les fichiers à tester");
        labelSelectFolder.setBorder(new EmptyBorder(0, 0, 10, 0));
        mJTextField = new JTextField();
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            JFileChooser mJFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
            mJFileChooser.setDialogTitle("Select Location");
            mJFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            mJFileChooser.setAcceptAllFileFilterUsed(true);

            if (mJFileChooser.showSaveDialog(mJFrame) == JFileChooser.APPROVE_OPTION) {
                mFileID = mJFileChooser.getSelectedFile().getPath();
                mJTextField.setText(mFileID);
            }
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

        mCheckBoxDataInstance = new JCheckBox("Analyser les instances de date", true);
        centerPanel.add(mCheckBoxDataInstance);
        mCheckBoxNetworkCalls = new JCheckBox("Analyser les appels web services", true);
        centerPanel.add(mCheckBoxNetworkCalls);
        mCheckBoxCheckFile = new JCheckBox("Analyser les opérations sur les fichiers", true);
        centerPanel.add(mCheckBoxCheckFile);
        mCheckBoxCheckTestAnnotation = new JCheckBox("Verifier si les annotations '@Test' sont présentes", true);
        centerPanel.add(mCheckBoxCheckTestAnnotation);

        mJPanelMain.add(centerPanel, BorderLayout.CENTER);
    }

    private void addStartButton() {
        JButton startButton = new JButton("Démarrer les tests");
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
