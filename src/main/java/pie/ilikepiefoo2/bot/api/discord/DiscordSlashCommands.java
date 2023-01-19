package pie.ilikepiefoo2.bot.api.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscordSlashCommands extends ListenerAdapter {
	private static final Logger LOG = LogManager.getLogger();
	private Command command;

	public DiscordSlashCommands(Command command) {
		this.command = command;
	}
	public static void upsertCommand(JDA jda) {
		jda.upsertCommand("class","Commands related to joining class specific channels.")
				.addSubcommands(
						createCommand(),
						joinCommand(),
						leaveCommand(),
						listCommand()
				).onSuccess(command -> {
					System.out.println("Successfully created command: " + command.getName());
					jda.addEventListener(new DiscordSlashCommands(command));
				}).queue();
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getFullCommandName().equals(command.getFullCommandName())) {
			if(event.getSubcommandName() == null){
				LOG.error("Error: Subcommand is null! Unable to process interaction!");
			}else {
				switch (event.getSubcommandName()) {
					case "create" -> ClassChannels.create(event);
					case "toggle" -> ClassChannels.toggle(event);
					case "leave" -> ClassChannels.leave(event);
					case "list" -> ClassChannels.list(event);
				}
			}
		}
	}



	public static SubcommandData createCommand() {
		return new SubcommandData("create","Create a class channel.")
				.addOptions(
						ClassChannels.subjectOption(),
						ClassChannels.classOption()
				);
	}

	public static SubcommandData joinCommand() {
		return new SubcommandData("join","Join a class channel.")
				.addOptions(
						ClassChannels.subjectOption(),
						ClassChannels.classOption()
				);
	}

	public static SubcommandData leaveCommand() {
		return new SubcommandData("leave","Leave a specific class channel.")
				.addOptions(
						new OptionData(
								OptionType.CHANNEL,
								"channel",
								"Channel to leave.",
								false
						)
				);
	}

	public static SubcommandData listCommand() {
		return new SubcommandData("list","List all the class channels that currently exist.");
	}

}
