package Bot_timecheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import listener.TimeCheckListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

public class CKBOT  {
	public static void main(String[] args) {
		
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NzI1Mzc1MjQ3MjcyMTE2MjY0.XxcYKg.XBhfhthYAmurxWSUS2jhDbLyxcc"; //��ū�� �������� ���ڵ� ���� ������ �����ɴϴ�.
		builder.setToken(token);
		
		
		
		
		
		new BotFrame(builder, token);
		
		
//		try {
//			builder.addEventListeners(new TimeCheckListener());
//			// builder.addEventListeners(new Guild_Voice_Events());
//			builder.build();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("����!");
	}
}

class BotFrame extends JFrame implements ActionListener{
	JDABuilder builder;
	String token;
	JButton ���۹�ư;
	JButton �����ư;
	BotFrame(JDABuilder builder, String token)
	{
		super("â�̸�");
		setSize(250, 250);
		setLayout(null);
		
		���۹�ư = new JButton("���۹�ư");
		�����ư = new JButton("�����ư");
//		JPanel ��ư�ǳ� = new JPanel();
//		��ư�ǳ�.add(���۹�ư);
//		��ư�ǳ�.setSize(250, 250);
//		��ư�ǳ�.setBorder(new LineBorder(Color.pink, 8));
		���۹�ư.setBounds(70, 45, 100, 50);
		�����ư.setBounds(70, 100, 100, 50);
		
		
		���۹�ư.addActionListener(this);
		�����ư.addActionListener(this);
		
//		token = "NzI1Mzc1MjQ3MjcyMTE2MjY0.XxcYKg.XBhfhthYAmurxWSUS2jhDbLyxcc";
		System.out.println("frame����");
		
		
		builder.setToken(token);
		add(���۹�ư);
		add(�����ư);
		
		
		
		this.builder = builder;
		this.token = token;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(���۹�ư)) {
			System.out.println("���۹�ư Ŭ��");
			try {
				builder.addEventListeners(new TimeCheckListener());
				// builder.addEventListeners(new Guild_Voice_Events());
				builder.build();
			} catch (Exception ec) {
				ec.printStackTrace();
			}
		}else if(e.getSource().equals(�����ư)) {
			System.out.println("�����ư Ŭ��");
			System.exit(0);
		}
	}
}