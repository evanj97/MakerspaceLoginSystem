/*

Makerspace Sign-in system, by Evan Johnson

if SID is known, create timestamp entry and display "signed-in" pop-up window TODO
--GUI Done TODO make this a messageDialog, not a JFrame

if SID is NOT known, display dialogue offering to try again OR first time sign-in TODO
if "try again" is selected, close popup.
--GUI Done

If first time sign-in, show new popup with fName, lName, email, and SID fields TODO
on loginBtn press, verify and timestamp, display "signed-in" pop-up window
on cancelBtn press, go back to initial login screen
--GUI Done

after GUI in place, add data-handling, add timestamp/sysTime stuff

regex to verify inputs --DONE

error if trying to add SID that already exists

TODO make all fonts dependent on screen resolution

 */

package src;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class MakerspaceLoginSystem
{
    private static InitialLoginWindow initialLoginWindow;
    private static FailedLoginWindow failedLoginWindow;
    private static NewUserWindow newUserWindow;
    private static SQLiteDB db;
    private static ReportWindow reportWindow;

    public static void main(String args[])
    {
        // TODO for testing formatting of the windows
        db = new SQLiteDB();
        initialLoginWindow = new InitialLoginWindow();
        failedLoginWindow = new FailedLoginWindow();
        // SuccessfulLoginWindow successfulLoginWindow = new SuccessfulLoginWindow(); // REPLACE
        newUserWindow = new NewUserWindow();
        reportWindow = new ReportWindow();

        createActionListeners();


        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        /*
        new Thread(() -> // DEBUG TOOL – CAN BE SAFELY DELETED
        {
            try
            {
                Thread.sleep(120000); // will auto-kill after 1 minute
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.exit(0);
        }).start();
        */
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


    }


    private static void createActionListeners()
    {

        // INITIAL LOGIN WINDOW

        // login with existing user, or display failedLoginWindow
        initialLoginWindow.loginBtn.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        login();
                    }
                }
        );

        initialLoginWindow.SIDfield.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        login();
                    }
                }
        );

        initialLoginWindow.createReportMenuItem.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        reportWindow.frm.setVisible(true);
                        reportWindow.frm.toFront();
                    }
                }
        );


        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // NEW USER WINDOW

        // attempt to login with a new user.  validate inputs, add student to DB, add timestamp to DB, close
        newUserWindow.loginBtn.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String rawSID = trimSpaces(newUserWindow.SIDField.getText());
                        String fName = trimSpaces(newUserWindow.fNameField.getText());
                        String lName = trimSpaces(newUserWindow.lNameField.getText());
                        String email = trimSpaces(newUserWindow.emailField.getText());

                        if (!rawSID.matches("\\d{9}"))
                        {
                            JOptionPane.showMessageDialog(null, "Student ID must be 9 numerical digits", "Invalid Student ID", JOptionPane.ERROR_MESSAGE);

                        } else if (!fName.matches("[\\w\\s-']{1,40}"))
                        {
                            JOptionPane.showMessageDialog(null, "First name must be between 1 and 40 characters, with no illegal characters", "Invalid First Name", JOptionPane.ERROR_MESSAGE);

                        } else if (!lName.matches("[\\w\\s-']{1,40}"))
                        {
                            JOptionPane.showMessageDialog(null, "Last name must be between 1 and 40 characters, with no illegal characters", "Invalid Last Name", JOptionPane.ERROR_MESSAGE);

                            // old regex = "[^@]+(\\.[^@]+)*@bellevuecollege\\.edu"


                        } else if (!email.matches("[\\w\\-]+(\\.[\\w\\-]+)*@bellevuecollege\\.edu")) //
                        {
                            JOptionPane.showMessageDialog(null, "Invalid Bellevue College Email", "Invalid Bellevue College Email", JOptionPane.ERROR_MESSAGE);

                        } else
                        {
                            int sid = Integer.parseInt(rawSID);

                            if (db.isKnownSID(sid))
                            {
                                JOptionPane.showMessageDialog(null, "Student ID already exists", "SID Already Exists", JOptionPane.ERROR_MESSAGE);
                            } else
                            {
                                System.out.println("1: " + sid);
                                System.out.println("2: " + fName);
                                System.out.println("3: " + lName);
                                System.out.println("4: " + email);

                                // add user to DB
                                db.addNewStudent(sid, fName, lName, email);

                                // clock in new user
                                db.signInStudent(sid);

                                // show success message
                                JOptionPane.showMessageDialog(null, "Sign-in successful!", "Success!", JOptionPane.PLAIN_MESSAGE);

                                // close window after login
                                newUserWindow.frm.setVisible(false);
                                newUserWindow.SIDField.setText("");
                                newUserWindow.fNameField.setText("");
                                newUserWindow.lNameField.setText("");
                                newUserWindow.emailField.setText("");
                            }


                        }
                    }
                }
        );

        // close window, and clear fields for safety
        newUserWindow.cancelBtn.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        newUserWindow.frm.setVisible(false);
                        newUserWindow.SIDField.setText("");
                        newUserWindow.fNameField.setText("");
                        newUserWindow.lNameField.setText("");
                        newUserWindow.emailField.setText("");
                    }
                }
        );

        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // FAILED LOGIN WINDOW

        // just close the window, to allow the user to retry inputting their SID
        failedLoginWindow.retryBtn.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        failedLoginWindow.frm.setVisible(false);
                    }
                }
        );

        // close window and replace with the newUserWindow, so the user can be added to the system
        failedLoginWindow.newUserBtn.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        failedLoginWindow.frm.setVisible(false); // close window

                        newUserWindow.SIDField.setText("");
                        newUserWindow.fNameField.setText("");
                        newUserWindow.lNameField.setText("");
                        newUserWindow.emailField.setText("");
                        newUserWindow.frm.setVisible(true);
                        newUserWindow.frm.toFront();
                    }
                }
        );

        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        // REPORT WINDOW

        reportWindow.reportDateBtn.addActionListener( // TODO
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String startDate = trimSpaces(reportWindow.startDateField.getText());
                        String endDate = trimSpaces(reportWindow.endDateField.getText());

                        String regex = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";

                        if ((!startDate.matches(regex)) || (!endDate.matches(regex)))
                        {
                            JOptionPane.showMessageDialog(null, "Date must be in format \"YYYY-MM-DD HH:MM:SS\"", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
                        } else
                        {
                            // if prints successfully
                            if (CSVPrinter.printTimeframe(startDate, endDate, db.retrieveAll(reportWindow.startDateField.getText(), reportWindow.endDateField.getText())))
                            {
                                // then print success message
                                String msg = "Successfully created .csv in " + System.getProperty("user.dir");
                                JOptionPane.showMessageDialog(null, msg, "Success!", JOptionPane.PLAIN_MESSAGE);
                            } else
                            {
                                // else print error message
                                String msg = "File failed to successfully print!  Make sure it is not in use by another program!";
                                JOptionPane.showMessageDialog(null, msg, "Failure!", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                }
        );

        reportWindow.reportAllBtn.addActionListener( // TODO
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        // if prints successfully
                        if (CSVPrinter.printAll(db.retrieveAll()))
                        {
                            reportWindow.totalField.setText("" + db.countTotalLogins());
                            reportWindow.distinctField.setText("" + db.countDistinctStudents());

                            // then print success message
                            String msg = "Successfully created .csv in " + System.getProperty("user.dir") + ".";
                            JOptionPane.showMessageDialog(null, msg, "Success!", JOptionPane.PLAIN_MESSAGE);
                        } else
                        {
                            // else print error message
                            String msg = "File failed to successfully print!  Make sure it is not in use by another program!";
                            JOptionPane.showMessageDialog(null, msg, "Failure!", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
        );

        reportWindow.closeBtn.addActionListener( // TODO
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        reportWindow.frm.setVisible(false);
                    }
                }
        );


    }

    public static void login()
    {
        String rawSID = trimSpaces(initialLoginWindow.SIDfield.getText());
        if (!rawSID.matches("\\d{9}")) // 9 [0-9] digits
        {
            JOptionPane.showMessageDialog(null, "Student ID must be 9 numerical digits", "Invalid Student ID", JOptionPane.ERROR_MESSAGE);

        } else
        {
            int sid = Integer.parseInt(rawSID);

            if (db.isKnownSID(sid))
            {
                db.signInStudent(sid); // if SID is in db, sign in

                initialLoginWindow.SIDfield.setText(""); // display success message, clear SID field for next person
                JOptionPane.showMessageDialog(null, "Sign-in successful!", "Success!", JOptionPane.PLAIN_MESSAGE);

            } else
            {
                // show FailedLoginWindow, clear SID
                initialLoginWindow.SIDfield.setText("");
                failedLoginWindow.frm.setVisible(true);
                failedLoginWindow.frm.toFront();
            }


        }
    }

    // trims spaces at beginning and end of a string, as well as any double, triple, etc. spaces within strings
    public static String trimSpaces(String s)
    {
        String result = s.trim(); // drop all spaces on the ends
        result = result.replaceAll("(\\s)+", " "); // remove excess spaces within string

        return result;
    }

}
