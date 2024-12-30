package pingplus.flyhacks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Fly {
    static boolean isFlying = false;
    // Register the /fly command
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("fly")
                .executes(context -> {
                    // Toggle flying mode
                    return toggleFly(context.getSource());
                })
                .then(CommandManager.argument("speed", FloatArgumentType.floatArg(0.1f, 100.0f))
                        .executes(context -> {
                            // Set flying speed
                            float speed = FloatArgumentType.getFloat(context, "speed");
                            return setFlySpeed(context.getSource(), speed);
                        })));
    }
    // Toggle flying mode
    private static int toggleFly(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        if (player != null) {

            if (isFlying) {
                // Disable flying
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
                player.sendAbilitiesUpdate();
                source.sendFeedback(() -> Text.literal("Flying disabled!"), false);
                isFlying=false;
            } else {
                // Enable flying
                player.getAbilities().allowFlying = true;
                player.getAbilities().flying = true;
                player.sendAbilitiesUpdate();
                source.sendFeedback(() -> Text.literal("Flying enabled!"), false);
                isFlying=true;
            }
        }
        return 1;
    }

    // Set flying speed
    private static int setFlySpeed(ServerCommandSource source, float speed) {
        ServerPlayerEntity player = source.getPlayer();

        if (player != null) {
            if (isFlying) {
                // Adjust the player's flight speed
                player.getAbilities().setFlySpeed(speed / 10.0f); // Minecraft uses a scale of 0.0 to 1.0 for fly speed
                player.sendAbilitiesUpdate();
                source.sendFeedback(() -> Text.literal("Flying speed set to " + speed + "!"), false);
            } else {
                source.sendFeedback(() -> Text.literal("You must be flying to set the speed! Use /fly to enable flying first."), false);
            }
        }
        return 1;
    }
}

