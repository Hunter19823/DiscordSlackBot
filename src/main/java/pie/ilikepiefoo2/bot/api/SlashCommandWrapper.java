package pie.ilikepiefoo2.bot.api;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SlashCommandWrapper {
	private final SlashCommandData commandData;

	public SlashCommandWrapper(SlashCommandData commandData) {
		this.commandData = commandData;
	}

	public static SlashCommandWrapper of(SlashCommandData data) {
		return new SlashCommandWrapper(data);
	}

	public SlashCommandData unwrap() {
		return commandData;
	}

	public SlashCommandWrapper setLocalizationFunction(LocalizationFunction localizationFunction) {
		return of(commandData.setLocalizationFunction(localizationFunction));
	}

	public SlashCommandWrapper setName(String name) {
		return of(commandData.setName(name));
	}

	public SlashCommandWrapper setNameLocalization(DiscordLocale locale, String name) {
		return of(commandData.setNameLocalization(locale, name));
	}

	public SlashCommandWrapper setNameLocalizations(Map<DiscordLocale, String> map) {
		return of(commandData.setNameLocalizations(map));
	}

	public SlashCommandWrapper setDefaultPermissions(DefaultMemberPermissions permission) {
		return of(commandData.setDefaultPermissions(permission));
	}

	public SlashCommandWrapper setGuildOnly(boolean guildOnly) {
		return of(commandData.setGuildOnly(guildOnly));
	}

	public SlashCommandWrapper setNSFW(boolean nsfw) {
		return of(commandData.setNSFW(nsfw));
	}

	public SlashCommandWrapper setDescription(String description) {
		return of(commandData.setDescription(description));
	}

	public SlashCommandWrapper setDescriptionLocalization(DiscordLocale locale, String description) {
		return of(commandData.setDescriptionLocalization(locale, description));
	}

	public SlashCommandWrapper setDescriptionLocalizations(Map<DiscordLocale, String> map) {
		return of(commandData.setDescriptionLocalizations(map));
	}

	public String getDescription() {
		return commandData.getDescription();
	}

	public LocalizationMap getDescriptionLocalizations() {
		return commandData.getDescriptionLocalizations();
	}

	public List<SubcommandData> getSubcommands() {
		return commandData.getSubcommands();
	}

	public List<SubcommandGroupData> getSubcommandGroups() {
		return commandData.getSubcommandGroups();
	}

	public List<OptionData> getOptions() {
		return commandData.getOptions();
	}

	public SlashCommandWrapper addOptions(OptionData... options) {
		return of(commandData.addOptions(options));
	}

	public SlashCommandWrapper addOptions(Collection<? extends OptionData> options) {
		return of(commandData.addOptions(options));
	}

	public SlashCommandWrapper addOption(OptionType type, String name, String description, boolean required, boolean autoComplete) {
		return of(commandData.addOption(type, name, description, required, autoComplete));
	}

	public SlashCommandWrapper addOption(OptionType type, String name, String description, boolean required) {
		return of(commandData.addOption(type, name, description, required));
	}

	public SlashCommandWrapper addOption(OptionType type, String name, String description) {
		return of(commandData.addOption(type, name, description));
	}

	public SlashCommandWrapper addSubcommands(SubcommandData... subcommands) {
		return of(commandData.addSubcommands(subcommands));
	}

	public SlashCommandWrapper addSubcommands(Collection<? extends SubcommandData> subcommands) {
		return of(commandData.addSubcommands(subcommands));
	}

	public SlashCommandWrapper addSubcommandGroups(SubcommandGroupData... groups) {
		return of(commandData.addSubcommandGroups(groups));
	}

	public SlashCommandWrapper addSubcommandGroups(Collection<? extends SubcommandGroupData> groups) {
		return of(commandData.addSubcommandGroups(groups));
	}

	public static SlashCommandWrapper fromCommand(Command command) {
		return of(SlashCommandData.fromCommand(command));
	}

	public static SlashCommandWrapper fromData(DataObject object) {
		return of(SlashCommandData.fromData(object));
	}

	public String getName() {
		return commandData.getName();
	}

	public LocalizationMap getNameLocalizations() {
		return commandData.getNameLocalizations();
	}

	public Command.Type getType() {
		return commandData.getType();
	}

	public DefaultMemberPermissions getDefaultPermissions() {
		return commandData.getDefaultPermissions();
	}

	public boolean isGuildOnly() {
		return commandData.isGuildOnly();
	}

	public boolean isNSFW() {
		return commandData.isNSFW();
	}

	public DataObject toData() {
		return commandData.toData();
	}
}
