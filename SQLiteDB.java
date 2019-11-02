package src;

import java.sql.*;

public class SQLiteDB
{
    private static Connection connection = null;
    private static Statement statement = null;

    // create student table
    private static final String CREATE_STUDENT_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS student " +
                    "(SID INTEGER PRIMARY KEY    NOT NULL, " +
                    " FNAME        VARCHAR(40)      NOT NULL, " +
                    " LNAME        VARCHAR(40)      NOT NULL, " +
                    " EMAIL        VARCHAR(50)      NOT NULL );";

    // create timestamp table
    private static final String CREATE_TIMELOG_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS login_time " +
                    "(SID           INTEGER      NOT NULL, " +
                    " LOGINTIME     TEXT            NOT NULL, " +
                    " FOREIGN KEY(SID) REFERENCES student(SID));";

    // inserts a new entry into Student table, complete with SID, fName, lName, and email
    private static final String INSERT_NEW_STUDENT_STATEMENT =
            "INSERT INTO student('SID', 'FNAME', 'LNAME', 'EMAIL') values (?, ?, ?, ?);";

    // inserts a new entry into Student table,
    private static final String LOGIN_STUDENT_STATEMENT =
            "INSERT INTO login_time(SID, LOGINTIME) values (?, datetime('now')); ";

    private static final String VERIFY_STUDENT_STATEMENT =
            "SELECT COUNT(*) FROM student WHERE sid = ? ; ";

    private static final String SELECT_ALL_STATEMENT =
            "SELECT student.SID, student.fname, student.lname, student.email, datetime(login_time.logintime, 'localtime') AS timestamp " +
                    " FROM student JOIN login_time " +
                    " ON student.SID = login_time.SID ; ";

    private static final String SELECT_TIME_FRAME_STATEMENT =
            "SELECT student.SID, student.fname, student.lname, student.email, datetime(login_time.logintime, 'localtime') AS timestamp " +
                    " FROM student JOIN login_time" +
                    " ON student.SID = login_time.SID" +
                    " WHERE login_time.logintime >= datetime(? , 'utc') AND login_time.logintime <= datetime(?, 'utc') ; ";

    private static final String COUNT_DISTINCT_STATEMENT =
            "SELECT count(*) as amount " +
                    " FROM student; ";

    private static final String COUNT_TOTAL_STATEMENT =
            "SELECT count(*) as amount " +
                    " FROM login_time; ";

    // datetime("timestring", 'utc') --convert timestring from localtime into utc
    // datetime("timestring", 'localtime') --convert timestring from utc into localtime

    private PreparedStatement insertNewStudent;
    private PreparedStatement loginStudent;
    private PreparedStatement verifyStudent;
    private PreparedStatement selectAll;
    private PreparedStatement selectTimeFrame;
    private PreparedStatement countDistinct;
    private PreparedStatement countTotal;


    public SQLiteDB() // initialize database
    {
        try
        {
            Class.forName("org.sqlite.JDBC"); // no idea what this line does, but the example had it
            connection = DriverManager.getConnection("jdbc:sqlite:students.db"); // sets up a "connection" to the database
            System.out.println("Opened database successfully");

            statement = connection.createStatement();

            // needed to enable foreign key constraints (why the fuck is this not on by default?)
            statement.execute(" PRAGMA foreign_keys = ON ; ");

            // create tables if not exist
            statement.execute(CREATE_STUDENT_TABLE_STATEMENT);
            statement.execute(CREATE_TIMELOG_TABLE_STATEMENT);

            // prepare prepared statements
            insertNewStudent = connection.prepareStatement(INSERT_NEW_STUDENT_STATEMENT);
            loginStudent = connection.prepareStatement(LOGIN_STUDENT_STATEMENT);
            verifyStudent = connection.prepareStatement(VERIFY_STUDENT_STATEMENT);
            selectAll = connection.prepareStatement(SELECT_ALL_STATEMENT);
            selectTimeFrame = connection.prepareStatement(SELECT_TIME_FRAME_STATEMENT);
            countDistinct = connection.prepareStatement(COUNT_DISTINCT_STATEMENT);
            countTotal = connection.prepareStatement(COUNT_TOTAL_STATEMENT);


            // TODO TEST

            // SAMPLE PREPARED STATEMENT QUERY
            //PreparedStatement tempPS = connection.prepareStatement("SELECT datetime('now');");
            //ResultSet tempRS = tempPS.executeQuery();

            //            while (tempRS.next())
            //            {
            //                System.out.println(tempRS.getString(1));
            //            }
            // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

        } catch (Exception e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    // add new student to the DB
    public void addNewStudent(int sid, String fName, String lName, String email)
    {
        try
        {
            insertNewStudent.setInt(1, sid);
            insertNewStudent.setString(2, fName);
            insertNewStudent.setString(3, lName);
            insertNewStudent.setString(4, email);
            insertNewStudent.executeUpdate();

        } catch (SQLException e)// if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("successfully added student with SID: " + sid + " Fname: " + fName + " Lname: " + lName + " Email: " + email);
    }

    // add a new timestamp in the login_time table for the given student's SID
    public void signInStudent(int sid)
    {
        try
        {
            loginStudent.setInt(1, sid);
            loginStudent.executeUpdate();

        } catch (SQLException e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("successfully signed-in student with SID: " + sid);

    }

    // checks to see if the given SID exists in the student table in the DB
    public boolean isKnownSID(int sid)
    {
        try
        {
            verifyStudent.setInt(1, sid);
            ResultSet RS = verifyStudent.executeQuery();


            RS.next();

            System.out.println("RS count: " + RS.getInt(1));

            if (RS.getInt(1) == 1)
                return true;
            else if (RS.getInt(1) == 0)
                return false;
            else
            {
                System.out.println("SQLiteDB.isKnownSID found more than 1 matching record, WTF?");
                System.exit(0);
            }

        } catch (SQLException e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        System.out.println("SQLiteDB.isKnownSID has gone HORRIBLY wrong");
        return false;
    }

    // Selects all info from student joined with login_time
    public ResultSet retrieveAll()
    {
        try
        {
            ResultSet RS = selectAll.executeQuery();


            //printResultSet(RS); // TODO remove when done testing

            return RS;

        } catch (SQLException e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    // retrieve all info from database with a time frame
    public ResultSet retrieveAll(String earlyTime, String laterTime)
    {
        try
        {
            selectTimeFrame.setString(1, earlyTime);
            selectTimeFrame.setString(2, laterTime);
            ResultSet RS = selectTimeFrame.executeQuery();

            //            ResultSet tempRS = RS;
            //            printResultSet(tempRS); // TODO remove when done testing

            return RS;

        } catch (SQLException e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    // console print all info from selectAll statements, mostly for debug
    public static String printResultSet(ResultSet RS)
    {
        StringBuilder sb = new StringBuilder();


        try
        {

            while (RS.next())
            {
                // String temp =
                // "SID:   " + RS.getInt("SID") +
                // "\nFname: " + RS.getString("FNAME") +
                // "\nLname: " + RS.getString("LNAME")+
                // "\nEmail: " + RS.getString("EMAIL")+
                // "\nDatetime:  " + RS.getString(5)+
                // "\n––––––––––––––––––––––––––––––––––––––––––––––––––––––––\n";

                sb.append("SID:      " + RS.getInt("SID"));
                sb.append("\nFname:    " + RS.getString("FNAME"));
                sb.append("\nLname:    " + RS.getString("LNAME"));
                sb.append("\nEmail:    " + RS.getString("EMAIL"));
                sb.append("\nDatetime: " + RS.getString("timestamp"));
                sb.append("\n––––––––––––––––––––––––––––––––––––––––––––––––––––––––\n");


                // System.out.println("SID:   " + RS.getInt("SID"));
                // System.out.println("Fname: " + RS.getString("FNAME"));
                // System.out.println("Lname: " + RS.getString("LNAME"));
                // System.out.println("Email: " + RS.getString("EMAIL"));
                // System.out.println("Datetime:  " + RS.getString(5)); // datetime
                //
                // System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
            }

        } catch (SQLException e) // if exception, crash program
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return sb.toString();
    }

    public int countDistinctStudents()
    {
        ResultSet RS = queryCountDistinct();

        try
        {
            RS.next();
            return RS.getInt("amount");

        } catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return -1;
    }

    public int countTotalLogins()
    {
        ResultSet RS = queryCountTotal();

        try
        {
            RS.next();
            return RS.getInt("amount");

        } catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return -1;
    }

    private ResultSet queryCountDistinct()
    {
        try
        {

            return countDistinct.executeQuery();

        } catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    private ResultSet queryCountTotal()
    {
        try
        {
            return countTotal.executeQuery();

        } catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }


}
