package Bot_timecheck;

import listener.TimeCheckListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

public class MainApp {
	public static void main(String[] args) {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NzI1Mzc1MjQ3MjcyMTE2MjY0.XxcYKg.XBhfhthYAmurxWSUS2jhDbLyxcc"; //��ū�� �������� ���ڵ� ���� ������ �����ɴϴ�.
		builder.setToken(token);
		try {
			builder.addEventListeners(new TimeCheckListener());
			// builder.addEventListeners(new Guild_Voice_Events());
			builder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("����!");
	}
}