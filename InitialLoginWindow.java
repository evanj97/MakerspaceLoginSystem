/*
    Made for Bellevue College RISE Makerspace by Evan Johnson

    Project Description:
    Digital sign-in system.  New users input their first name, last name, student ID number,
    and college email.  When a user is in the system already, all they need to sign in is
    their student ID number, greatly shortening sign-in time.

    Class Description:  Displays the main sign-in window the user first sees when approaching the system.

 */


package src;


import javax.swing.*;
import java.awt.*;

public class InitialLoginWindow
{
    // element fonts
    private static Font titleFont = new Font(Font.SANS_SERIF, 0, 48);
    private static Font sidFont = new Font(Font.SANS_SERIF, 0, 24);
    private static Font loginBtnFont = new Font(Font.SANS_SERIF, 0, 24);

    JFrame frm;
    JPanel pnl;


    ImageIcon icon;
    JLabel iconLabel;

    JLabel SIDLabel;
    JTextField SIDfield;
    JPanel SIDPanel;

    JButton loginBtn;

    JMenuBar menuBar;
    JMenu menu;
    JMenuItem createReportMenuItem;


    public InitialLoginWindow()
    {
        // Setting up JFrame
        frm = new JFrame("Makerspace Sign-in V1.1");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        frm.setPreferredSize(screenSize);
        frm.setSize(screenSize);

        // Setting up Main Panel
        pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

        // icon
        icon = new ImageIcon("RISE signin.png");
        iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX((float) 0.5);


        // SID Label
        SIDLabel = new JLabel("Student ID: ");
        SIDLabel.setAlignmentX((float) 0.5);
        SIDLabel.setFont(sidFont);

        // SID Field
        SIDfield = new JTextField();
        SIDfield.setAlignmentX((float) 0.5); // set left/right alignment to the center
        SIDfield.setMaximumSize(new Dimension(250, 100));
        SIDfield.setColumns(9);
        SIDfield.setFont(sidFont);

        // SID Panel
        SIDPanel = new JPanel();

        // buttonPanel
        SIDPanel = new JPanel();
        SIDPanel.setLayout(new BoxLayout(SIDPanel, BoxLayout.LINE_AXIS));
        SIDPanel.add(Box.createHorizontalGlue());
        SIDPanel.add(SIDLabel);
        SIDPanel.add(SIDfield);
        SIDPanel.add(Box.createHorizontalGlue());

        // loginBtn
        loginBtn = new JButton("Login");
        loginBtn.setFont(loginBtnFont);
        loginBtn.setAlignmentX((float) 0.5);
        loginBtn.setSize(new Dimension(500, 200)); // UNKNOWN IF THIS WORKS ON WIN/LINUX

        frm.add(pnl);

        pnl.add(Box.createVerticalGlue());
        pnl.add(iconLabel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(SIDPanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(loginBtn);
        pnl.add(Box.createVerticalGlue());


        // MENU
        menuBar = new JMenuBar();
        menu = new JMenu("Admin");
        createReportMenuItem = new JMenuItem("Create Attendance Report");

        frm.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(createReportMenuItem);

        // MENU


        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        frm.pack();

    }


}
