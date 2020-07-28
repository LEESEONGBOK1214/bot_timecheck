package listener;

import java.util.ArrayList;
import java.util.List;

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
//			System.out.println(user_arr.get(i).name + " = " + user_arr.get(i).������);
//		}
		switch (op) {
		case "��":
			System.out.println("go to tcc-end");

			// String ��¹� = tcc.end(user_arr, ch.getId());
			// System.out.println("���� ��¹� ##################\n" + ��¹�);
			return;
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		System.out.println("ä��ġ�� ä�� id :" + e.getChannel().getId());
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
			case "Ȧ��":
				tcc.holy(e, ch);
				return;

			case "���":
				e.getChannel().sendMessage("���� ���").queue();
				return;

			case "ping":
				e.getChannel().sendMessage("pong!").queue();
				return;

			case "���ɾ�":
				tcc.cmdList(e, ch);
				return;

			case "tlwkr":
			case "����": // ����
				del_Msg(msg);
//				System.out.println("���� �Էµ�");
				tcc.start(user_arr, e);
//				System.out.println("���� ��Ʈ ��");
				return;

			case "whdfy":
			case "Rmx":
			case "����":
			case "��":
			case "�P":
				del_Msg(msg);
				tcc.end(user_arr, e, msg);
				return;
			case "ť":
				// �޼�������(message);
				del_Msg(msg);
				tcc.queue(user_arr);
				return;
			case "�ð�Ȯ��":
				del_Msg(msg);
				tcc.total_time(user_arr, e);
				return;
			case "�Ͻ�����":
				del_Msg(msg);
				// sayMsg(ch, "�Ͻ����� �׽�Ʈ��.");
				tcc.pause(user_arr, e);
			case "������":
				// tcc.test();
				Channel tch;

//				System.out.println(tch.getApi()
//						.addUserChangeStatusListener(org.javacord.api.listener.user.UserChangeStatusListener)));
				return;

			case "�ְ��ð�����":
				tcc.view_week(user_arr, e);
				return;
			}
		}
	}

//	public void onStatusChange(StatusChangeEvent e) {
//		// TODO Auto-generated method stub
//		// System.out.println("���� ��ȯ..?");
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
//		System.out.println("���� ��ȯ �߽�.!!");
//	}

//	public void onGuildJoin(@Nonnull GuildJoinEvent e) {
//		System.out.println(e.getGuild().getId() + " ������ ");
//		System.out.println(e.getGuild() + "������ ����.");
//	}
//
//	public void onGuildLeave(@Nonnull GuildLeaveEvent e) {
//		System.out.println(e.getGuild().getId() + " ������ ");
//		System.out.println(e.getGuild() + "�������� ����.");
//	}
//
//	public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) {
//		System.out.println(e.getGuild().getId() + " ������ ");
//		System.out.println(e.getGuild() + "ä�ο� ����.");
//	}
//
//	public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent e) {
//		System.out.println(e.getGuild().getId() + " ������ ");
//		System.out.println(e.getGuild() + "ä�ο��� ����.");
//	}
	boolean sw = false;
	int count = 1;

	// public void on

	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent e) {
		// ���� ���� Ȯ�� ������.
		// System.out.println("onUserUpdateOnlineStatus");
		// System.out.println(e.getMember() + "������ " + e.getNewOnlineStatus());
		// Message message;
		// String ��¹� = "";
//		if()
		// if(e.)
		if (!(count++ % 3 == 0))
			return;
		// sw = !sw;
		// System.out.println(e.getNewOnlineStatus().name());
		// message.editMessage("test1");
		List<TextChannel> ch_list = e.getGuild().getTextChannels();

		String id = e.getUser().getId();
		String name = e.getUser().getName();
		// System.out.println("=========================���º�ȯ!!!");
		// e.getGuild().getSystemChannel().sendMessage("=========================���º�ȯ!!!").queue();
		if (e.getNewOnlineStatus().toString().equals("OFFLINE")) {
			int i = -1;

			for (int j = 0; j < user_arr.size(); j++) {
				if (user_arr.get(j).id.equals(e.getUser().getId())) {
					System.out.println("���� : " + user_arr.get(j).now_ch);
					i = j;
					break;
				}
			}
			/*
			 * for (int j = 0; j < ch_list.size(); j++) {
			 * 
			 * System.out.println("�� : " + ch_list.get(j).getId()); if
			 * (ch_list.get(j).getId().equals(user_arr.get(i).now_ch)) {
			 * System.out.println("ä�� id ����"); } }
			 */
			System.out.println(name + "������ offline���·� ��ȯ.");
			String autoP = "";
			// System.out.println("id : " + id);
			// System.out.println("���� ������ : " + user_arr.size());

			if (user_arr.get(i).id.equals(id)) {
				if (user_arr.get(i).������) {
					System.out.println("������ true!");
					// break;
				} else {

				}
			}

			autoP = tcc.end(user_arr, id);
			// System.out.println("@@@@@@tcc.end : " + tcc.end(user_arr, id));
			if (autoP == null || autoP.equals("")) {
				return;
			}
			// System.out.println("############���º�ȯ �̺�Ʈ ������ - autoP : \n " + autoP);
			// System.out.println("ch_id : " + ch_id);
			// System.out.println("e.getJDA().getTextChannelById(ch_id).getName() :" +
			// e.getJDA().getTextChannelById(ch_id).getName());
			// System.out.println("e.getJDA().getTextChannelById(ch_id) :" +
			// e.getJDA().getTextChannelById(ch_id));
			System.out.println();

//			System.out.println(user_arr.get(i).now_ch);
//			System.out.println("���� ������ �ִ� TextChannel id : " + 
//								e.getJDA().getTextChannelById(user_arr.get(i).now_ch));
//			System.out.println("���� ������ �ִ� TextChannel id : " + 
//								e.getGuild().getTextChannelById(user_arr.get(i).now_ch));
			
//			System.out.println(e.getGuild().getChannels().equals(user_arr.get(i).now_ch));
			sayMsg(e.getJDA().getTextChannelById(user_arr.get(i).now_ch), autoP);
			
			

			// public MessageUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull
			// Message message)
		}
		return;
	}

	private void sayMsg(TextChannel channel, String msg) {
		// System.out.println("�޼��� @ : \n" + msg);
		System.out.print("ä�� : ");
		// System.out.println(channel.);
		channel.sendMessage(msg).queue();
//		channel.sendMessage("asdfasdfasdfasdf").apply();
//		channel.sendMessage("�̰� �ǰ���").queue();
		// channel.sendMessage()
		return;
	}

	private void del_Msg(Message msg) {
		msg.delete().queue();
	}
}
