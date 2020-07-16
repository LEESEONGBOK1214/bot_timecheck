package oracleDB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import listener.user;

public class OracleDB {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	int users = 0;

	public int getusers() {
		return users;
		// conn.
	}

	public OracleDB(ArrayList<user> user_arr) {
		conn = null;
		pstm = null;
		rs = null;
		
		select_user(user_arr);
	}

	public int select_user(ArrayList<user> user_arr) {
		String query = "select * from t_user";
		try {
			// System.out.println("count users");
			
			conn = DBConnection.getConnection();
			System.out.println("���� : " + query);
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				users++;
				user_arr.add(new user(rs.getString(1), rs.getString(2)));
				// users = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();

		}
		System.out.println("���� �� : " + users);
		// return�� user �� == count(*)
		return users;
	}

	public boolean ck_user(String id) {
		String query = "select * from t_user where usr_id = " + id;
		
		try {
			// System.out.println("ck user");

			conn = DBConnection.getConnection();
			System.out.println("���� : " + query);
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			// insert into t_record
			// values('rec_id'(varchar2), 'rec_date'(varchar2), rec_time(int));


			if (rs.next()) {
				// �ش� id�� ���� ���!
				// System.out.println("�ش� id�� ����!");
				return true;
			} else {
				// ���� ���
				// System.out.println("�ش� id�� ����. ���� ������ ��.");
				return false;
			}

		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();

		}
		return false;
	}
	public void insert(String query) {
		try {
			// System.out.println("start insert");
			conn = DBConnection.getConnection();
			System.out.println("���� : " + query);
			pstm = conn.prepareStatement(query);
			// insert into t_record
			// values('rec_id'(varchar2), 'rec_date'(varchar2), rec_time(int));
			rs = pstm.executeQuery();

			if (rs.next()) {
				// ����.
				// System.out.println("===============insert success ==============");
				conn.commit();
			} else {
				conn.rollback();
			}

			// System.out.println("end of insert");

		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();

		}
	}// end of insert_user

	

	public void time_week(String query) {
		try {
			System.out.println("���� : " + query);

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();


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

		}
	}
	
	public long total_time(String id) {
		String query = "select sum(rec_time) from t_record r where rec_id = " + id;
		String tot_t = null;
		try {
			System.out.println("���� : " + query);

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			while (rs.next()) {

				tot_t = rs.getString(1);
			}

			// System.out.println(id + "���� �� �ð� : " + tot_t);
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		}
		// long total = Long.parseLong(tot_t);
		// System.out.println("total : " + total);
		return Long.parseLong(tot_t);
	}

	// db connection ����.
	{
		try

		{
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
/*
 * public void test() { // Connection conn = null; // DB����� ����(����)�� ���� ��ü //
 * PreparedStatement pstm = null; // SQL ���� ��Ÿ���� ��ü // ResultSet rs = null; //
 * �������� �����Ϳ� ���� ��ȯ���� ���� ��ü
 * 
 * try { // SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)��� // �� ����� ���� ResulSet ��ü�� �غ��� ��
 * �����Ų��. String quary = "SELECT * FROM t_record";
 * 
 * conn = DBConnection.getConnection(); pstm = conn.prepareStatement(quary); rs
 * = pstm.executeQuery();
 * 
 * 
 * //System.out.println("id\tdate\ttime");
 * //System.out.println("============================================");
 * 
 * while (rs.next()) { // System.out.println("a"); String rec_id =
 * rs.getString(1); String rec_date = rs.getString(2); int rec_time =
 * rs.getInt(3);
 * 
 * String result = rec_id + "\t" + rec_date + "\t" + rec_time;
 * //System.out.println(result); }
 * 
 * } catch (SQLException sqle) { System.out.println("SELECT������ ���� �߻�");
 * sqle.printStackTrace();
 * 
 * } } try{
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