package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeCheckListener extends ListenerAdapter {
	
	final ArrayList<user> user_arr = new ArrayList<user>();
	TimeCheckcmd tcc = new TimeCheckcmd(user_arr);
	// OracleDB DB = new OracleDB(user_arr);

	public TimeCheckListener() {
		// DB.select_user(user_arr);
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		Message msg = e.getMessage();
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();
		
		System.out.println(cmd);
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
			case "����": // ����
				del_Msg(msg);
				System.out.println("���� �Էµ�");
					tcc.start(user_arr, e);
				System.out.println("���� ��Ʈ ��");
				return;

			case "��":
				del_Msg(msg);
					tcc.end(user_arr, e, msg);
				return;
			case "ť":
   			  //�޼�������(message);
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
				return;

			case "�ְ��ð�����":
				tcc.view_week(user_arr, e);
				return;
			}
		}
	}

	private void del_Msg(Message msg) {
		msg.delete().queue();
	}

	private void sayMsg(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue();
	}

	private String ToTime(Date date) {
		SimpleDateFormat �ð����� = new SimpleDateFormat("MM��/dd��/ HH�� :mm�� :ss��");

		// System.out.println(�ð�����.format(date));

		return �ð�����.format(date);
	}
}

