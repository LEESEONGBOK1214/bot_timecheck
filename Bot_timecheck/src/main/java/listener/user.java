package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.javacord.api.DiscordApi;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import oracleDB.OracleDB;

public class user{
	public String id;
	public String 이름;
	String now_ch;
	Date 시작시간 = null;
	Date 끝시간 = null;
	long 멈춘시간 = 0;
	long 총시간 = 0;
	boolean 진행중 = false;
	boolean 정지 = false;
	// Font font = new Font();
	// Calendar 시작시간 = Calendar.getInstance();
	// Calendar 끝시간 = Calendar.getInstance();
	SimpleDateFormat 시간출력포맷 = new SimpleDateFormat("MM/dd/ HH: mm: ss");
	// 출력포맷 만들기 = new SimpleDateFormat("MM / dd / HH:mm");

	String 시간출력; // 시작 or 끝시간.format(cal.getTime());
	// String 끝 = 시작시간.format(cal.getTime());

	DiscordApi DA;

	public String getname() {
		return this.이름;
	}
	
	public user(String id, String name) { // 시작 시 호출.
		// System.out.println("받은 아이디 : " + id);
		// ServerLeaveListener SLL = new ServerLeaveListener();

		// ServerMemberLeaveEvent SMLE = new ServerMemberLeaveEvent(DA.getServers()
		// server, user);
		if (id.equals(null)) {
			// System.out.println("널값 들어옴 종료함");
			return;
		} else {
			// System.out.println("널값아님 실행함");
			this.id = id;
			this.이름 = name;
			//System.out.println(this.name);
		}

		// 시작();

	}

	void 시작() {
		// System.out.println("시작!");
		this.시작시간 = new Date();
		// System.out.println(시작시간);
		this.진행중 = true;

	}

	// @SuppressWarnings("deprecation")
	long diff = 0;

	String 끝() {
		// System.out.println("끝 - 입장");
		String retn;
		끝시간 = new Date();

		if (정지) {
			retn = ">일시정지를 끝내주세요";
		} else {
//			System.out.println(끝시간.getTime());
//			System.out.println(시작시간.getTime());
//			System.out.println((멈춘시간 * 1000));
			diff = 끝시간.getTime() - 시작시간.getTime() - (멈춘시간 * 1000); // 멈춘시간은 초단위로 설정되어있다.

			long hour = diff / (60 * 60 * 1000);
			long min = diff / (60 * 1000) % 60;
			long sec = diff / 1000 % 60;

			String 시작시간_문자열 = 시간출력포맷.format(시작시간.getTime());
			시간출력포맷 = new SimpleDateFormat("dd일/ HH시: mm분: ss초");
			String 끝시간_문자열 = 시간출력포맷.format(끝시간.getTime());

			if (hour >= 1) {
				// 1시간 이상일때
				retn = ("```yaml\r\n" + this.이름 + "님은\n" + 시작시간_문자열 + "부터\n" + 끝시간_문자열 + "까지 하여\n" + "총" + hour + "시간 "
						+ min + "분 " + sec + "초 공부했습니다.\n" + "```");
				/*
				 * e.getChannel().sendMessage("```css\r\n#" + message.getAuthor().getName() +
				 * "님은\n#" + 시작시간_문자열 + "부터\n" + 끝시간_문자열 + "까지 하여\n#" + "총" + hour + "시간 " +min
				 * + "분 " + sec + "초 공부했습니다.\n" + "```");
				 */
				// e.getChannel().sendMessage(message.getAuthor().getName() + "님은"+ 시작시간_문자열 +
				// "부터\n"+ 끝시간_문자열 + "까지 하여\n총" + hour + "시간 " +min + "분 " + sec + "초 공부했습니다.");
			} else {
				// 1시간 미만일때,
				// 시간 출력 x
				retn = ("```yaml\r\n" + this.이름 + "님은\n" + 시작시간_문자열 + "부터\n" + 끝시간_문자열 + "까지 하여\n" + "총" + min + "분 " + sec
						+ "초 공부했습니다.\n" + "```");
				;
				// e.getChannel().sendMessage(message.getAuthor().getName() + "님은"+ 시작시간_문자열 +
				// "부터\n"+ 끝시간_문자열 + "까지 하여\n총" +min + "분 " + sec + "초 공부했습니다.");
			}	
			멈춘시간 = 0;
			진행중 = false;
		}
		
		
		// System.out.println("끝 - retn : \n\t" + retn);
		// System.out.println("끝 - retn 끝남!");
		return retn;
		// 총시간 = 총시간 + diff; // 합 저장. 포맷 가독성 위해서 그냥 저장.
	}

	void 총시간(MessageReceivedEvent e, ArrayList<user> user_arr) {
		// System.out.println("총시간 진입..!");
		OracleDB DB = new OracleDB(user_arr);
		// System.out.println("총시간 들어온 유저의 ID : " + id);
		총시간 = DB.total_time(id); // 총시간은 초단위로 저장되고 반환한다.
		// System.out.println("유저의 총시간 : " + 총시간);

		long hour = 총시간 / (60 * 60);
		long min = (총시간 / 60) % 60;
		long sec = 총시간 % 60;
		// System.out.println();
		// e.getChannel().sendMessage("시스템 시작 이후로, " + message.getAuthor().getName() +
		// "님은 총" + hour + "시간 " +min + "분 " + sec + "초 공부했습니다.");
		e.getChannel().sendMessage("```diff\r\n" + "-시스템 시작 이후로,\n-" + this.이름 + "님은 총\n-" + hour + "시간 " + min + "분 "
				+ sec + "초 공부했습니다.\n" + "```").queue();
	}

	Date 정지시작시간 = null;
	Date 정지끝낸시간 = null;

	void 일시정지(MessageReceivedEvent e) {

		// print(e.getChannel(), "test!");
		if (진행중 == false) {
			e.getChannel().sendMessage("시작 미입력.").queue();
			return;
		}
		if (!정지) { // 0이면 해제 1이면 정지
			// 정지
			//System.out.println("정지 함");
			정지시작시간 = new Date();
			시간출력포맷 = new SimpleDateFormat("dd일/ HH시: mm분: ss초");
			String 정지시작문자열 = 시간출력포맷.format(정지시작시간.getTime());
			e.getChannel().sendMessage("```tex\n$" + this.이름 + "정지 시작시간 : " + 정지시작문자열 + "\n```").queue();

		} else {
			// 해제
			//System.out.println("해제 함");

			정지끝낸시간 = new Date();

			멈춘시간 = 멈춘시간 + (정지끝낸시간.getTime() - 정지시작시간.getTime()) / 1000;

			long hour = 멈춘시간 / (60 * 60);
			long min = (멈춘시간 / 60) % 60;
			long sec = 멈춘시간 % 60;
			//System.out.println("멈춘 시간 : " + 멈춘시간);
			e.getChannel().sendMessage("```tex\n$" + this.이름 + "멈춘시간 : " + hour + "시간" + min + "분" + sec + "초" + "\n```")
					.queue();

		}

		정지 = !정지;
	}

	boolean 중복확인(String id) {
		if (id.equals(this.id)) {
			// 중복!!
			return true;
		}
		return false;
	}

	/*
	 * 사용 포맷 for(int i=0;i<user_arr.size();i++) { // 시작에 아이디가 있다면, 끝 실행.
	 * if(user_arr.get(i).중복확인(message.getAuthor().getAvatarId())) {
	 * 
	 * } }
	 */

	Date get시작시간() {
		return this.시작시간;
	}

	
}
