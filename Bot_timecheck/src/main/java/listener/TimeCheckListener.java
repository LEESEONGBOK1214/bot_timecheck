package listener;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter {
	// =================================================================
	// 전역변수 영역
	final ArrayList<user> user_arr = new ArrayList<user>();
	TimeCheckcmd tcc = new TimeCheckcmd(user_arr);
	boolean 강제종료 = false; // 강제종료가 입력이 됐을 경우 true.
	final int 문제없음 = -999999;
	int 답 = 문제없음;	// 문제내기가 입력이 됐을 경우 true.
	// =================================================================

	

	public void onMessageReceived(MessageReceivedEvent e) {
//		System.out.println("채팅치는 채널 id :" + e.getChannel().getId());
//		System.out.println(e.toString());
		Message msg = e.getMessage();
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();

		// System.out.println(cmd);

//		System.out.println(강제종료 + cmd);
		if (cmd.startsWith("!")) {
			// !로 시작하면.
			cmd = cmd.substring(1); // ! 떼고 나서.
			if(강제종료) {
				tcc.강제종료하기(user_arr, e, cmd);	 // 강제종료 시키기.
				//강제종료() 메서드를 사용하면 시간이 추가가 됨.
				return;
			}
			else if(!cmd.equals("문제종료") && 답 != 문제없음) { // 답이 문제없음이 아님 => 문제가 있다.
				tcc.solve_problem(user_arr, e, 답, cmd);
				return;
			}else {
				switch(cmd) {
				case "문제종료":
					//System.out.println("문제 종료.");
					//sayMsg(e.getTextChannel(), "강제종료된 사람 수 : " + tcc.end_problem(user_arr, e));
					sayMsg(e.getTextChannel(), "문제 제출 종료.");
					답 = 문제없음;
					return;
				}
			}
			
		}
		if (cmd.startsWith("$")) {

			cmd = cmd.substring(1);
			// System.out.println(cmd);

			// System.out.println(cmd);
			switch (cmd) {
			case "홀리":
				tcc.holy(e, ch);
				return;

			case "산산":
				e.getChannel().sendMessage("산은 산산").queue();
				return;

			case "ping":
				e.getChannel().sendMessage("pong!").queue();
				return;

			case "복령어":
				tcc.cmdList(e, ch);
				return;

			case "tlwkr":
			case "시작": // 시작
				del_Msg(msg);
//				System.out.println("시작 입력됨");
				try {
					tcc.start(user_arr, e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				System.out.println("시작 파트 끝");
				return;

			case "whdfy":
			case "Rmx":
			case "정지":
			case "종료":
			case "긑":
			case "rmx":
			case "끝":
				del_Msg(msg);
				tcc.end(user_arr, e, msg);
				return;

			case "진행중":
				// 메세지제거(message);
				del_Msg(msg);
				tcc.queue(user_arr, e);
				return;
			case "총시간":
				del_Msg(msg);
				tcc.total_time(user_arr, e);
				return;
			case "일시정지":
				del_Msg(msg);
				// sayMsg(ch, "일시정지 테스트중.");
				tcc.pause(user_arr, e);
			case "시험기능":
				// tcc.test();
				// user_arr.get(14).getname();

//				System.out.println(tch.getApi()
//						.addUserChangeStatusListener(org.javacord.api.listener.user.UserChangeStatusListener)));
				return;

			case "주간시간보기":
				tcc.view_week(user_arr, e);
				return;
			case "일일시간보기":
				del_Msg(msg);
				tcc.view_today_times(user_arr, e);
				return;
			case "시간확인":
				del_Msg(msg);
				tcc.view_today_time(e);
				return;
			case "출석":
				tcc.Attendance(e);
				return;

			case "강제종료":
				강제종료 = tcc.고수인가(e.getMember().getId());
				if(강제종료) {
					tcc.queue(user_arr, e);
					sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0), "이름입력");
				}
				return;
//			case "문제내기":
//				
//				if(e.getTextChannel().getName().equals("문제내기"))
//				{
//					if(tcc.queue(user_arr, e) !=0 ) { // 진행중인 사람이 있어야 정상작동.
//						if(답 == 문제없음) { // 출제중이 아니므로 문제를 낸다.
//							답 = tcc.start_problem(user_arr, e); // 답 저장.
//							//System.out.println("start_problem 실행.");
//							//System.out.println("답 : " + 답);
//							return;
//						}else {//문제가 있다면 문제 풀이를 안한 사람 전부 강제 끝 처리.
//							sayMsg(e.getJDA().getTextChannelsByName("문제내기", true).get(0), "현재 제출된 문제가 있습니다.");
//							return;
//						}
//					}
//				}
//				
//				
//				return ;
			case "테스트":
				return ;
			case "DB유저수":
				//tcc.
				//sayMsg(e.getJDA().getTextChannelsByName("test1", true).get(0), "테스트중맞음.");
			}
			
				
		}
	}


	int count = 1;
	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent e) {
		
		if (!(count++ % 3 == 0))
			return;
		String id = e.getUser().getId();
		if (e.getNewOnlineStatus().toString().equals("OFFLINE")) {
			String msg = 강제종료(e.getMember().getId());
			if(!msg.equals("no")) {
				sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), msg);
			}
		}
		return;
	}
	public String 강제종료(String id) {

		int i = -1;
		for (int j = 0; j < user_arr.size(); j++) {
			if (user_arr.get(j).id.equals(id)) {
				i = j;
				break;
			}
		}
		String autoP = "";
		if(i==-1)return "no";
		
		if (user_arr.get(i).id.equals(id)) {
			if (user_arr.get(i).진행중) {
				autoP = tcc.end(user_arr, id);
			} else {
				//System.out.println("진행중 아니므로 할거없따~");
				return "no";
			}
		}

		if (autoP.equals( null) || autoP.equals("")) {
			return null;
		}

		return autoP;

	}

	public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent e) {
		String msg = 강제종료(e.getMember().getId());
		if(!msg.equals("no")) {
			sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), msg);
		}
		

	}

	private void sayMsg(TextChannel channel, String msg) {
		// System.out.println("메세지 @ : \n" + msg);
		//System.out.print("채널 : ");
		//System.out.println(channel.getName());
		//System.out.println("메세지 : " + msg);
		if(msg != null) {
			channel.sendMessage(msg).queue();
		}
		
//		channel.sendMessage("asdfasdfasdfasdf").apply();
//		channel.sendMessage("이건 되겠지").queue();
		// channel.sendMessage()
		return;
	}

	private void del_Msg(Message msg) {
		msg.delete().queue();
	}
}
