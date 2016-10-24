package utils;

import sql.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;


/**
 * Created by Tyler on 10/19/2016.
 *
 * Needs UID from main menu login.
 */

public class Alerts {

    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con = ConnectionClass.conn;

    static public Integer getInteger(ResultSet rs, String strColName) throws Exception {
        int nValue = rs.getInt(strColName);
        return rs.wasNull() ? null : nValue;
    }

    public static void view(int userID) throws Exception{
        String response = null;
        Scanner s = new Scanner(System.in);
        ArrayList<String> alerts = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();

        //return all alerts for give UID
        try {
            ps = con.prepareStatement(SqlQueries.SQL_VIEW_ALERT_FOR_UID);
            ps.setInt(1, Userid.USER_ID_STATIC);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int currAID = 0;
        while(rs.next())
        {
            currAID = getInteger(rs, "AID");
            System.out.println("AID = " + currAID);

            Date tempDate = rs.getDate("ALERT_DATE");
            System.out.println("Alert Date = " + tempDate.toString());

            String tempType = rs.getString("ALERT_TYPE");
            System.out.println("Alert Type = " + tempType);

            int currUID = getInteger(rs, "U_ID");
            System.out.println("UID = " + currUID);

            String tempStatus = rs.getString("STATUS");
            System.out.println("Alert Status = " + tempType);

            int tempPid = getInteger(rs, "PID");
            System.out.println("PID = " + tempPid);

            String tempDesc = rs.getString("DESCRIPTION");
            System.out.println("Description = " + tempDesc);

            System.out.println("\n\nWould you like to clear this alert? (Y/N)");
            response = s.nextLine().toUpperCase();

            if (response.equals("Y"))
            {
                ps = con.prepareStatement(SqlQueries.SQL_CLEAR_ALERT);
                ps.setInt(1, currAID);
                rs = ps.executeQuery();
                System.out.println("Update cleared.");
            }
            System.out.print("\n\n\n");

        }

    }

}
