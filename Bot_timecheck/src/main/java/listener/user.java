package listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.javacord.api.DiscordApi;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import oracleDB.OracleDB;

public class user {
	String id;
	String name;
	Date ���۽ð� = null;
	Date ���ð� = null;
	long ����ð� = 0;
	long �ѽð� = 0;
	boolean ������ = false;
	boolean ���� = false;
	// Font font = new Font();
	// Calendar ���۽ð� = Calendar.getInstance();
	// Calendar ���ð� = Calendar.getInstance();
	SimpleDateFormat �ð�������� = new SimpleDateFormat("MM/dd/ HH: mm: ss");
	// ������� ����� = new SimpleDateFormat("MM / dd / HH:mm");

	String �ð����; // ���� or ���ð�.format(cal.getTime());
	// String �� = ���۽ð�.format(cal.getTime());

	DiscordApi DA;
	public user(String id, String name) { // ���� �� ȣ��.
		// System.out.println("���� ���̵� : " + id);
		// ServerLeaveListener SLL = new ServerLeaveListener();

		// ServerMemberLeaveEvent SMLE = new ServerMemberLeaveEvent(DA.getServers()
		// server, user);
		if (id.equals(null)) {
			// System.out.println("�ΰ� ���� ������");
			return;
		} else {
			// System.out.println("�ΰ��ƴ� ������");
			this.id = id;
			this.name = name;
		}

		// ����();

	}

	void ����() {
		// System.out.println("����!");
		this.���۽ð� = new Date();
		// System.out.println(���۽ð�);
		this.������ = true;

	}

	// @SuppressWarnings("deprecation")
	long diff;

	String ��() {
		System.out.println("�� - ����");
		String retn;
		���ð� = new Date();

		diff = ���ð�.getTime() - ���۽ð�.getTime() - (����ð� * 1000); // ����ð��� �ʴ����� �����Ǿ��ִ�.

		long hour = diff / (60 * 60 * 1000);
		long min = diff / (60 * 1000) % 60;
		long sec = diff / 1000 % 60;

		String ���۽ð�_���ڿ� = �ð��������.format(���۽ð�.getTime());
		�ð�������� = new SimpleDateFormat("dd��/ HH��: mm��: ss��");
		String ���ð�_���ڿ� = �ð��������.format(���ð�.getTime());

		if (hour >= 1) {
			// 1�ð� �̻��϶�
			retn = ("```yaml\r\n" + name + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ�
					+ "���� �Ͽ�\n" + "��" + hour + "�ð� " + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```");
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
			retn = ("```yaml\r\n" + name + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ� + "���� �Ͽ�\n" + "��" + min + "�� " + sec
					+ "�� �����߽��ϴ�.\n" + "```");
			;
			// e.getChannel().sendMessage(message.getAuthor().getName() + "����"+ ���۽ð�_���ڿ� +
			// "����\n"+ ���ð�_���ڿ� + "���� �Ͽ�\n��" +min + "�� " + sec + "�� �����߽��ϴ�.");
		}

		������ = false;
		
		System.out.println("�� - retn : \n\t" + retn);
		System.out.println("�� - retn ����!");
		return retn;
		// �ѽð� = �ѽð� + diff; // �� ����. ���� ������ ���ؼ� �׳� ����.
	}


	void �ѽð�(MessageReceivedEvent e, ArrayList<user> user_arr) {
		// System.out.println("�ѽð� ����..!");
		OracleDB DB = new OracleDB(user_arr);
		// System.out.println("�ѽð� ���� ������ ID : " + id);
		�ѽð� = DB.today_time(id); // �ѽð��� �ʴ����� ����ǰ� ��ȯ�Ѵ�.
		// System.out.println("������ �ѽð� : " + �ѽð�);

		long hour = �ѽð� / (60 * 60);
		long min = (�ѽð� / 60) % 60;
		long sec = �ѽð� % 60;
		// System.out.println();
		// e.getChannel().sendMessage("�ý��� ���� ���ķ�, " + message.getAuthor().getName() +
		// "���� ��" + hour + "�ð� " +min + "�� " + sec + "�� �����߽��ϴ�.");
		e.getChannel().sendMessage("```diff\r\n" + "-�ý��� ���� ���ķ�,\n-" + name + "���� ��\n-" + hour
				+ "�ð� " + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```").queue();
	}

	Date �������۽ð� = null;
	Date ���������ð� = null;

	void �Ͻ�����(MessageReceivedEvent e) {

		//print(e.getChannel(), "test!");
		if (������ == false) {
			e.getChannel().sendMessage("���� ���Է�.").queue();
			return;
		}
		if (!����) { // 0�̸� ���� 1�̸� ����
			// ����
			System.out.println("���� ��");
			�������۽ð� = new Date();
			�ð�������� = new SimpleDateFormat("dd��/ HH��: mm��: ss��");
			String �������۹��ڿ� = �ð��������.format(�������۽ð�.getTime());
			e.getChannel().sendMessage("```tex\n$" + name + "���� ���۽ð� : " + �������۹��ڿ� + "\n```").queue();

		} else {
			// ����
			System.out.println("���� ��");

			���������ð� = new Date();

			����ð� = (���������ð�.getTime() - �������۽ð�.getTime()) / 1000;

			System.out.println("���� �ð� : " + ����ð�);
			e.getChannel().sendMessage("```tex\n$" + name + "����ð� : " + ����ð� + "\n```").queue();
		}

		���� = !����;
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
