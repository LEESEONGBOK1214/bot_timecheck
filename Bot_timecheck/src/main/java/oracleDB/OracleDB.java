package oracleDB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDB {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
	public OracleDB() {
		conn = null;
		pstm = null;
		rs = null;
	}

	public void insert(String quary) {
		try {
			System.out.println("start insert");
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			// insert into t_record
			// values('rec_id'(varchar2), 'rec_date'(varchar2), rec_time(int));
			// rs = pstm.executeQuery();
			conn.commit();
			System.out.println("end of insert");

		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();

		} finally {
			// DB ������ �����Ѵ�.
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		}
	}// end of insert_user

	public void test() {
		// Connection conn = null; // DB����� ����(����)�� ���� ��ü
		// PreparedStatement pstm = null; // SQL ���� ��Ÿ���� ��ü
		// ResultSet rs = null; // �������� �����Ϳ� ���� ��ȯ���� ���� ��ü

		try {
			// SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)���
			// �� ����� ���� ResulSet ��ü�� �غ��� �� �����Ų��.
			String quary = "SELECT * FROM t_record";

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			rs = pstm.executeQuery();

			/*
			
			*/

			System.out.println("id\tdate\ttime");
			System.out.println("============================================");

			while (rs.next()) {
				// System.out.println("a");
				String rec_id = rs.getString(1);
				String rec_date = rs.getString(2);
				int rec_time = rs.getInt(3);

				String result = rec_id + "\t" + rec_date + "\t" + rec_time;
				System.out.println(result);
			}

		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();

		} finally {
			// DB ������ �����Ѵ�.
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		}
	}
}
/*
 * try{
 * 
 * } catch (SQLException sqle) { System.out.println("SELECT������ ���� �߻�");
 * sqle.printStackTrace();
 * 
 * } finally { // DB ������ �����Ѵ�. try { if (rs != null) { rs.close(); } if (pstm !=
 * null) { pstm.close(); } if (conn != null) { conn.close(); } } catch
 * (Exception e) { throw new RuntimeException(e.getMessage()); }
 * 
 * }
 */
/*
 * EMP ���̺��� ������ Ÿ��
 * 
 * EMPNO NOT NULL NUMBER(4) -- int ENAME VARCHAR2(10) -- String JOB VARCHAR2(9)
 * -- String MGR NUMBER(4) -- int HIREDATE DATE -- Date SAL NUMBER(7,2) --
 * float/double COMM NUMBER(7,2) -- float/double DEPTNO NUMBER(2) -- int
 */

// int empno = rs.getInt(1); //int empno = rs.getInt("empno"); ���� ��� �÷�  �̸��� ��� �ȴ�. 
// String ename = rs.getString(2); 
// String job = rs.getString(3); 
// int mgr = rs.getInt(4); 
// java.sql.Date hiredate = rs.getDate(5); // Date Ÿ�� ó�� 
// int sal = rs.getInt(6); 
// int comm = rs.getInt(7); 
// int deptno = rs.getInt(8); 
// String result =
// empno+" "+ename+" "+job+" "+mgr+" "+hiredate+" "+sal+" "+comm+" "+deptno;
// System.out.println(result);
//
// ��ó:https:// all-record.tistory.com/72 [������ ��� ���]