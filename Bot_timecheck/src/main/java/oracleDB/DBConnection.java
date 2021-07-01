package oracleDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
    public static Connection dbConn;
    
    public DBConnection() {
    	//TODO 싱글톤으로 변경 해주어야 함.
    }
    
	public static Connection getConnection() {
		Connection conn = null;
		try {
//			String user = "timeck"; // timeck <
//			String pw = "lenope1214"; // �뵒鍮꾩젒�냽 鍮꾨�踰덊샇
//			String url = "jdbc:oracle:thin:@localhost:1521:xe";
//			 String user = "c##2001376";
//		     String pw = "p2001376";
//			 String url = "jdbc:oracle:thin:@10.30.3.95:1521:orcl";
			String user = "dblab";
		     String pw = "1234";
			 String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database�뿉 �뿰寃곕릺�뿀�뒿�땲�떎.\n");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB �뱶�씪�씠踰� 濡쒕뵫 �떎�뙣 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB �젒�냽�떎�뙣 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
        }
		return conn;
	}
}