package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import oracleDB.OracleDB;

public class TimeCheckcmd {
	OracleDB DB;

	TimeCheckcmd(ArrayList<user> user_arr) {
		DB = new OracleDB(user_arr);
	}

	void test() {
		// DB.test();
	}
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

	void total_time(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String ����ID = e.getAuthor().getId();
		String ������ = e.getAuthor().getName();
		
		for (int i = 0; i < user_arr.size(); i++) {
			// System.out.println("���� id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(����ID))
      		  {// �� ã�Ƽ� �ش� �� �ѽð� ���.
      			  //System.out.println("��ã��?");
      			  //message.reply("i");
					user_arr.get(i).�ѽð�(e, user_arr);
					return;
      		  }
      	  }
	}
	
	void start(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String ����ID = e.getAuthor().getId();
		String ������ = e.getAuthor().getName();

		System.out.println("start ����.");

		int ������ȣ = -1; // ArrayList�� ���� ������ȣ ã������.
		for (int i = 0; i < DB.getusers(); i++) { // �ߺ��� Ȯ��
			// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(����ID)) { // ID�� ������ true ������ false
				// ���� ������ ���� ��ȣ�� ã�Ƽ�, �������̸� �޼��� ��� �� ����.
				System.out.println("����ID : " + ����ID);
				System.out.println("������ : " + ������);
				if (user_arr.get(i).������) {
					e.getChannel().sendMessage("```ini\r\n[" + user_arr.get(i).name + "-> �ߺ� �����߽��ϴ�.]```").queue();
					System.out.println("�ߺ� ���� : " + user_arr.get(i).name);
					return;
				} else {
					// �������� �ƴѵ� �ش� ������ �������.
					// ���̵� �ȸ�������.
					������ȣ = i;
					break;
				}
			}

		}
		// System.out.println(��ȣ);

		if (������ȣ >= 0) {
			user_arr.get(������ȣ).����();
		} else { // ������ ���� ���.
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			String quary = 
					"insert into t_user values('" + ����ID + "', '" + ������ + "')";
			System.out.println(quary);
			DB.insert(quary);
			������ȣ = user_arr.size() - 1;
			user_arr.get(������ȣ).����();
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
				{ // DB�� ������ ���۽ð� �ֱ�.
					OracleDB DB = new OracleDB(user_arr);

					SimpleDateFormat �ð�������� = new SimpleDateFormat("yyMMdd");
					String start_date = �ð��������.format(user_arr.get(i).get���۽ð�().getTime());
					System.out.println("========================start_date : " + start_date + "=============");
					String quary = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
							+ user_arr.get(i).diff / 1000
							+ ")";
					DB.insert(quary);
				}
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
	
	void pause(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String ����ID = e.getAuthor().getId();
		String ������ = e.getAuthor().getName();

		// System.out.println("�Ͻ����� �޼��� ���� ID" + ����ID);
		for (int i = 0; i < user_arr.size(); i++) {
			if (����ID.equals(user_arr.get(i).id)) {
				// System.out.println("�Ͻ����� �޼��� ��ġ ���� ID" + user_arr.get(i).id);
				user_arr.get(i).�Ͻ�����(e);
				break;
			}
		}

	}
	
	void view_week() {

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
