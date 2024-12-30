package pingplus.flyhacks;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegistry {

    // Method to register all commands
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        Fly.register(dispatcher);

    }
}