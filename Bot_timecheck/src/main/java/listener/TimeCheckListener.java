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

			TimeCheckcmd tcc = new TimeCheckcmd();
			System.out.println(cmd);
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
				System.out.println("start");
					tcc.start(user_arr, e);
				System.out.println("end");
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
			case "�ѽð�":
				del_Msg(msg);
	  			  
	          	  for(int i=0;i<user_arr.size();i++) { // ���ۿ� ���̵� �ִٸ�, �� ����.
	          		  //  System.out.println("���� id : " + user_arr.get(i).id);
	          		  if(user_arr.get(i).�ߺ�Ȯ��(e.getAuthor().getId()))
	          		  {// �� ã�Ƽ� �ش� �� �ѽð� ���.
	          			  //System.out.println("��ã��?");
	          			  //message.reply("i");
	          			  user_arr.get(i).�ѽð�(e, msg);
	          		  }
	          	  }
	          	return;
			case "������":
				tcc.test();
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

class user {
	String id;
	String name;
	private Date ���۽ð� = null;
	private Date ���ð� = null;
	long ����ð� = 0;
	long �ѽð� = 0;
	boolean ������ = false;
	// Font font = new Font();
	// Calendar ���۽ð� = Calendar.getInstance();
	// Calendar ���ð� = Calendar.getInstance();
	SimpleDateFormat �ð�������� = new SimpleDateFormat("MM/dd/ HH: mm: ss");
	// ������� ����� = new SimpleDateFormat("MM / dd / HH:mm");

	String �ð����; // ���� or ���ð�.format(cal.getTime());
	// String �� = ���۽ð�.format(cal.getTime());

	user(String id, String name) { // ���� �� ȣ��.
		System.out.println("���� ���̵� : " + id);
		if (id.equals(null)) {
			// System.out.println("�ΰ� ���� ������");
			return;
		} else {
			// System.out.println("�ΰ��ƴ� ������");
			this.id = id;
			this.name = name;
		}

		����();

	}

	void ����() {
		// System.out.println("����!");
		this.���۽ð� = new Date();
		// System.out.println(���۽ð�);
		this.������ = true;
	}

	// @SuppressWarnings("deprecation")
	void ��(MessageReceivedEvent e) {

		���ð� = new Date();

		long diff = ���ð�.getTime() - ���۽ð�.getTime() + ����ð�;

		long hour = diff / (60 * 60 * 1000);
		long min = diff / (60 * 1000) % 60;
		long sec = diff / 1000 % 60;

		String ���۽ð�_���ڿ� = �ð��������.format(���۽ð�.getTime());
		�ð�������� = new SimpleDateFormat("dd��/ HH��: mm��: ss��");
		String ���ð�_���ڿ� = �ð��������.format(���ð�.getTime());
		
		if (hour >= 1) {
			// 1�ð� �̻��϶�
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ�
					+ "���� �Ͽ�\n" + "��" + hour + "�ð� " + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```").queue();
			/*
			 * e.getChannel().sendMessage("```css\r\n#" + message.getAuthor().getName() +
			 * "����\n#" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ� + "���� �Ͽ�\n#" + "��" + hour + "�ð� " +min
			 * + "�� " + sec + "�� �����߽��ϴ�.\n" + "```");
			 */
			// e.getChannel().sendMessage(message.getAuthor().getName() + "����"+ ���۽ð�_���ڿ� +
			// "����\n"+ ���ð�_���ڿ� + "���� �Ͽ�\n��" + hour + "�ð� " +min + "�� " + sec + "�� �����߽��ϴ�.");
		} else {
			// 1�ð� �̸��϶�,
			// �ð� ��� x
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ�
					+ "���� �Ͽ�\n" + "��" + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```").queue();;
			// e.getChannel().sendMessage(message.getAuthor().getName() + "����"+ ���۽ð�_���ڿ� +
			// "����\n"+ ���ð�_���ڿ� + "���� �Ͽ�\n��" +min + "�� " + sec + "�� �����߽��ϴ�.");
		}
		������ = false;

		�ѽð� = �ѽð� + diff; // �� ����. ���� ������ ���ؼ� �׳� ����.
	}

	void �ѽð�(MessageReceivedEvent e, Message msg) {
		long hour = �ѽð� / (60 * 60 * 1000);
		long min = �ѽð� / (60 * 1000) % 60;
		long sec = �ѽð� / 1000 % 60;
		// System.out.println();
		// e.getChannel().sendMessage("�ý��� ���� ���ķ�, " + message.getAuthor().getName() +
		// "���� ��" + hour + "�ð� " +min + "�� " + sec + "�� �����߽��ϴ�.");
		e.getChannel().sendMessage("```diff\r\n" + "-�ý��� ���� ���ķ�,\n-" + e.getAuthor().getName() + "���� ��\n-" + hour
				+ "�ð� " + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```");
	}

	void �Ͻ�����(MessageReceivedEvent e, boolean ����) {
		Date �����ð� = null;
		if (����) { // 0�̸� ���� 1�̸� ����
			// ����
			�����ð� = new Date();
		} else {
			// ����
			����ð� = �����ð�.getTime();
		}
	}

	boolean �ߺ�Ȯ��(String id) {
		if (id.equals(this.id)) {
			// �ߺ�!!
			return true;
		}
		return false;
	}

	/*
	 * ��� ���� for(int i=0;i<user_arr.size();i++) { // ���ۿ� ���̵� �ִٸ�, �� ����.
	 * if(user_arr.get(i).�ߺ�Ȯ��(message.getAuthor().getAvatarId())) {
	 * 
	 * } }
	 */

	Date get���۽ð�() {
		return this.���۽ð�;
	}
}
