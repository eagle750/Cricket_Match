package Cricket;

import java.sql.*;
public class Connect_db {

    static Connection con = null;


    public static Connection getConnection() {
        if (con != null) return con;
        return getConnection("jdbc:mysql://localhost:3306/sys", System.getenv("user"), System.getenv("password"));
    }

    private static Connection getConnection(String db_name, String user_name, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(db_name, user_name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }


    public static void destroy() {

        if (con != null) {

            try {
                con.close();
            } catch (Exception e) {
            }

        }
    }
}
