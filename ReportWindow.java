/*
    Made for Bellevue College RISE Makerspace by Evan Johnson

    Project Description:
    Digital sign-in system.  New users input their first name, last name, student ID number,
    and college email.  When a user is in the system already, all they need to sign in is
    their student ID number, greatly shortening sign-in time.

    Class Description:  Administrator report window.  GUI window element used by the staff to
    create new .csv files containing student information and get a quick preview of the number
    of attendees.

 */

package src;

import javax.swing.*;
import java.awt.*;

public class ReportWindow
{

    // fonts for various elements
    private Font titleFont = new Font(Font.SANS_SERIF, 0, 24);
    private Font labelFont = new Font(Font.SANS_SERIF, 0, 18);
    private Font fieldFont = new Font(Font.SANS_SERIF, 0, 14);
    private Font buttonFont = new Font(Font.SANS_SERIF, 0, 18);
    private Font dateFont = new Font(Font.MONOSPACED, 0, 16);


    JFrame frm;
    private JPanel pnl;

    private JLabel titleLabel;

    // Date Panel
    private JPanel datePanel;
    JLabel startDateLabel;
    JLabel endDateLabel;
    JTextField startDateField;
    JTextField endDateField;

    // Report Panel
    private JPanel reportPanel;
    private JPanel statPanel;
    private JPanel btnPanel;

    // Stat Panel
    private JPanel distinctPanel;
    private JLabel distinctLabel;
    JTextField distinctField;

    private JPanel totalPanel;
    private JLabel totalLabel;
    JTextField totalField;

    // Btn Panel
    JButton reportDateBtn;
    JButton reportAllBtn;

    JButton closeBtn;

    public ReportWindow()
    {
        frm = new JFrame("Attendance Report");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        Dimension windowSize = new Dimension((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));

        // Setting up Main Panel
        pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

        //Title Label
        titleLabel = new JLabel("Attendance Report");
        titleLabel.setAlignmentX((float) 0.5);
        titleLabel.setFont(titleFont);

        // DATE PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // start date label
        startDateLabel = new JLabel("Start Date: ");
        startDateLabel.setAlignmentX((float) 0.5);
        startDateLabel.setFont(labelFont);
        // start date field
        startDateField = new JTextField();
        startDateField.setAlignmentX((float) 0.5);

        startDateField.setMaximumSize(new Dimension(300, 35));
        startDateField.setPreferredSize(new Dimension(300, 35));
        startDateField.setFont(dateFont);
        // end date label
        endDateLabel = new JLabel("End Date: ");
        endDateLabel.setAlignmentX((float) 0.5);
        endDateLabel.setFont(labelFont);
        // end date field
        endDateField = new JTextField();
        endDateField.setAlignmentX((float) 0.5);
        endDateField.setMaximumSize(new Dimension(300, 35));
        endDateField.setPreferredSize(new Dimension(300, 35));
        endDateField.setFont(dateFont);
        // date panel
        datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.LINE_AXIS));

        datePanel.add(Box.createHorizontalGlue());
        datePanel.add(startDateLabel);
        datePanel.add(startDateField);
        datePanel.add(Box.createHorizontalGlue());
        datePanel.add(endDateLabel);
        datePanel.add(endDateField);
        datePanel.add(Box.createHorizontalGlue());


        // STAT PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // distinct label
        distinctLabel = new JLabel("Number of Unduplicated Attendees: ");
        distinctLabel.setAlignmentX((float) 0.5);
        distinctLabel.setFont(labelFont);
        // distinct field
        distinctField = new JTextField();
        distinctField.setAlignmentX((float) 0.5);
        distinctField.setPreferredSize(new Dimension(200, 35));
        distinctField.setFont(fieldFont);
        distinctField.setEditable(false);
        // distinct panel
        distinctPanel = new JPanel();
        distinctPanel.setLayout(new BoxLayout(distinctPanel, BoxLayout.LINE_AXIS));

        distinctPanel.add(Box.createHorizontalGlue());
        distinctPanel.add(distinctLabel);
        distinctPanel.add(distinctField);
        distinctPanel.add(Box.createHorizontalGlue());

        // total label
        totalLabel = new JLabel("Total Number of Visits: ");
        totalLabel.setAlignmentX((float) 0.5);
        totalLabel.setFont(labelFont);
        // total field
        totalField = new JTextField();
        totalField.setAlignmentX((float) 0.5);
        totalField.setPreferredSize(new Dimension(200, 35));
        totalField.setFont(fieldFont);
        totalField.setEditable(false);
        // total panel
        totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));

        totalPanel.add(Box.createHorizontalGlue());
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);
        totalPanel.add(Box.createHorizontalGlue());

        // STAT PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        statPanel = new JPanel();
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.PAGE_AXIS));

        statPanel.add(Box.createVerticalGlue());
        statPanel.add(distinctPanel);
        statPanel.add(Box.createVerticalGlue());
        statPanel.add(totalPanel);
        statPanel.add(Box.createVerticalGlue());

        // BUTTON PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // report date button
        reportDateBtn = new JButton("Create Report");
        reportDateBtn.setFont(buttonFont);
        reportDateBtn.setAlignmentX((float) 0.5);
        reportDateBtn.setSize(400, 200);

        // report all button
        reportAllBtn = new JButton("Create Cumulative Report");
        reportAllBtn.setFont(buttonFont);
        reportAllBtn.setAlignmentX((float) 0.5);
        reportAllBtn.setSize(400, 200);

        // button panel
        btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));

        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(reportDateBtn);
        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(reportAllBtn);
        btnPanel.add(Box.createVerticalGlue());

        // REPORT PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * *

        reportPanel = new JPanel();
        reportPanel.add(statPanel);
        reportPanel.add(btnPanel);


        // CLOSE BUTTON * * * * * * * * * * * * * * * * * * * * * * * * * * *

        closeBtn = new JButton("Close");
        closeBtn.setFont(buttonFont);
        closeBtn.setAlignmentY((float) 0.5);
        closeBtn.setSize(400, 200);

        // MAIN PANEL * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        pnl.add(titleLabel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(datePanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(reportPanel);
        pnl.add(Box.createVerticalGlue());
        pnl.add(closeBtn);


        //BUILD

        frm.add(pnl);


        frm.setPreferredSize(windowSize);
        frm.setSize(windowSize);

        // center on screen
        frm.setLocation((screenSize.width / 2) - (windowSize.width / 2), (screenSize.height / 2) - (windowSize.height / 2));


        frm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frm.setVisible(false);
        frm.pack();


    }


}
