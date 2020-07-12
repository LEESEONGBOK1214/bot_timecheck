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




public class TimeCheckListener extends ListenerAdapter {
	String ��ɾ���[] = { "ping", "echo", "����", "��", "�ѽð�", "Ȧ��" };
	final ArrayList<user> user_arr = new ArrayList<user>();

	@Override
	public void onMessageReceived(MessageReceivedEvent e)
    {
		Message msg = e.getMessage(); 
		String cmd = e.getMessage().getContentRaw();
		MessageChannel ch = e.getChannel();

		//System.out.println(cmd);
		if (cmd.startsWith("$")) {

			cmd = cmd.substring(1);
			//System.out.println(cmd);

			switch (cmd) {
			case "echo":
				if (cmd.isEmpty()) {
					sayMsg(e.getChannel(), "echo�� �޾Ƹ��� ���� �Է��ؾ� �մϴ�.");
				} else {
					sayMsg(ch, cmd);
				}
				return;
			// echo ��ɾ� ��=========================

			case "ping":
				e.getChannel().sendMessage("pong!").queue();
				return;
			// ping ��ɾ� ��=========================

			case "���ɾ�":
				String ��� = "";
				��� += "===��ɾ� ���===\n";
				for (int i = 0; i < ��ɾ���.length; i++) {
					��� += (��ɾ���[i] + "\n");
				}
				sayMsg(ch, ���);
				// ���ɾ� ��ɾ� ��=========================
				return;
			case "����": // ����
				// �޼�������(message);
				del_Msg(msg);
				
				// message.delete();
				//System.out.println(e.getAuthor().getId());
				
				int ��ȣ = -1;
				for (int i = 0; i < user_arr.size(); i++) { // �ߺ��� Ȯ��
					// System.out.println("user_arr.get(i).id : " + user_arr.get(i).id);
					if (user_arr.get(i).�ߺ�Ȯ��(e.getAuthor().getId())) {// System.out.println("�ߺ�
																		// ����!!!");
						if (user_arr.get(i).������) {
							e.getChannel().sendMessage(user_arr.get(i).name + "-> �ߺ� �����߽��ϴ�.");
							return;
						} else {
							// �������� �ƴѵ� �ش� ������ �������.
							// ���̵� �ȸ�������.
							��ȣ = i;
							return;
						}
					}

				}
				// System.out.println(��ȣ);
				if (��ȣ >= 0) {
					user_arr.get(��ȣ).����();
				} else {
					user_arr.add(new user(e.getAuthor().getId(), e.getAuthor().getName()));
					��ȣ = user_arr.size() - 1;
				}

				// System.out.println("==========" + ��ȣ + "==========");

				// System.out.println("�ش� ������ ���۽ð� : " + user_arr.get(��ȣ).get���۽ð�());
				String ���۽ð� = ToTime(user_arr.get(��ȣ).get���۽ð�());
				e.getChannel().sendMessage("```ini\r\n[" + e.getAuthor().getName() + "]�� ���� �ð�\n[" + ���۽ð� + "]```");
				// ���� ��ɾ� ��=========================
				return;
			}
		}

    }
	private void del_Msg(Message msg)
	{
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
		// System.out.println("���� ���̵� : " + id);
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
		// System.out.println(message.getAuthor().getName() + "���� "+ ���۽ð�_���ڿ� + "���� "+
		// ���ð�_���ڿ� + "���� �Ͽ� ��" +min + "�� " + sec + "�� �����߽��ϴ�.");
		if (hour >= 1) {
			// 1�ð� �̻��϶�
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ�
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
			e.getChannel().sendMessage("```yaml\r\n" + e.getAuthor().getName() + "����\n" + ���۽ð�_���ڿ� + "����\n" + ���ð�_���ڿ�
					+ "���� �Ͽ�\n" + "��" + min + "�� " + sec + "�� �����߽��ϴ�.\n" + "```");
			// e.getChannel().sendMessage(message.getAuthor().getName() + "����"+ ���۽ð�_���ڿ� +
			// "����\n"+ ���ð�_���ڿ� + "���� �Ͽ�\n��" +min + "�� " + sec + "�� �����߽��ϴ�.");
		}
		������ = false;

		�ѽð� = �ѽð� + diff; // �� ����. ���� ������ ���ؼ� �׳� ����.
	}

	void �ѽð�(MessageReceivedEvent e) {
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
