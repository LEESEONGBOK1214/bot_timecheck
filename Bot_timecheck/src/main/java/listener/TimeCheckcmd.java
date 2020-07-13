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

public class TimeCheckcmd {
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
	
	void start(ArrayList<user> user_arr, MessageReceivedEvent e) {
		System.out.println("start 들어옴.");
		int 유저번호 = -1; // ArrayList의 현재 유저번호 찾기위함.
		for (int i = 0; i < user_arr.size(); i++) { // 중복값 확인
			// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
			if (user_arr.get(i).중복확인(e.getAuthor().getId())) {
				// 현재 유저와 같은 번호를 찾아서, 진행중이면 메세지 출력 후 종료.
				if (user_arr.get(i).진행중) {
					e.getChannel().sendMessage(user_arr.get(i).name + "-> 중복 시작했습니다.");
					return;
				} else {
					// 진행중은 아닌데 해당 유저가 있을경우.
					// 아이디를 안만들어야함.
					유저번호 = i;
					return;
				}
			}

		}
		// System.out.println(번호);
		if (유저번호 >= 0) {
			user_arr.get(유저번호).시작();
		} else {
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			유저번호 = user_arr.size() - 1;
		}
		System.out.println(유저번호);
		// System.out.println("==========" + 번호 + "==========");

		System.out.println("해당 유저의 시작시간 : " + user_arr.get(유저번호).get시작시간());
		String 시작시간 = ToTime(user_arr.get(유저번호).get시작시간());
		sayMsg(e.getChannel(), "```ini\r\n[" + e.getAuthor().getName() + "]의 시작 시간\n[" + 시작시간 + "]```");
		
		System.out.println("start 마지막.");
	}
	
	// end의 시작
	void end(ArrayList<user> user_arr, MessageReceivedEvent e, Message msg) {
		for (int i = 0; i < user_arr.size(); i++) { // 시작에 아이디가 있다면, 끝 실행.

			if (user_arr.get(i).중복확인(msg.getAuthor().getId())) {
				if (user_arr.get(i).진행중 == false)
					return;
				user_arr.get(i).끝(e);
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
