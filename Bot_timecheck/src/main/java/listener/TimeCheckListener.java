package listener;

import java.util.ArrayList;

import org.javacord.api.entity.channel.Channel;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter{
	// DiscordApi discordbot;
	final ArrayList<user> user_arr = new ArrayList<user>();
	TimeCheckcmd tcc = new TimeCheckcmd(user_arr);
	// OracleDB DB = new OracleDB(user_arr);


	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
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
				System.out.println("���� �Էµ�");
				tcc.start(user_arr, e);
				System.out.println("���� ��Ʈ ��");
				return;

			case "whdfy":
			case "Rmx":
			case "����":
			case "��":
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
		System.out.println(e.getNewOnlineStatus().name());
		// message.editMessage("test1");
		String id = e.getUser().getId();
		String name = e.getUser().getName();
		System.out.println("=========================���º�ȯ!!!");
		// e.getGuild().getSystemChannel().sendMessage("=========================���º�ȯ!!!").queue();
		if (e.getNewOnlineStatus().toString().equals("OFFLINE")) {

			System.out.println(name + "������ offline���·� ��ȯ.");
			String autoP = "";
			// System.out.println("id : " + id);
			// System.out.println("���� ������ : " + user_arr.size());
			for (int i = 0; i < user_arr.size(); i++) {
				// System.out.println("ua.get i id : " + user_arr.get(i).id);
				if (user_arr.get(i).id.equals(id)) {
					if (user_arr.get(i).������) {
						System.out.println("������ true!");
						break;
					}else {

					}
				}
			}
			autoP = tcc.end(user_arr, id);
			// System.out.println("@@@@@@tcc.end : " + tcc.end(user_arr, id));
			if (autoP == null || autoP.equals("")) {
				return;
			}
			System.out.println("############���º�ȯ �̺�Ʈ ������ - autoP : \n  " + autoP);
			sayMsg(e.getGuild().getSystemChannel(), autoP);

		}

		//public MessageUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Message message)

		return;
	}

	private void sayMsg(TextChannel channel, String msg) {
		System.out.println("�޼��� @ : \n" + msg);

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

