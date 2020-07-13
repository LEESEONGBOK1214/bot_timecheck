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
			sayMsg(ch, "echo�� �޾Ƹ��� ���� �Է��ؾ� �մϴ�.");
		} else {
			sayMsg(ch, cmd);
		}
	}
	
	void holy(MessageReceivedEvent e, MessageChannel ch)
	{
		if (e.getAuthor().getId().equals("715527843827810355")) {
			sayMsg(ch, "����");
		} else {
			sayMsg(ch, "����");
		}
	}
	
	void cmdList(MessageReceivedEvent e, MessageChannel ch) {
		String ��ɾ���[] = { "ping", "echo", "����", "��", "�ѽð�", "Ȧ��" };
		String ��� = "";
		��� += "===��ɾ� ���===\n";
		for (int i = 0; i < ��ɾ���.length; i++) {
			��� += (��ɾ���[i] + "\n");
		}
		sayMsg(ch, ���);
	}
	
	void start(ArrayList<user> user_arr, MessageReceivedEvent e) {
		System.out.println("start ����.");
		int ������ȣ = -1; // ArrayList�� ���� ������ȣ ã������.
		for (int i = 0; i < user_arr.size(); i++) { // �ߺ��� Ȯ��
			// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
			if (user_arr.get(i).�ߺ�Ȯ��(e.getAuthor().getId())) {
				// ���� ������ ���� ��ȣ�� ã�Ƽ�, �������̸� �޼��� ��� �� ����.
				if (user_arr.get(i).������) {
					e.getChannel().sendMessage(user_arr.get(i).name + "-> �ߺ� �����߽��ϴ�.");
					return;
				} else {
					// �������� �ƴѵ� �ش� ������ �������.
					// ���̵� �ȸ�������.
					������ȣ = i;
					return;
				}
			}

		}
		// System.out.println(��ȣ);
		if (������ȣ >= 0) {
			user_arr.get(������ȣ).����();
		} else {
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			������ȣ = user_arr.size() - 1;
		}
		System.out.println(������ȣ);
		// System.out.println("==========" + ��ȣ + "==========");

		System.out.println("�ش� ������ ���۽ð� : " + user_arr.get(������ȣ).get���۽ð�());
		String ���۽ð� = ToTime(user_arr.get(������ȣ).get���۽ð�());
		sayMsg(e.getChannel(), "```ini\r\n[" + e.getAuthor().getName() + "]�� ���� �ð�\n[" + ���۽ð� + "]```");
		
		System.out.println("start ������.");
	}
	
	// end�� ����
	void end(ArrayList<user> user_arr, MessageReceivedEvent e, Message msg) {
		for (int i = 0; i < user_arr.size(); i++) { // ���ۿ� ���̵� �ִٸ�, �� ����.

			if (user_arr.get(i).�ߺ�Ȯ��(msg.getAuthor().getId())) {
				if (user_arr.get(i).������ == false)
					return;
				user_arr.get(i).��(e);
				// user_arr.remove(i);
				return;
			}
		}
	}
	// end of end
	
	void queue(ArrayList<user> user_arr) {
		System.out.println("===========ť==========");
     	  for(int i=0;i<user_arr.size();i++)
     	  {
     		  System.out.println(user_arr.get(i).name + " " + user_arr.get(i).id);
     	  }
     	  System.out.println("===========ť==========");
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
