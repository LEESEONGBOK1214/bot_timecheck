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
			case "홀리":
					tcc.holy(e, ch);
				return;
				
			case "산산":
					e.getChannel().sendMessage("산은 산산").queue();
				return;
				
			case "ping":
					e.getChannel().sendMessage("pong!").queue();
				return;
				
			case "복령어":
					tcc.cmdList(e, ch);
				return;
			case "시작": // 시작
				del_Msg(msg);
				System.out.println("시작 입력됨");
					tcc.start(user_arr, e);
				System.out.println("시작 파트 끝");
				return;

			case "끝":
				del_Msg(msg);
					tcc.end(user_arr, e, msg);
				return;
			case "큐":
   			  //메세지제거(message);
				del_Msg(msg);
					tcc.queue(user_arr);
	           	return;
			case "시간확인":
				del_Msg(msg);
				tcc.total_time(user_arr, e);
	          	return;
			case "일시정지":
				del_Msg(msg);
				// sayMsg(ch, "일시정지 테스트중.");
				tcc.pause(user_arr, e);
			case "시험기능":
				// tcc.test();
				return;

			case "주간시간보기":
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
		SimpleDateFormat 시간포맷 = new SimpleDateFormat("MM월/dd일/ HH시 :mm분 :ss초");

		// System.out.println(시간포맷.format(date));

		return 시간포맷.format(date);
	}
}

