package listener;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSuppressEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Guild_Voice_Events extends ListenerAdapter {
	// Operations op;
	public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent e) {

	}

	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent e) {
		System.out.println(e.getMember().getUser().getName() + "유저가 보이스채팅 들어옴!");
	}

	public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent e) {
	}

	public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent e) {
		TimeCheckListener tcl = new TimeCheckListener();
		
		// System.out.println("OUT!!");
		// System.out.println(e.getGuild().getJDA().getSelfUser().);
		// tcl.Operations("끝", e.getGuild().getJDA().getSelfUser());

	}

	public void onGuildVoiceMute(@Nonnull GuildVoiceMuteEvent e) {
	}

	public void onGuildVoiceDeafen(@Nonnull GuildVoiceDeafenEvent e) {
	}

	public void onGuildVoiceGuildMute(@Nonnull GuildVoiceGuildMuteEvent e) {
	}

	public void onGuildVoiceGuildDeafen(@Nonnull GuildVoiceGuildDeafenEvent e) {
	}

	public void onGuildVoiceSelfMute(@Nonnull GuildVoiceSelfMuteEvent e) {
	}

	public void onGuildVoiceSelfDeafen(@Nonnull GuildVoiceSelfDeafenEvent e) {
	}

	public void onGuildVoiceSuppress(@Nonnull GuildVoiceSuppressEvent e) {
	}
}
