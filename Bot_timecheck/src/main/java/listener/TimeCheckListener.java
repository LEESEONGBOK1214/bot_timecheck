package listener;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import org.javacord.api.entity.channel.Channel;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkChannelEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter implements GuildVoiceState {
	// DiscordApi discordbot;
	final ArrayList<user> user_arr = new ArrayList<user>();
	TimeCheckcmd tcc = new TimeCheckcmd(user_arr);

	void Operations(String op) {
//		for (int i = 0; i < user_arr.size(); i++) {
//			System.out.println(user_arr.get(i).name + " = " + user_arr.get(i).진행중);
//		}
		switch (op) {
		case "끝":
			System.out.println("go to tcc-end");

			// String 출력문 = tcc.end(user_arr, ch.getId());
			// System.out.println("받은 출력문 ##################\n" + 출력문);
			return;
		}
	}

	boolean 강제종료 = false;

	public void onMessageReceived(MessageReceivedEvent e) {
//		System.out.println("채팅치는 채널 id :" + e.getChannel().getId());
//		System.out.println(e.toString());
		Message msg = e.getMessage();
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();

		// System.out.println(cmd);
		// System.out.println(cmd);

//		System.out.println(강제종료 + cmd);
		if (강제종료 && cmd.startsWith("!")) {
  // !로 시작하면.
//				System.out.println("강제종료 : " + 강제종료 +"\n" + cmd);
			cmd = cmd.substring(1); // ! 떼고 나서.
			if (tcc.고수인가(e.getMember().getId())) {
//					System.out.println("강제종료 후");
				강제종료 = tcc.강제종료하기(user_arr, e, cmd);	
				if(강제종료) {
					sayMsg(e.getJDA().getTextChannelsByName("강제종료", true).get(0), "이름입력 ");
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
				Channel tch;

//				System.out.println(tch.getApi()
//						.addUserChangeStatusListener(org.javacord.api.listener.user.UserChangeStatusListener)));
				return;

			case "주간시간보기":
				tcc.view_week(user_arr, e);
				return;
			case "일일시간보기":
				tcc.view_today(user_arr, e);
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
			}
		}
	}

	/*
	 * // public void onStatusChange(StatusChangeEvent e) { // // TODO
	 * Auto-generated method stub // // System.out.println("상태 변환..?"); //
	 * System.out.println("ns : " + e.getNewStatus()); // System.out.println("os : "
	 * + e.getOldStatus()); // }
	 * 
	 * // public void onUserActivityStart(@Nonnull UserActivityStartEvent e) // { //
	 * // System.out.println(e.getGuild()); // System.out.print(e.getUser() + "  ");
	 * // System.out.println("onUserActivityStart"); // } // // public void
	 * onUserActivityEnd(UserActivityEndEvent e) { // System.out.print(e.getUser());
	 * // System.out.println("=============onUserActivityEnd==============="); //
	 * tcc.end(user_arr, e.getUser().getId()); // } // // public void
	 * onUserUpdateActivityOrder(@Nonnull UserUpdateActivityOrderEvent e) { //
	 * System.out.println("상태 변환 했슴.!!"); // }
	 * 
	 * // public void onGuildJoin(@Nonnull GuildJoinEvent e) { //
	 * System.out.println(e.getGuild().getId() + " 유저가 "); //
	 * System.out.println(e.getGuild() + "서버에 들어옴."); // } // // public void
	 * onGuildLeave(@Nonnull GuildLeaveEvent e) { //
	 * System.out.println(e.getGuild().getId() + " 유저가 "); //
	 * System.out.println(e.getGuild() + "서버에서 퇴장."); // } // // public void
	 * onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) { //
	 * System.out.println(e.getGuild().getId() + " 유저가 "); //
	 * System.out.println(e.getGuild() + "서버에 들어옴."); // } // // public void
	 * onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent e) { //
	 * System.out.println(e.getGuild().getId() + " 유저가 "); //
	 * System.out.println(e.getGuild() + "서버에서 퇴장."); // }
	 * 
	 */

	int count = 1;

	// public void on

	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent e) {
		// 유저 상태 확인 리스너.
		// System.out.println("onUserUpdateOnlineStatus");
		// System.out.println(e.getMember() + "유저가 " + e.getNewOnlineStatus());
		// Message message;
		// String 출력문 = "";
//		if()
		// if(e.)
//		System.out.println("상태변환..?" + e.getNewOnlineStatus());
		if (!(count++ % 3 == 0))
			return;
		// sw = !sw;
		// System.out.println(e.getNewOnlineStatus().name());
		// message.editMessage("test1");
//		List<TextChannel> ch_list = e.getGuild().getTextChannels();

		String id = e.getUser().getId();
		// System.out.println("=========================상태변환!!!");
		// e.getGuild().getSystemChannel().sendMessage("=========================상태변환!!!").queue();
		// System.out.println("여기까진?");
		if (e.getNewOnlineStatus().toString().equals("OFFLINE")) {
			String msg = 강제종료(e.getMember().getId());
			if(!msg.equals("no")) {
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
				System.out.println("진행중 아니므로 할거없따~");
				return "no";
			}
		}

		if (autoP == null || autoP.equals("")) {
			return null;
		}

		return autoP;

	}

	public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent e) {
		// 길드 보이스 나감.. 이라는데.. 과연!!
//		System.out.println(e.getMember().getUser().getName());
//		System.out.println(e.getVoiceState().getChannel().getName());
		String msg = 강제종료(e.getMember().getId());
		if(!msg.equals("no")) {
			sayMsg(e.getJDA().getTextChannelsByName("출석", true).get(0), msg);
		}
		

	}

	private void sayMsg(TextChannel channel, String msg) {
		// System.out.println("메세지 @ : \n" + msg);
		System.out.print("채널 : ");
		System.out.println(channel.getName());
		System.out.println("메세지 : " + msg);
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

	public JDA getJDA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSelfMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelfDeafened() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDeafened() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGuildMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGuildDeafened() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuppressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VoiceChannel getChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guild getGuild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getMember() {
		return null;
	}

	@Override
	public boolean inVoiceChannel() {
//		// TODO Auto-generated method stub
//		net.dv8tion.jda.api.entities.Member Member = getMember();
//		net.dv8tion.jda.api.entities.VoiceChannel VoiceChannel;
//		System.out.println("inVoiceChannel Evenet");
//		if(guild.getVoiceChannelsByName("잠수채널(15분 잠수시)", true) != null) {
//			System.out.println(name + "유저가 잠수채널로 입장.");
//		}
		return false;
	}

	@Override
	public String getSessionId() {
		return null;
	}

}
