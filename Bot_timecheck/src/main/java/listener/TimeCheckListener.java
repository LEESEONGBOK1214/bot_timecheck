package listener;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter {
	// DiscordApi discordbot;
	final ArrayList<user> user_arr = new ArrayList<user>();
	TimeCheckcmd tcc = new TimeCheckcmd(user_arr);
	
	boolean 강제종료 = false;
	ArrayList<Message> 시작확인메세지 = new ArrayList<Message>();
	public void onMessageReceived(MessageReceivedEvent e) {
//		System.out.println("e.getGuild().getName() : " + e.getGuild().getName());
		Message msg = e.getMessage();
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();
//		System.out.println("입력 : " + cmd);
		if(cmd.contains("시작확인")) {
			시작확인메세지.add(msg);
		}
		
		if (강제종료 && cmd.startsWith("!")) {
			// !로 시작하면.
//				System.out.println("강제종료 : " + 강제종료 +"\n" + cmd);
			cmd = cmd.substring(1); // ! 떼고 나서.
			if (tcc.고수인가(e.getMember().getId())) {
//					System.out.println("강제종료 후");
				강제종료 = tcc.강제종료하기(user_arr, e, cmd);
				if (강제종료) {
					sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0), "이름입력 ");
				}
			}
		}
		if (cmd.startsWith("$")) {

			cmd = cmd.substring(1);
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
			case "시작1": // 시작
//				System.out.println("시작 진입" + "시작확인보냄 : " + 시작확인보냄);
				if(시작확인메세지.size() != 0){
//					System.out.print("시작확인보냄 > for 진입 > ");
					for(int i =0; i< user_arr.size();i++) {
//						System.out.println(user_arr.get(i).시작확인);
						if(user_arr.get(i).시작확인)
						{
//							System.out.print("유저의 시작확인이 체크 > ");
							for(int j = 0; j<시작확인메세지.size();j++) {
								if(시작확인메세지.get(j).toString().contains(e.getMember().getUser().getName())) {
									Message tmpMsg = 시작확인메세지.get(j);
//									System.out.println("메세지 확인까지 옴.");
//									System.out.println("메세지 : " + msg);
//									System.out.println("체크된 메세지 : " + tmpMsg);
									시작확인메세지.remove(j);
									tmpMsg.delete().queue();
//									System.out.println(tmpMsg);
								}
							}
							user_arr.get(i).시작확인 = false;
						}
					}
					
				}
				del_Msg(msg);
//				System.out.println("시작 입력됨");
				tcc.start(user_arr, e);
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
				user_arr.get(14).getname();

//				System.out.println(tch.getApi()
//						.addUserChangeStatusListener(org.javacord.api.listener.user.UserChangeStatusListener)));
				return;

			case "주간시간보기":
				tcc.view_week(user_arr, e);
				return;
			case "일일시간보기":
				//System.out.println("일일시간보기 진입.");
				tcc.view_today(user_arr, e);
				return;

			case "출석":
				tcc.Attendance(e);
				return;

			case "강제종료":
				강제종료 = tcc.고수인가(e.getMember().getId());
				if (강제종료) {
					tcc.queue(user_arr, e);
					sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0), "이름입력");

				}
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
			if (!msg.equals("no")) {
				sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), msg);
			}
		}
		return;
	}

//	public void onGuildUpdateAfkChannel(@Nonnull GuildUpdateAfkChannelEvent e) {
//  말그대로 잠수 채널 변경시의 이벤트.. 별 의미 없다.
//		System.out.println(e.getNewAfkChannel());
//	}

//	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent e) {
//		// 무엇인지 아직 모른다. 실험 해봐야 함.
//		// 특정 길드에 들어갈 때 되면 좋겠다 ㅎㅎ..
//		// 뭔지 모르겠다. 그냥 작동 안함.
//		System.out.println(e.getMember().getUser().getName() + "\t" + e.getNewValue());
//	}
	public String 강제종료(String id) {

		int i = -1;
		for (int j = 0; j < user_arr.size(); j++) {
			if (user_arr.get(j).id.equals(id)) {
				i = j;
				break;
			}
		}
		String autoP = "";
		if (user_arr.get(i).id.equals(id)) {
			if (user_arr.get(i).진행중) {
				autoP = tcc.end(user_arr, id);
			} else {
				// System.out.println("진행중 아니므로 할거없따~");
				return "no";
			}
		}

		if (autoP == null || autoP.equals("")) {
			return null;
		}

		return autoP;

	}

	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent e) {
		String name = e.getMember().getUser().getName();
		String channel = e.getChannelJoined().getName();
		if(!e.getGuild().getName().equals("자격증스터디")) {
			return;
		}else if(e.getGuild().getName().equals("ㅅ1")) {
			String 출력문 = e.getMember().getUser().getAsMention() +" 시작확인!";
			sayMsg(e.getJDA().getTextChannelsByName("test1", true).get(0), 출력문);
		}
		
		if(channel.equals("1번방") || channel.equals("2번방") || channel.equals("3번방") || channel.equals("test01")) {
//			System.out.println("들어온 사람 : " + name);
//			System.out.println("들어간 채널 : " + channel );
			String 출력문 = e.getMember().getUser().getAsMention() +" 시작확인!";
			sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), 출력문);
//			System.out.println("getMem.usr.id : " + e.getMember().getUser().getId());
			for(int i =0; i< user_arr.size();i++) {
//				System.out.println("user_arr_id : " + user_arr.get(i).id);
				
				if(user_arr.get(i).id.equals(e.getMember().getUser().getId()))
				{
//					System.out.println("여기 안오니...??????@@@@@@@@@@@@@");
					user_arr.get(i).시작확인 = true;
				}
			}
//			TextChannel test01 = e.getJDA().getTextChannelsByName("test1", true).get(0);
//			MessageEmbed testMsgEmbed = new MessageEmbed(channel, channel, channel, null, null, count, null, null, null, null, null, null, null);
			
		}
		
	}

	public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent e) {
		// 길드 보이스 나감.. 이라는데.. 과연!!
//		System.out.println(e.getMember().getUser().getName());
//		System.out.println(e.getVoiceState().getChannel().getName());
		// sayMsg(e.getJDA().getTextChannelsByName("test1", true).get(0), "음성채팅방 나갈 때
		// 출력됩니다.");
		String msg = 강제종료(e.getMember().getId());
		if (!msg.equals("no")) {
			sayMsg(e.getJDA().getTextChannelsByName("test1", true).get(0), msg);
		}

	}

	private void sayMsg(TextChannel channel, String msg) {
		// System.out.println("메세지 @ : \n" + msg);
		// System.out.print("채널 : ");
		// System.out.println(channel.getName());
		// System.out.println("메세지 : " + msg);
		if (msg != null) {
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
