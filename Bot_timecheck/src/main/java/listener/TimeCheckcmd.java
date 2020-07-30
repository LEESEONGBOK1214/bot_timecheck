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

	// int ������ = 0;
	TimeCheckcmd(ArrayList<user> user_arr) {
		DB = new OracleDB(user_arr);
		DB.select_user(user_arr);
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
		String ��ɾ���[] = { "����", "��", "�ְ��ð�����", "�ð�Ȯ��(���� �� �ð�)", "�Ͻ����� (�ٽ��ϸ� ���������)", "ping", "Ȧ��", "���" };
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
		
		
		// System.out.println("start ����.");
		// System.out.println("DB.getusers() : " + DB.getusers());
		int ������ȣ = -1; // ArrayList�� ���� ������ȣ ã������.
		
		for (int i = 0; i < DB.getusers(); i++) { // �ߺ��� Ȯ��
			// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
			// System.out.println(i + "�� ���� id : " + user_arr.get(i).id);
			if (user_arr.get(i).id.equals(����ID)) { // ID�� ������ true ������ false
				System.out.println("===============id�˻� ����.");
				// ���� ������ ���� ��ȣ�� ã�Ƽ�, �������̸� �޼��� ��� �� ����.
				// System.out.println("����ID : " + ����ID);
				// System.out.println("������ : " + ������);
				user_arr.get(i).now_ch = e.getChannel().getId();
				if (user_arr.get(i).������) {
					e.getChannel().sendMessage("```ini\r\n[" + user_arr.get(i).name + "-> �ߺ� �����߽��ϴ�.]```").queue();
					// System.out.println("�ߺ� ���� : " + user_arr.get(i).name);
					return;
				} else {
					// �������� �ƴѵ� �ش� ������ �������.
					// ���̵� �ȸ�������.
					������ȣ = i;
					break;
				}
			}
		}
		// ��������!!
		if (������ȣ < 0) { // -1�϶��� ������ ���� ����!!
			// System.out.println("������ ����!!");
			user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
			String query = "insert into t_user values('" + ����ID + "', '" + ������ + "')";
			System.out.println(query);
			DB.insert(query);
			������ȣ = user_arr.size() - 1;

		}
		
		user_arr.get(������ȣ).����();
		System.out.println(
				"��ȣ : " + ������ȣ + " " + user_arr.get(������ȣ).name + "�� ����" + "�߽��ϴ�!!!!!!" + user_arr.get(������ȣ).������);


		// System.out.println("�ش� ������ ���۽ð� : " + user_arr.get(������ȣ).get���۽ð�());
		String ���۽ð� = ToTime(user_arr.get(������ȣ).get���۽ð�());


		sayMsg(e.getChannel(), "```ini\r\n[" + user_arr.get(������ȣ).name + "]�� ���� �ð�\n[" + ���۽ð� + "]```");
		
		// System.out.println("start ������.");
	}

	
	// end�� ����

	void end(ArrayList<user> user_arr, MessageReceivedEvent e, Message msg) {
		for (int i = 0; i < user_arr.size(); i++) { // ���ۿ� ���̵� �ִٸ�, �� ����.

			if (user_arr.get(i).�ߺ�Ȯ��(msg.getAuthor().getId())) {
				if (user_arr.get(i).������ == false) {
					return;
				}

				sayMsg(e.getChannel(), user_arr.get(i).��());
				{ // DB�� ������ ���۽ð� �ֱ�.
					// OracleDB DB = new OracleDB(user_arr);

					SimpleDateFormat �ð�������� = new SimpleDateFormat("yyMMdd");
					String start_date = �ð��������.format(user_arr.get(i).get���۽ð�().getTime());
					// System.out.println("========================start_date : " + start_date +
					// "=============");
					String query = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
							+ user_arr.get(i).diff / 1000
							+ ")";
					DB.insert(query);
				}


				// user_arr.remove(i);
				return;
			}
		}
	}

	public String end(ArrayList<user> user_arr, String id) {
		// System.out.println("end - ����.");
		// TODO Auto-generated method stub

//		for (int i = 0; i < user_arr.size(); i++) { // ���ۿ� ���̵� �ִٸ�, �� ����.
//
//			if (user_arr.get(i).�ߺ�Ȯ��(msg.getAuthor().getId())) {
//				if (user_arr.get(i).������ == false) {
//					return;
//				}
//
//				sayMsg(e.getChannel(), user_arr.get(i).��());
//				{ // DB�� ������ ���۽ð� �ֱ�.
//					// OracleDB DB = new OracleDB(user_arr);
//
//					SimpleDateFormat �ð�������� = new SimpleDateFormat("yyMMdd");
//					String start_date = �ð��������.format(user_arr.get(i).get���۽ð�().getTime());
//					// System.out.println("========================start_date : " + start_date +
//					// "=============");
//					String query = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
//							+ user_arr.get(i).diff / 1000
//							+ ")";
//					DB.insert(query);
//				}
//
//
//				// user_arr.remove(i);
//				return;
//			}
//		}


		String retn = "";

		for (int i = 0; i < user_arr.size(); i++) {
			System.out.println(i + " " + user_arr.get(i).name + user_arr.get(i).������);
			if (user_arr.get(i).id.equals(id) && user_arr.get(i).������) {
				retn = user_arr.get(i).��();


				SimpleDateFormat �ð�������� = new SimpleDateFormat("yyMMdd");
				String start_date = �ð��������.format(user_arr.get(i).get���۽ð�().getTime());
				String query = "insert into t_record values('" + user_arr.get(i).id + "', '" + start_date + "', "
						+ user_arr.get(i).diff / 1000 + ")";
				DB.insert(query);
				break;
			}
		}
		// System.out.println("end - ���� ����.\n" + "retn : " + retn);
		return retn;
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
	
	void view_week(ArrayList<user> user_arr, MessageReceivedEvent e) {
		String ��¹�[] = DB.week_time(user_arr.size());
		��¹�[0] += "\n";
		sayMsg(e.getChannel(), ��¹�[0]);
		// sayMsg(e.getChannel(), "\n");
		sayMsg(e.getChannel(), ��¹�[1]);
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
