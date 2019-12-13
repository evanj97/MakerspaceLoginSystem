/*
    Made for Bellevue College RISE Makerspace by Evan Johnson

    Project Description:
    Digital sign-in system.  New users input their first name, last name, student ID number,
    and college email.  When a user is in the system already, all they need to sign in is
    their student ID number, greatly shortening sign-in time.

    Class Description:  contains 2 public static functions that generate .csv files containing
    student information.

 */

package src;

import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CSVPrinter
{
    // create a .csv file that contains a record of every sign-in performed, including all student data
    public static boolean printAll(ResultSet RS)
    {
        boolean successful = true;

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDT = now.format(formatter);

        try (PrintWriter printWriter = new PrintWriter(new File((formattedDT + " total attendance.csv")), "UTF-8"))
        {
            printWriter.write(buildString(RS));

        } catch (Exception e)
        {
            System.out.println("problem in CSVPrinter.printAll() ");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            successful = false;

        }
        return successful;
    }

    // create a .csv file that contains a record of every sign-in performed within a range, (given a ResultSet of data within that range)
    public static boolean printTimeframe(String startDate, String endDate, ResultSet RS)
    {
        boolean successful = true;

        try (PrintWriter printWriter = new PrintWriter(new File(("attendance from " + startDate.substring(10) + " to " + endDate.substring(10) + ".csv")), "UTF-8"))
        {
            printWriter.write(buildString(RS));

        } catch (Exception e)
        {
            System.out.println("problem in CSVPrinter.printTimeframe() ");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            successful = false;
        }
        return successful;
    }

    // build a .csv formatted string to fill the .csv file with
    private static String buildString(ResultSet RS)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("SID,First Name,Last Name,Email,Timestamp\n"); // first line of CSV

        try
        {
            while (RS.next())
            {
                sb.append("" + RS.getInt("SID") + ",");
                sb.append(RS.getString("FNAME") + ",");
                sb.append(RS.getString("LNAME") + ",");
                sb.append(RS.getString("EMAIL") + ",");
                sb.append(RS.getString("timestamp") + "\n");
            }


        } catch (SQLException e)
        {
            System.out.println("problem in CSVPrinter.buildString() ");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return sb.toString();
    }
}
