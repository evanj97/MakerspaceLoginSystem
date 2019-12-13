/*
    Made for Bellevue College RISE Makerspace by Evan Johnson

    Project Description:
    Digital sign-in system.  New users input their first name, last name, student ID number,
    and college email.  When a user is in the system already, all they need to sign in is
    their student ID number, greatly shortening sign-in time.

    Class Description:  Failed login window class.  Window shown to user when their student ID is
    not recognized.  Displays options to retry their SID or register as a new user.

 */

package src;

import javax.swing.*;
import java.awt.*;

public class FailedLoginWindow
{

    // element fonts
    private static Font messageFont = new Font(Font.SANS_SERIF, 0, 28);
    private static Font retryBtnFont = new Font(Font.SANS_SERIF, 0, 24);
    private static Font newUserBtnFont = new Font(Font.SANS_SERIF, 0, 24);


    JFrame frm;
    private JPanel pnl;

    private JTextArea messageLabel;
    JButton retryBtn;
    JButton newUserBtn;

    private JPanel buttonPanel;

    public FailedLoginWindow()
    {
        frm = new JFrame("Unknown SID");

        Toolkit tk = Toolkit.getDefaultToolkit();

        Dimension screenSize = tk.getScreenSize();
        Dimension windowSize = new Dimension(screenSize.width / 2, screenSize.height / 2);

        // Setting up Main Panel
        pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

        // messageLabel
        messageLabel = new JTextArea("Unknown SID.  If this is your first time visiting the Makerspace, select \"New User\" ");
        messageLabel.setAlignmentX((float) 0.5);
        messageLabel.setFont(messageFont);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setMaximumSize(new Dimension((int) (windowSize.width * 0.85), (int) (windowSize.height * 0.5)));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(new Color(255, 255, 255, 0));

        // retryBtn
        retryBtn = new JButton("Retry");
        retryBtn.setFont(retryBtnFont);
        retryBtn.setAlignmentX((float) 0.5);
        retryBtn.setSize(new Dimension(500, 200)); // UNKNOWN IF THIS WORKS ON WIN/LINUX

        // newUserBtn
        newUserBtn = new JButton("New User");
        newUserBtn.setFont(newUserBtnFont);
        newUserBtn.setAlignmentX((float) 0.5);
        newUserBtn.setSize(new Dimension(500, 200)); // UNKNOWN IF THIS WORKS ON WIN/LINUX

        // buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(retryBtn);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(newUserBtn);
        buttonPanel.add(Box.createHorizontalGlue());

        frm.add(pnl);

        pnl.add(Box.createVerticalGlue());
        pnl.add(messageLabel);
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
