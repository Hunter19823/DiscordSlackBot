import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import pie.ilikepiefoo2.bot.api.discord.DiscordSlashCommands;

public class DiscordTest {
    public static void main( String[] args ) {
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.VOICE_STATE, CacheFlag.MEMBER_OVERRIDES);

        // Enable bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);

        // Set activity
        builder.setActivity(Activity.playing("Messing around with JDA Api"));

        JDA jda = builder.build();
		DiscordSlashCommands.upsertCommand(jda);
    }
}
