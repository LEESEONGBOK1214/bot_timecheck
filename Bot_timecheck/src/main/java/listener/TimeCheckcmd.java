package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import oracleDB.OracleDB;

public class TimeCheckcmd {
	OracleDB DB;

	// int 유저수 = 0;
	TimeCheckcmd(ArrayList<user> user_arr) {
		DB = new OracleDB(user_arr);
		DB.select_user(user_arr);
	}

	void test() {

		// DB.test();
	}
	void echo(MessageReceivedEvent e, MessageChannel ch,String cmd) {
		if (cmd.isEmpty()) {
			sayMsg(ch, "echo는 메아리할 말을 입력해야 합니다.");
		} else {
			sayMsg(ch, cmd);
		}
	}
	
	void holy(MessageReceivedEvent e, MessageChannel ch)
	{
		if (e.getAuthor().getId().equals("715527843827810355")) {
			sayMsg(ch, "마마");
		} else {
			sayMsg(ch, "몰리");
		}
	}
	
	void cmdList(MessageReceivedEvent e, MessageChannel ch) {
		String 명령어목록[] = { "시작", "끝", "주간시간보기", "일일시간보기", "출석", "총시간", "일시정지 (다시하면 해제)", "ping", "홀리", "산산" };
		String 출력 = "";
		출력 += "===명령어 목록===\n";
		for (int i = 0; i < 명령어목록.length; i++) {
			출력 += (명령어목록[i] + "\n");
		}
		sayMsg(ch, 출력);
	}

	void total_time(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 유저ID = e.getAuthor().getId();
		
		for (int i = 0; i < user_arr.size(); i++) {
			// System.out.println("현재 id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(유저ID))
      		  {// 값 찾아서 해당 값 총시간 출력.
					user_arr.get(i).총시간(e, user_arr);
					return;
      		  }
      	  }
	}
	
	void start(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 유저ID = e.getAuthor().getId();
		String 유저명 = e.getAuthor().getName();
		
		int 유저번호 = -1; // ArrayList의 현재 유저번호 찾기위함.
		
		for (int i = 0; i < DB.getusers(); i++) { // 중복값 확인

			if (user_arr.get(i).id.equals(유저ID)) { // ID가 있으면 true 없으면 false
				System.out.println("===============id검색 성공.");
				// 현재 유저와 같은 번호를 찾아서, 진행중이면 메세지 출력 후 종료.

				user_arr.get(i).now_ch = e.getChannel().getId();
				if (user_arr.get(i).진행중) {
					e.getChannel().sendMessage("```ini\r\n[" + user_arr.get(i).getname() + "-> 중복 시작했습니다.]```").queue();
					// System.out.println("중복 시작 : " + user_arr.get(i).name);
					return;
				} else {
					// 진행중은 아닌데 해당 유저가 있을경우.
					// 아이디를 안만들어야함.
					유저번호 = i;
					break;
				}
			}
		}
		// 만들어야함!!
		if (유저번호 < 0) { // -1일때는 유저가 없을 때임!!
			// System.out.println("유저가 없음!!");
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			String query = "insert into t_user values('" + 유저ID + "', '" + 유저명 + "')";
			DB.insert(query);
			유저번호 = user_arr.size() - 1;
		}
		user_arr.get(유저번호).시작();
		String 시작시간 = ToTime(user_arr.get(유저번호).get시작시간());
		sayMsg(e.getChannel(), "```ini\r\n[" + user_arr.get(유저번호).getname() + "]의 시작 시간\n[" + 시작시간 + "]```");
		
		// System.out.println("start 마지막.");
	}

	

	// end의 시작

	void end(ArrayList<user> user_arr, MessageReceivedEvent e, Message msg) {
		for (int i = 0; i < user_arr.size(); i++) { // 시작에 아이디가 있다면, 끝 실행.

			if (user_arr.get(i).중복확인(msg.getAuthor().getId())) {
				if (user_arr.get(i).진행중 == false) {
					return;
				}
				sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), user_arr.get(i).끝());
				{ // DB에 유저의 시작시간 넣기.
					// OracleDB DB = new OracleDB(user_arr);

					SimpleDateFormat 시간출력포맷 = new SimpleDateFormat("yyMMddHH");
					String start_date = 시간출력포맷.format(user_arr.get(i).get시작시간().getTime());
					String query = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
							+ user_arr.get(i).diff / 1000
							+ ")";
					DB.insert(query);
				}
				return;
			}
		}
	}

	public String end(ArrayList<user> user_arr, String id) {
		String retn = "";
		for (int i = 0; i < user_arr.size(); i++) {
			//System.out.println(i + " " + user_arr.get(i).name + user_arr.get(i).진행중);
			if (user_arr.get(i).id.equals(id) && user_arr.get(i).진행중) {
				retn = user_arr.get(i).끝();
				SimpleDateFormat 시간출력포맷 = new SimpleDateFormat("yyMMddHH");
				String start_date = 시간출력포맷.format(user_arr.get(i).get시작시간().getTime());
				String query = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
						+ user_arr.get(i).diff / 1000 + ")";
				DB.insert(query);
				break;
			}
		}
		return retn;
	}
	// end of end
	

	void queue(ArrayList<user> user_arr, MessageReceivedEvent e) {

		sayMsg(e.getChannel(), ">>>  진행중 목록");
		int c = 0;
	 	for(int i=0 ;i<user_arr.size();i++)
	 	{
	 		if(user_arr.get(i).진행중) {
	 			//System.out.println(user_arr.get(i).name + " " + user_arr.get(i).id);
	 			sayMsg(e.getChannel(), (user_arr.get(i).이름 + " " + user_arr.get(i).id));
	 			c++;
	 		}
	 	}if(c==0) {
	 		sayMsg(e.getChannel(), ">없음");
	 	}
	 	//sayMsg(e.getChannel(), "==============진행중인사람==============");
	}
	
	void pause(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 유저ID = e.getAuthor().getId();
		String 유저명 = e.getAuthor().getName();

		// System.out.println("일시정지 메세지 유저 ID" + 유저ID);
		for (int i = 0; i < user_arr.size(); i++) {
			if (유저ID.equals(user_arr.get(i).id)) {
				// System.out.println("일시정지 메세지 일치 유저 ID" + user_arr.get(i).id);
				user_arr.get(i).일시정지(e);
				break;
			}
		}

	}
	
	void view_week(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 출력문[] = DB.week_time(user_arr.size());
		출력문[0] += "\n";
		sayMsg(e.getChannel(), 출력문[0]);
		// sayMsg(e.getChannel(), "\n");
		sayMsg(e.getChannel(), 출력문[1]);
	}
	
	public void view_today(ArrayList<user> user_arr, MessageReceivedEvent e) {
		// TODO Auto-generated method stub
		String 출력문 = DB.today_time(user_arr);
		sayMsg(e.getChannel(), 출력문);

	}
	
	public void Attendance(MessageReceivedEvent e) {
		// TODO Auto-generated method stub

		String query = "select usr_name, sum(rec_time) from t_user, t_record where usr_id = rec_id\r\n"
				+ "and rec_date between to_char(sysdate, 'RRMMDD') || '06' and\r\n"
				+ "to_char(sysdate+1, 'RRMMDD') || '06' group by usr_name";

		sayMsg(e.getChannel(), DB.getAttendance(query));
	}
	

	
	
	public boolean 강제종료하기(ArrayList<user> user_arr, MessageReceivedEvent e, String msg) {
		for (int i = 0; i < user_arr.size(); i++) {
			// 느낌표 떼고 그 뒤에거만 왔을것.
			// ex !이성복 -> 이성복
			//    이성복          ==     이성복 비교
			if (msg.equals(user_arr.get(i).이름)) {
				if(user_arr.get(i).진행중==false) {
					sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0),
							user_arr.get(i).이름 + " 진행중이 아님.");
					return false;
				}
				sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0),
						user_arr.get(i).이름 + "을 강제종료 합니다.");
				sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0),
						user_arr.get(i).끝());
				
				
				return false;
			}
		}
		
		return true;
	}


	public boolean 고수인가(String id) {
		String[] 고수들 = {
				"221937902093991936", //중원이형
				"562265706079584256", //창현이형
				"569159798919004171", //동진이형
				"380676589349896193",  //원용이형
				"262951571053084673" // 테스트용 이성복
		};
		
		for(int i=0;i<고수들.length;i++) {
			if(id.equals(고수들[i])) {
				return true;
			}
		}
		
		return false;
	}

	
	
	private void sayMsg(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue();
	}

	private String ToTime(Date date) {
		SimpleDateFormat 시간포맷 = new SimpleDateFormat("MM월/dd일/ HH시 :mm분 :ss초");

		// System.out.println(시간포맷.format(date));

		return 시간포맷.format(date);
	}

}
