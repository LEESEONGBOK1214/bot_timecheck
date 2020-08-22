package listener;

import java.util.ArrayList;

import org.javacord.api.entity.channel.Channel;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter {
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

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
//		System.out.println("채팅치는 채널 id :" + e.getChannel().getId());
//		System.out.println(e.toString());
		Message msg = e.getMessage();
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();

		// System.out.println(cmd);
		// System.out.println(cmd);
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

			case "whdfy":case "Rmx":case "정지":case "종료":case "긑":case "rmx":
			case "끝":
				del_Msg(msg);
				tcc.end(user_arr, e, msg);
				return;
				
			case "진행중":
				// 메세지제거(message);
				del_Msg(msg);
				tcc.queue(user_arr, e);
				return;
			case "시간확인":
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
				
			case "출석":
				tcc.Attendance(e);
				return;
			}
		}
	}

//	public void onStatusChange(StatusChangeEvent e) {
//		// TODO Auto-generated method stub
//		// System.out.println("상태 변환..?");
//		System.out.println("ns : " + e.getNewStatus());
//		System.out.println("os : " + e.getOldStatus());
//	}

//	public void onUserActivityStart(@Nonnull UserActivityStartEvent e)
//	{
//		// System.out.println(e.getGuild());
//		System.out.print(e.getUser() + "  ");
//		System.out.println("onUserActivityStart");
//	}
//
//	public void onUserActivityEnd(@Nonnull UserActivityEndEvent e) {
//		System.out.print(e.getUser());
//		System.out.println("=============onUserActivityEnd===============");
//		tcc.end(user_arr, e.getUser().getId());
//	}
//
//	public void onUserUpdateActivityOrder(@Nonnull UserUpdateActivityOrderEvent e) {
//		System.out.println("상태 변환 했슴.!!");
//	}

//	public void onGuildJoin(@Nonnull GuildJoinEvent e) {
//		System.out.println(e.getGuild().getId() + " 유저가 ");
//		System.out.println(e.getGuild() + "서버에 들어옴.");
//	}
//
//	public void onGuildLeave(@Nonnull GuildLeaveEvent e) {
//		System.out.println(e.getGuild().getId() + " 유저가 ");
//		System.out.println(e.getGuild() + "서버에서 퇴장.");
//	}
//
//	public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) {
//		System.out.println(e.getGuild().getId() + " 유저가 ");
//		System.out.println(e.getGuild() + "채널에 들어옴.");
//	}
//
//	public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent e) {
//		System.out.println(e.getGuild().getId() + " 유저가 ");
//		System.out.println(e.getGuild() + "채널에서 퇴장.");
//	}
	boolean sw = false;
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
		System.out.println("상태변환..?" + e.getNewOnlineStatus());
		if (!(count++ % 3 == 0))
			return;
		// sw = !sw;
		// System.out.println(e.getNewOnlineStatus().name());
		// message.editMessage("test1");
//		List<TextChannel> ch_list = e.getGuild().getTextChannels();

		String id = e.getUser().getId();
		String name = e.getUser().getName();
		// System.out.println("=========================상태변환!!!");
		// e.getGuild().getSystemChannel().sendMessage("=========================상태변환!!!").queue();
		System.out.println("여기까진?");
		if (e.getNewOnlineStatus().toString().equals("OFFLINE")) {
			int i = -1;

			for (int j = 0; j < user_arr.size(); j++) {
				if (user_arr.get(j).id.equals(e.getUser().getId())) {
					System.out.println("기준 : " + user_arr.get(j).now_ch);
					i = j;
					break;
				}
			}
			/*
			 * for (int j = 0; j < ch_list.size(); j++) {
			 * 
			 * System.out.println("비교 : " + ch_list.get(j).getId()); if
			 * (ch_list.get(j).getId().equals(user_arr.get(i).now_ch)) {
			 * System.out.println("채널 id 같음"); } }
			 */
			System.out.println(name + "유저가 offline상태로 변환.");
			String autoP = "";
			// System.out.println("id : " + id);
			// System.out.println("유저 사이즈 : " + user_arr.size());

			if (user_arr.get(i).id.equals(id)) {
				if (user_arr.get(i).진행중) {
					System.out.println("진행중 true!");
					// break;
				} else {

				}
			}

			autoP = tcc.end(user_arr, id);
			// System.out.println("@@@@@@tcc.end : " + tcc.end(user_arr, id));
			if (autoP == null || autoP.equals("")) {
				return;
			}
			// System.out.println("############상태변환 이벤트 리스너 - autoP : \n " + autoP);
			// System.out.println("ch_id : " + ch_id);
			// System.out.println("e.getJDA().getTextChannelById(ch_id).getName() :" +
			// e.getJDA().getTextChannelById(ch_id).getName());
			// System.out.println("e.getJDA().getTextChannelById(ch_id) :" +
			// e.getJDA().getTextChannelById(ch_id));
			System.out.println();

//			System.out.println(user_arr.get(i).now_ch);
//			System.out.println("현재 유저가 있는 TextChannel id : " + 
//								e.getJDA().getTextChannelById(user_arr.get(i).now_ch));
//			System.out.println("현재 유저가 있는 TextChannel id : " + 
//								e.getGuild().getTextChannelById(user_arr.get(i).now_ch));
			
//			System.out.println(e.getGuild().getChannels().equals(user_arr.get(i).now_ch));
			sayMsg(e.getJDA().getTextChannelById(user_arr.get(i).now_ch), autoP);
			
			

			// public MessageUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull
			// Message message)
		}
		return;
	}

	private void sayMsg(TextChannel channel, String msg) {
		// System.out.println("메세지 @ : \n" + msg);
		System.out.print("채널 : ");
		// System.out.println(channel.);
		channel.sendMessage(msg).queue();
//		channel.sendMessage("asdfasdfasdfasdf").apply();
//		channel.sendMessage("이건 되겠지").queue();
		// channel.sendMessage()
		return;
	}

	private void del_Msg(Message msg) {
		msg.delete().queue();
	}
}
