package pie.ilikepiefoo.bot.api;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SubCommandWrapper {
	private final SubcommandData subcommandData;

	public SubCommandWrapper(SubcommandData subcommandData) {
		this.subcommandData = subcommandData;
	}

	public static SubCommandWrapper of(SubcommandData data) {
		return new SubCommandWrapper(data);
	}

	public SubCommandWrapper unwrap() {
		return of(subcommandData);
	}

	public SubCommandWrapper setName(String name) {
		return of(subcommandData.setName(name));
	}

	public SubCommandWrapper setNameLocalization(DiscordLocale locale, String name) {
		return of(subcommandData.setNameLocalization(locale, name));
	}

	public SubCommandWrapper setNameLocalizations(Map<DiscordLocale, String> map) {
		return of(subcommandData.setNameLocalizations(map));
	}

	public SubCommandWrapper setDescription(String description) {
		return of(subcommandData.setDescription(description));
	}

	public SubCommandWrapper setDescriptionLocalization(DiscordLocale locale, String description) {
		return of(subcommandData.setDescriptionLocalization(locale, description));
	}

	public SubCommandWrapper setDescriptionLocalizations(Map<DiscordLocale, String> map) {
		return of(subcommandData.setDescriptionLocalizations(map));
	}

	public SubCommandWrapper addOptions(OptionData... options) {
		return of(subcommandData.addOptions(options));
	}

	public SubCommandWrapper addOptions(Collection<? extends OptionData> options) {
		return of(subcommandData.addOptions(options));
	}

	public SubCommandWrapper addOption(OptionType type, String name, String description, boolean required, boolean autoComplete) {
		return of(subcommandData.addOption(type, name, description, required, autoComplete));
	}

	public SubCommandWrapper addOption(OptionType type, String name, String description, boolean required) {
		return of(subcommandData.addOption(type, name, description, required));
	}

	public SubCommandWrapper addOption(OptionType type, String name, String description) {
		return of(subcommandData.addOption(type, name, description));
	}

	public List<OptionData> getOptions() {
		return subcommandData.getOptions();
	}

	public String getName() {
		return subcommandData.getName();
	}

	public LocalizationMap getNameLocalizations() {
		return subcommandData.getNameLocalizations();
	}

	public String getDescription() {
		return subcommandData.getDescription();
	}

	public LocalizationMap getDescriptionLocalizations() {
		return subcommandData.getDescriptionLocalizations();
	}

	public DataObject toData() {
		return subcommandData.toData();
	}

	public static SubCommandWrapper fromData(DataObject json) {
		return of(SubcommandData.fromData(json));
	}

	public static SubCommandWrapper fromSubcommand(Command.Subcommand subcommand) {
		return of(SubcommandData.fromSubcommand(subcommand));
	}
}
