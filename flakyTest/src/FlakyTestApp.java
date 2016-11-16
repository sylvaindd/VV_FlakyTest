import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Guillaume on 16/11/2016.
 */
public class FlakyTestApp {

    private String mFileID;
    private JTextField mJTextField;
    private JFrame mJFrame;
    private JPanel mJPanelMain;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new FlakyTestApp().displayApp();
    }

    private void createWindow() {
        mJFrame = new JFrame();

        mJFrame.setTitle("Flaky Test App");
        mJFrame.setSize(500, 500);
        mJFrame.setResizable(false);
        mJFrame.setLocationRelativeTo(null);
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mJPanelMain = new JPanel();
        mJPanelMain.setLayout(new BorderLayout());

        mJFrame.getContentPane().add(mJPanelMain);
    }

    private void addFolderChooser() {
        JLabel labelSelectFolder = new JLabel("Selectionner votre dossier contenant les tests");
        mJTextField = new JTextField();
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser mJFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
                mJFileChooser.setDialogTitle("Select Location");
                mJFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                mJFileChooser.setAcceptAllFileFilterUsed(false);

                if (mJFileChooser.showSaveDialog(mJFrame) == JFileChooser.APPROVE_OPTION)
                {
                    mFileID = mJFileChooser.getSelectedFile().getPath();
                    mJTextField.setText(mFileID);
                }

            }
        });

        JPanel headerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridwidth = 6;
        constraints.gridx = 0;
        constraints.gridy = 0;
        headerPanel.add(labelSelectFolder, constraints);

        constraints.weightx = 1;
        constraints.gridwidth = 5;
        constraints.gridy = 1;
        headerPanel.add(mJTextField, constraints);

        constraints.weightx = 0.1;
        constraints.gridwidth = 1;
        constraints.gridx = 6;
        headerPanel.add(browseButton, constraints);

        mJPanelMain.add(headerPanel, BorderLayout.NORTH);
    }

    private void displayApp(){
        createWindow();
        addFolderChooser();
        mJFrame.setVisible(true);
    }
}
