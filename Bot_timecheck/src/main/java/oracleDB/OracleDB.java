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
		// System.out.println("USERS 반환 " + users);
		return users;
		// conn.
	}

	public OracleDB(ArrayList<user> user_arr) {
		conn = null;
		pstm = null;
		rs = null;
		
		// select_user(user_arr);
	}


	public int select_user(ArrayList<user> user_arr) {
		String query = "select * from t_user";
		try {
			// System.out.println("count users");
			
			conn = DBConnection.getConnection();
			System.out.println("쿼리 : " + query);
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				users++;
				user_arr.add(new user(rs.getString(1), rs.getString(2)));
				// users = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();

		}
		System.out.println("유저 수 : " + users);
		// return은 user 수 == count(*)
		return users;
	}

	public boolean ck_user(String id) {
		String query = "select * from t_user where usr_id = " + id;
		
		try {
			// System.out.println("ck user");

			conn = DBConnection.getConnection();
			System.out.println("쿼리 : " + query);
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			// insert into t_record
			// values('rec_id'(varchar2), 'rec_date'(varchar2), rec_time(int));


			if (rs.next()) {
				// 해당 id가 없을 경우!
				// System.out.println("해당 id가 있음!");
				return true;
			} else {
				// 있을 경우
				// System.out.println("해당 id가 없음. 새로 만들어야 함.");
				return false;
			}

		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();

		}
		return false;
	}
	public void insert(String query) {
		System.out.println("DB - insert - 입장\n Query : " + query);
		try {
			// System.out.println("start insert");
			conn = DBConnection.getConnection();
			// System.out.println("쿼리 : " + query);
			pstm = conn.prepareStatement(query);
			// insert into t_record
			// values('rec_id'(varchar2), 'rec_date'(varchar2), rec_time(int));
			rs = pstm.executeQuery();

			if (rs.next()) {
				// 성공.
				System.out.println("===============insert success ==============");
//				conn.commit();
			} else {
				conn.rollback();
			}

			// System.out.println("end of insert");

		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();

		}
	}// end of insert_user


	public long today_time(String id) {
		String query = "select nvl(sum(rec_time), 0) from t_record where rec_id = " + id;
		String tot_t = null;
		try {
			System.out.println("쿼리 : " + query);

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			while (rs.next()) {

				tot_t = rs.getString(1);
			}
			// System.out.println(id + "님의 총 시간 : " + tot_t);
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		}
		// long total = Long.parseLong(tot_t);
		// System.out.println("total : " + total);

		return Long.parseLong(tot_t);
	}

	public String[] week_time(int size) {
		String 유저명[] = new String[size];
		int 주간시간[] = new int[size];

		String query = "select u.usr_name, sum(nvl(rec_time, 0))총시간 \r\n" + "from t_record r, t_user u \r\n"
				+ "where \r\n" + "      r.rec_id = u.usr_id and\r\n"
				+ "      rec_date between to_number(to_char((next_day(sysdate-6, '일요일')),'yyMMdd')) and next_day(sysdate, '일요일')\r\n"
				+ "group by u.usr_name";

		int count = 0;
		try {
			System.out.println("쿼리 : " + query);

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			while (rs.next()) {
//				System.out.println("현재 count" + count);
				유저명[count] = rs.getString(1);
				주간시간[count++] = rs.getInt(2);
			}
			// System.out.println(id + "님의 총 시간 : " + tot_t);
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		}
		

		System.out.println("us : " + size);
		String 공부시간문자열 = "";
		String 공부학생문자열 = "====공부한 학생 목록====\n";
		if (count <= 0) {
			공부시간문자열 = "이번 주 공부한 학생이 없습니다.";
			공부학생문자열 += "없습니다.";
		} else {
			공부학생문자열 += 유저명[0];
			공부시간문자열 += 유저명[0] + "님의 주간시간 : " + 주간시간[0]/3600 + "h" + (주간시간[0]/60)%60 + "m" + 주간시간[0]%60 + "s" + "\n";
			for (int i = 1; i < count; i++) {
				long hour = 주간시간[i]/3600;
				long min = (주간시간[i]/60)%60;
				long sec = 주간시간[i]%60;
				
				공부시간문자열 += 유저명[i] + "님의 주간시간 : " + hour + "h" + min + "m" + sec + "s" + "\n";
				공부학생문자열 += ", " + 유저명[i];
				if (i % 5 == 4) {
					공부학생문자열 += "\n";
				}
			}
		}

		String[] 출력문 = new String[2];
		출력문[0] = 공부학생문자열;
		출력문[0] += "\n===================";

		출력문[1] += "\n===================";
		출력문[1] = 공부시간문자열;
		출력문[1] += "===================";

		return 출력문;
	}

	public String run_sql(String query) {
		String 출력문 = "";
		try {
			System.out.println("쿼리 : " + query);

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			while (rs.next()) {

			}
			// System.out.println(id + "님의 총 시간 : " + tot_t);
		} catch (SQLException sqle) {
			System.out.println("sql문에서 예외 발생");
			출력문 = "sql문에서 예외 발생";
			sqle.printStackTrace();
		}

		return query;
	}
	// db connection 종료.
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
 * public void test() { // Connection conn = null; // DB연결된 상태(세션)을 담은 객체 //
 * PreparedStatement pstm = null; // SQL 문을 나타내는 객체 // ResultSet rs = null; //
 * 쿼리문을 날린것에 대한 반환값을 담을 객체
 * 
 * try { // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면 // 그 결과를 담을 ResulSet 객체를 준비한 후
 * 실행시킨다. String quary = "SELECT * FROM t_record";
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
 * } catch (SQLException sqle) { System.out.println("SELECT문에서 예외 발생");
 * sqle.printStackTrace();
 * 
 * } } try{
 * 
 * } catch (SQLException sqle) { System.out.println("SELECT문에서 예외 발생");
 * sqle.printStackTrace();
 * 
 * } finally { // DB 연결을 종료한다. try { if (rs != null) { rs.close(); } if (pstm !=
 * null) { pstm.close(); } if (conn != null) { conn.close(); } } catch
 * (Exception e) { throw new RuntimeException(e.getMessage()); }
 * 
 * }
 */
/*
 * EMP 테이블의 데이터 타입
 * 
 * EMPNO NOT NULL NUMBER(4) -- int ENAME VARCHAR2(10) -- String JOB VARCHAR2(9)
 * -- String MGR NUMBER(4) -- int HIREDATE DATE -- Date SAL NUMBER(7,2) --
 * float/double COMM NUMBER(7,2) -- float/double DEPTNO NUMBER(2) -- int
 */

// int empno = rs.getInt(1); //int empno = rs.getInt("empno"); 숫자 대신 컬럼  이름을 적어도 된다. 
// String ename = rs.getString(2); 
// String job = rs.getString(3); 
// int mgr = rs.getInt(4); 
// java.sql.Date hiredate = rs.getDate(5); // Date 타입 처리 
// int sal = rs.getInt(6); 
// int comm = rs.getInt(7); 
// int deptno = rs.getInt(8); 
// String result =
// empno+" "+ename+" "+job+" "+mgr+" "+hiredate+" "+sal+" "+comm+" "+deptno;
// System.out.println(result);
//
// 출처:https:// all-record.tistory.com/72 [세상의 모든 기록]