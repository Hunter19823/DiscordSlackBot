package pie.ilikepiefoo2.bot.api.discord;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pie.ilikepiefoo2.bot.api.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassChannels {
	private static final Logger LOG = LogManager.getLogger();
	private static List<Command.Choice> choices = List.of();

	public static boolean isEnabled(Guild guild) {
		return Config.getInstance().getGuildConfig(guild).hasAttribute("class_channels");
	}

	public static String getClassChannelCategory(Guild guild) {
		return Config.getInstance().getGuildConfig(guild).getAttribute("class_channel_category");
	}


	public static void create(SlashCommandInteractionEvent event) {
		if(!ClassChannels.isEnabled(event.getGuild())) {
			event.reply("Class channels are not enabled on this server.").setEphemeral(true).queue();
			return;
		}
		String subject = Objects.requireNonNull(event.getOption("subject")).getAsString().toLowerCase();
		int classNumber = Objects.requireNonNull(event.getOption("class")).getAsInt();
		String category = ClassChannels.getClassChannelCategory(event.getGuild());
		var cat = event.getGuild().getCategoryById(category);
		if(cat == null) {
			event.reply("The class channel category is not set up correctly.").setEphemeral(true).queue();
			LOG.error("Error: Class channel category is not set up correctly! Unable to create class channel!\nGuild: {}(#{})", event.getGuild().getName(), event.getGuild().getId());
			return;
		}
		cat.getTextChannels().parallelStream().filter((textChannel) -> {
			return textChannel.getName().contains(subject+"-"+classNumber);
		}).findAny().ifPresent((textChannel -> {
			event.reply("A class channel for "+subject+"-"+classNumber+" already exists.")
					.setEphemeral(true)
					.addActionRow(
							Button.primary(
									"join-"+
											textChannel.getGuild().getId()+
											"-"+
											cat.getId()+
											"-"+
											textChannel.getId()+
											"-"+
											Objects.requireNonNull(event.getMember()).getId(),
							"Click here to toggle viewing that class channel.")
					);
		})
		);
	}

	public static void toggle(SlashCommandInteractionEvent event) {
		if(!ClassChannels.isEnabled(event.getGuild())) {
			event.reply("Class channels are not enabled on this server.").setEphemeral(true).queue();
			return;
		}
		String subject = Objects.requireNonNull(event.getOption("subject")).getAsString().toLowerCase();
		int classNumber = Objects.requireNonNull(event.getOption("class")).getAsInt();
		String category = ClassChannels.getClassChannelCategory(event.getGuild());
		var cat = event.getGuild().getCategoryById(category);
		if(cat == null) {
			event.reply("The class channel category is not set up correctly.").setEphemeral(true).queue();
			LOG.error("Error: Class channel category is not set up correctly! Unable to create class channel!\nGuild: {}(#{})", event.getGuild().getName(), event.getGuild().getId());
			return;
		}
		cat.getTextChannels().parallelStream().filter((textChannel) -> {
			return textChannel.getName().contains(subject+"-"+classNumber);
		}).findAny().ifPresent((textChannel) -> {
			textChannel.getPermissionContainer().upsertPermissionOverride(Objects.requireNonNull(event.getMember()))
					// TODO: Toggle permissions for the user.
		});

	}

	public static void leave(SlashCommandInteractionEvent event) {
		if(!ClassChannels.isEnabled(event.getGuild())) {
			event.reply("Class channels are not enabled on this server.").setEphemeral(true).queue();
			return;
		}
		// TODO: Leave a class channel.
	}

	public static void list(SlashCommandInteractionEvent event) {
		if (!ClassChannels.isEnabled(event.getGuild())) {
			event.reply("Class channels are not enabled on this server.").setEphemeral(true).queue();
			return;
		}
		// TODO: List all class channels.
	}

	public static List<Command.Choice> loadChoice() {
		choices = new ArrayList<>();
		Document document = Config.getInstance().config;
		Element el = document.getElementById("class_abbreviations");
		var children = el.getElementsByTagName("choice");
		for (int i = 0; i < children.getLength(); i++) {
			var child = children.item(i);
			if (child instanceof Element element) {
				var name = element.getAttribute("name");
				var value = element.getAttribute("value");
				choices.add(new Command.Choice(name, value));
			}
		}
		return choices;
	}

	public static void addChoice(String name, String value) {
		Document document = Config.getInstance().config;
		Element el = document.getElementById("class_abbreviations");
		Element choice = document.createElement("choice");
		choice.setAttribute("name", name);
		choice.setAttribute("value", value);
		el.appendChild(choice);
		choices.add(new Command.Choice(name, value));
	}


	public static OptionData classOption() {
		return new OptionData(
				OptionType.INTEGER,
				"class",
				"The specific class number for the course. Ex: 101, 121, 222, 316, etc..."
		)
				.setMinValue(100)
				.setMaxValue(999)
				.setRequired(true);
	}

	public static OptionData subjectOption() {
		return new OptionData(
				OptionType.STRING,
				"subject",
				"The class subject name. Ex: SER, CSE, EGR, etc.",
				true,
				true
		)
				.setMinLength(3)
				.setMaxLength(3)
				.addChoices(ClassChannels.loadChoice());
	}
}
