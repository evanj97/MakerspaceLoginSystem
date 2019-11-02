package src;

import javax.swing.*;
import java.awt.*;

public class NewUserWindow
{
    private Font titleFont = new Font(Font.SANS_SERIF, 0, 28);
    private Font labelFont = new Font(Font.SANS_SERIF, 0, 20);
    private Font fieldFont = new Font(Font.SANS_SERIF, 0, 18);
    private Font buttonFont = new Font(Font.SANS_SERIF, 0, 18);


    JFrame frm;
    private JPanel pnl;


    private JLabel titleLabel;

    // fName
    private JLabel fNameLabel;
    JTextField fNameField;
    private JPanel fNamePanel;

    // lName
    private JLabel lNameLabel;
    JTextField lNameField;
    private JPanel lNamePanel;

    // SID
    private JLabel SIDLabel;
    JTextField SIDField;
    private JPanel SIDPanel;

    // email
    private JLabel emailLabel;
    JTextField emailField;
    private JPanel emailPanel;

    // buttons
    JButton loginBtn;
    JButton cancelBtn;
    private JPanel buttonPanel;


    public NewUserWindow()
    {

        frm = new JFrame("New User");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        Dimension windowSize = new Dimension((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));

        // setup main panel
        pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));


        // titleLabel
        titleLabel = new JLabel("New User");
        titleLabel.setAlignmentX((float) 0.5);
        titleLabel.setFont(titleFont);


        // fNameLabel
        fNameLabel = new JLabel("First Name: ");
        fNameLabel.setAlignmentX((float) 0.5);
        fNameLabel.setFont(labelFont);
        // fNameField
        fNameField = new JTextField();
        fNameField.setAlignmentX((float) 0.5); // set left/right alignment to the center
        fNameField.setMaximumSize(new Dimension(500, 100));
        fNameField.setColumns(20);
        fNameField.setFont(fieldFont);
        // fNamePanel
        fNamePanel = new JPanel();
        fNamePanel.setLayout(new BoxLayout(fNamePanel, BoxLayout.LINE_AXIS));
        fNamePanel.add(fNameLabel);
        fNamePanel.add(fNameField);


        // lNameLabel
        lNameLabel = new JLabel("Last Name: ");
        //lNameLabel.setAlignmentX((float) 0.5);
        lNameLabel.setFont(labelFont);
        // lNameField
        lNameField = new JTextField();
        //lNameField.setAlignmentX((float) 0.5); // set left/right alignment to the center
        lNameField.setMaximumSize(new Dimension(500, 100));
        lNameField.setColumns(20);
        lNameField.setFont(fieldFont);
        // lNamePanel
        lNamePanel = new JPanel();
        lNamePanel.setLayout(new BoxLayout(lNamePanel, BoxLayout.LINE_AXIS));
        lNamePanel.add(lNameLabel);
        lNamePanel.add(lNameField);


        // SIDLabel
        SIDLabel = new JLabel("Student ID: ");
        SIDLabel.setAlignmentX((float) 0.5);
        SIDLabel.setFont(labelFont);
        // SIDField
        SIDField = new JTextField();
        SIDField.setAlignmentX((float) 0.5); // set left/right alignment to the center
        SIDField.setMaximumSize(new Dimension(500, 100));
        SIDField.setColumns(9);
        SIDField.setFont(fieldFont);
        // SIDPanel
        SIDPanel = new JPanel();
        SIDPanel.setLayout(new BoxLayout(SIDPanel, BoxLayout.LINE_AXIS));
        SIDPanel.add(SIDLabel);
        SIDPanel.add(SIDField);


        // emailLabel
        emailLabel = new JLabel("Student Email: ");
        emailLabel.setAlignmentX((float) 0.5);
        emailLabel.setFont(labelFont);
        // emailField
        emailField = new JTextField();
        emailField.setAlignmentX((float) 0.5); // set left/right alignment to the center
        emailField.setMaximumSize(new Dimension(500, 100));
        emailField.setColumns(40);
        emailField.setFont(fieldFont);
        // emailPanel
        emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.LINE_AXIS));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);


        // loginBtn
        loginBtn = new JButton("Login");
        loginBtn.setFont(buttonFont);
        loginBtn.setAlignmentX((float) 0.5);
        loginBtn.setSize(new Dimension(400, 200)); // UNKNOWN IF THIS WORKS ON WIN/LINUX
        // cancelBtn
        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(buttonFont);
        cancelBtn.setAlignmentX((float) 0.5);
        cancelBtn.setSize(new Dimension(400, 200)); // UNKNOWN IF THIS WORKS ON WIN/LINUX
        // buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        frm.add(pnl);

        pnl.add(Box.createVerticalGlue());
        pnl.add(titleLabel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(fNamePanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(lNamePanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(SIDPanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(emailPanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(buttonPanel);
        pnl.add(Box.createVerticalGlue());


        frm.setPreferredSize(windowSize);
        frm.setSize(windowSize);
        // center on screen
        frm.setLocation((screenSize.width / 2) - (windowSize.width / 2), (screenSize.height / 2) - (windowSize.height / 2));


        frm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frm.setVisible(false);
        frm.pack();


    }

}
