package net.goldorion.cct.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.goldorion.cct.customtabs.CCTManager;
import net.goldorion.cct.customtabs.CustomCreativeTab;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

@Mod.EventBusSubscriber
public class CCTCommands {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("cct")
                .requires(s -> s.hasPermission(4))
                .then(Commands.argument("registryName", StringArgumentType.word())
                        .then(Commands.argument("icon", ItemArgument.item(event.getBuildContext()))
                                .then(Commands.argument("enableSearchBar", BoolArgumentType.bool())
                                        .then(Commands.argument("items", MessageArgument.message()).executes(context -> {
                                            Optional<ResourceKey<Item>> icon = ForgeRegistries.ITEMS.getResourceKey(ItemArgument.getItem(context, "icon").getItem());

                                            CustomCreativeTab tab = new CustomCreativeTab(StringArgumentType.getString(context, "registryName"),
                                                    icon.map(itemResourceKey -> itemResourceKey.location().toString()).orElse("minecraft:air"),
                                                    BoolArgumentType.getBool(context, "enableSearchBar"),
                                                    Arrays.stream(MessageArgument.getMessage(context, "items").getString().split(" ")).toList());

                                            if (CCTManager.generateTabFile(tab)) {
                                                context.getSource().sendSystemMessage(Component.translatable("command.cct.generate_file.success", tab.registry_name,
                                                        new File(CCTManager.FOLDER, tab.registry_name + ".json").getPath()));
                                                return 1;
                                            } else {
                                                context.getSource().sendSystemMessage(Component.translatable("command.cct.generate_file.fail", tab.registry_name));
                                                return 0;
                                            }
                                        }))))));
    }

}
