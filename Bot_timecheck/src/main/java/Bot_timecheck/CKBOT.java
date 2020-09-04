package Bot_timecheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import listener.TimeCheckListener;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

public class CKBOT {
	public static void main(String[] args) {

		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NzI1Mzc1MjQ3MjcyMTE2MjY0.XxcYKg.XBhfhthYAmurxWSUS2jhDbLyxcc"; // 토큰은 여러분의 디스코드 앱의 봇에서 가져옵니다.
		builder.setToken(token);

		new BotFrame(builder, token);

//		try {
//			builder.addEventListeners(new TimeCheckListener());
//			// builder.addEventListeners(new Guild_Voice_Events());
//			builder.build();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("시작!");
	}
}

class BotFrame extends JFrame implements ActionListener {
	JDABuilder builder;
	String token;
	JButton 시작버튼;
	JButton 종료버튼;

	BotFrame(JDABuilder builder, String token) {
		super("창이름");
		setSize(250, 250);
		setLayout(null);

		시작버튼 = new JButton("시작버튼");
		종료버튼 = new JButton("종료버튼");
//		JPanel 버튼판넬 = new JPanel();
//		버튼판넬.add(시작버튼);
//		버튼판넬.setSize(250, 250);
//		버튼판넬.setBorder(new LineBorder(Color.pink, 8));
		시작버튼.setBounds(70, 45, 100, 50);
		종료버튼.setBounds(70, 100, 100, 50);

		시작버튼.addActionListener(this);
		종료버튼.addActionListener(this);

//		token = "NzI1Mzc1MjQ3MjcyMTE2MjY0.XxcYKg.XBhfhthYAmurxWSUS2jhDbLyxcc";
		System.out.println("frame진입");

		builder.setToken(token);
		add(시작버튼);
		add(종료버튼);

		this.builder = builder;
		this.token = token;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(시작버튼)) {
			System.out.println("시작버튼 클릭");
			try {
				builder.addEventListeners(new TimeCheckListener());
				// builder.addEventListeners(new AfkListener());
				// builder.addEventListeners(new Guild_Voice_Events());
				builder.build();
			} catch (Exception ec) {
				ec.printStackTrace();
			}
		} else if (e.getSource().equals(종료버튼)) {
			System.out.println("종료버튼 클릭");
			System.exit(0);
		}
	}
}