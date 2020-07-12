package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;


import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;




public class TimeCheckListener extends ListenerAdapter {
	String 명령어목록[] = { "ping", "echo", "시작", "끝", "총시간", "홀리" };
	final ArrayList<user> user_arr = new ArrayList<user>();

	@Override
	public void onMessageReceived(MessageReceivedEvent e)
    {
		Message msg = e.getMessage(); 
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();

		//System.out.println(cmd);
		if (cmd.startsWith("$")) {

			cmd = cmd.substring(1);
			//System.out.println(cmd);

			switch (cmd) {
			case "echo":
				if (cmd.isEmpty()) {
					sayMsg(e.getChannel(), "echo는 메아리할 말을 입력해야 합니다.");
				} else {
					sayMsg(ch, cmd);
				}
				return;
			// echo 명령어 끝=========================

			case "ping":
				e.getChannel().sendMessage("pong!").queue();
				return;
			// ping 명령어 끝=========================

			case "복령어":
				String 출력 = "";
				출력 += "===명령어 목록===\n";
				for (int i = 0; i < 명령어목록.length; i++) {
					출력 += (명령어목록[i] + "\n");
				}
				sayMsg(ch, 출력);
				// 복령어 명령어 끝=========================
				return;
			case "시작": // 시작
				// 메세지제거(message);
				del_Msg(msg);
				
				// message.delete();
				//System.out.println(e.getAuthor().getId());
				
				int 번호 = -1;
				for (int i = 0; i < user_arr.size(); i++) { // 중복값 확인
					// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
					if (user_arr.get(i).중복확인(e.getAuthor().getId())) {// System.out.println("중복
																		// 시작!!!");
						if (user_arr.get(i).진행중) {
							e.getChannel().sendMessage(user_arr.get(i).name + "-> 중복 시작했습니다.");
							return;
						} else {
							// 진행중은 아닌데 해당 유저가 있을경우.
							// 아이디를 안만들어야함.
							번호 = i;
							return;
						}
					}

				}
				// System.out.println(번호);
				if (번호 >= 0) {
					user_arr.get(번호).시작();
				} else {
					user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
					번호 = user_arr.size() - 1;
				}

				// System.out.println("==========" + 번호 + "==========");

				// System.out.println("해당 유저의 시작시간 : " + user_arr.get(번호).get시작시간());
				String 시작시간 = ToTime(user_arr.get(번호).get시작시간());
				e.getChannel().sendMessage("```ini\r\n[" + e.getAuthor().getName() + "]의 시작 시간\n[" + 시작시간 + "]```");
				// 시작 명령어 끝=========================
				return;
			}
		}

    }
	private void del_Msg(Message msg)
	{
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

class user {
	String id;
	String name;
	private Date 시작시간 = null;
	private Date 끝시간 = null;
	long 멈춘시간 = 0;
	long 총시간 = 0;
	boolean 진행중 = false;
	// Font font = new Font();
	// Calendar 시작시간 = Calendar.getInstance();
	// Calendar 끝시간 = Calendar.getInstance();
	SimpleDateFormat 시간출력포맷 = new SimpleDateFormat("MM/dd/ HH: mm: ss");
	// 출력포맷 만들기 = new SimpleDateFormat("MM / dd / HH:mm");

	String 시간출력; // 시작 or 끝시간.format(cal.getTime());
	// String 끝 = 시작시간.format(cal.getTime());

	user(String id, String name) { // 시작 시 호출.
		// System.out.println("받은 아이디 : " + id);
		if (id.equals(null)) {
			// System.out.println("널값 들어옴 종료함");
			return;
		} else {
			// System.out.println("널값아님 실행함");
			this.id = id;
			this.name = name;
		}

		시작();

	}

	void 시작() {
		// System.out.println("시작!");
		this.시작시간 = new Date();
		// System.out.println(시작시간);
		this.진행중 = true;
	}

	// @SuppressWarnings("deprecation")
	void 끝(MessageReceivedEvent e) {

		끝시간 = new Date();

		long diff = 끝시간.getTime() - 시작시간.getTime() + 멈춘시간;

		long hour = diff / (60 * 60 * 1000);
		long min = diff / (60 * 1000) % 60;
		long sec = diff / 1000 % 60;

		String 시작시간_문자열 = 시간출력포맷.format(시작시간.getTime());
		시간출력포맷 = new SimpleDateFormat("dd일/ HH시: mm분: ss초");
		String 끝시간_문자열 = 시간출력포맷.format(끝시간.getTime());
		// System.out.println(message.getAuthor().getName() + "님은 "+ 시작시간_문자열 + "부터 "+
		// 끝시간_문자열 + "까지 하여 총" +min + "분 " + sec + "초 공부했습니다.");
		if (hour >= 1) {
			// 1시간 이상일때
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "님은\n" + 시작시간_문자열 + "부터\n" + 끝시간_문자열
					+ "까지 하여\n" + "총" + hour + "시간 " + min + "분 " + sec + "초 공부했습니다.\n" + "```");
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
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "님은\n" + 시작시간_문자열 + "부터\n" + 끝시간_문자열
					+ "까지 하여\n" + "총" + min + "분 " + sec + "초 공부했습니다.\n" + "```");
			// e.getChannel().sendMessage(message.getAuthor().getName() + "님은"+ 시작시간_문자열 +
			// "부터\n"+ 끝시간_문자열 + "까지 하여\n총" +min + "분 " + sec + "초 공부했습니다.");
		}
		진행중 = false;

		총시간 = 총시간 + diff; // 합 저장. 포맷 가독성 위해서 그냥 저장.
	}

	void 총시간(MessageReceivedEvent e) {
		long hour = 총시간 / (60 * 60 * 1000);
		long min = 총시간 / (60 * 1000) % 60;
		long sec = 총시간 / 1000 % 60;
		// System.out.println();
		// e.getChannel().sendMessage("시스템 시작 이후로, " + message.getAuthor().getName() +
		// "님은 총" + hour + "시간 " +min + "분 " + sec + "초 공부했습니다.");
		e.getChannel().sendMessage("```diff\r\n" + "-시스템 시작 이후로,\n-" + e.getAuthor().getName() + "님은 총\n-" + hour
				+ "시간 " + min + "분 " + sec + "초 공부했습니다.\n" + "```");
	}

	void 일시정지(MessageReceivedEvent e, boolean 정지) {
		Date 정지시간 = null;
		if (정지) { // 0이면 해제 1이면 정지
			// 정지
			정지시간 = new Date();
		} else {
			// 해제
			멈춘시간 = 정지시간.getTime();
		}
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
