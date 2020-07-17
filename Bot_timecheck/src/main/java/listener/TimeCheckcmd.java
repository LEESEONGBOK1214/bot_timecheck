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
		String 명령어목록[] = { "ping", "echo", "시작", "끝", "총시간", "홀리" };
		String 출력 = "";
		출력 += "===명령어 목록===\n";
		for (int i = 0; i < 명령어목록.length; i++) {
			출력 += (명령어목록[i] + "\n");
		}
		sayMsg(ch, 출력);
	}

	void total_time(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 유저ID = e.getAuthor().getId();
		String 유저명 = e.getAuthor().getName();
		
		for (int i = 0; i < user_arr.size(); i++) {
			// System.out.println("현재 id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(유저ID))
      		  {// 값 찾아서 해당 값 총시간 출력.
      			  //System.out.println("못찾음?");
      			  //message.reply("i");
					user_arr.get(i).총시간(e, user_arr);
					return;
      		  }
      	  }
	}
	
	void start(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String 유저ID = e.getAuthor().getId();
		String 유저명 = e.getAuthor().getName();

		System.out.println("start 들어옴.");
		// System.out.println("DB.getusers() : " + DB.getusers());
		int 유저번호 = -1; // ArrayList의 현재 유저번호 찾기위함.
		for (int i = 0; i < DB.getusers(); i++) { // 중복값 확인
			// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
			System.out.println(i + "의 유저 id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(유저ID)) { // ID가 있으면 true 없으면 false
				// 현재 유저와 같은 번호를 찾아서, 진행중이면 메세지 출력 후 종료.
				System.out.println("유저ID : " + 유저ID);
				System.out.println("유저명 : " + 유저명);
				if (user_arr.get(i).진행중) {
					e.getChannel().sendMessage("```ini\r\n[" + user_arr.get(i).name + "-> 중복 시작했습니다.]```").queue();
					System.out.println("중복 시작 : " + user_arr.get(i).name);
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
			System.out.println("유저가 없음!!");
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			String quary = "insert into t_user values('" + 유저ID + "', '" + 유저명 + "')";
			System.out.println(quary);
			DB.insert(quary);
			유저번호 = user_arr.size() - 1;

		}
		
		user_arr.get(유저번호).시작();

		// System.out.println("해당 유저의 시작시간 : " + user_arr.get(유저번호).get시작시간());
		String 시작시간 = ToTime(user_arr.get(유저번호).get시작시간());


		sayMsg(e.getChannel(), "```ini\r\n[" + e.getAuthor().getName() + "]의 시작 시간\n[" + 시작시간 + "]```");
		
		System.out.println("start 마지막.");
	}

	
	// end의 시작
	void end(ArrayList<user> user_arr, MessageReceivedEvent e, Message msg) {
		for (int i = 0; i < user_arr.size(); i++) { // 시작에 아이디가 있다면, 끝 실행.

			if (user_arr.get(i).중복확인(msg.getAuthor().getId())) {
				if (user_arr.get(i).진행중 == false) {
					return;
				}

				user_arr.get(i).끝(e);
				{ // DB에 유저의 시작시간 넣기.
					OracleDB DB = new OracleDB(user_arr);

					SimpleDateFormat 시간출력포맷 = new SimpleDateFormat("yyMMdd");
					String start_date = 시간출력포맷.format(user_arr.get(i).get시작시간().getTime());
					System.out.println("========================start_date : " + start_date + "=============");
					String quary = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
							+ user_arr.get(i).diff / 1000
							+ ")";
					DB.insert(quary);
				}
				// user_arr.remove(i);
				return;
			}
		}
	}
	// end of end
	
	void queue(ArrayList<user> user_arr) {
		System.out.println("===========큐==========");
     	  for(int i=0;i<user_arr.size();i++)
     	  {
     		  System.out.println(user_arr.get(i).name + " " + user_arr.get(i).id);
     	  }
     	  System.out.println("===========큐==========");
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
		String query = 
				"select u.usr_name, sum(nvl(rec_time, 0))총시간 \r\n" + 
				"from t_record r, t_user u \r\n" + 
				"where \r\n" + 
				"      r.rec_id = u.usr_id and\r\n" + 
				"      rec_date between to_number(to_char((next_day(sysdate, '일요일')),'yyMMdd'))-6 and next_day(sysdate, '일요일')\r\n" + 
						"group by u.usr_name";
		sayMsg(e.getChannel(), DB.week_time(query, user_arr.size()));
	}
	
	
	
	
	
	
	
	
	
	
	private void del_Msg(Message msg) {
		msg.delete().queue();
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
