package net.goldorion.cct;

import com.mojang.logging.LogUtils;
import net.goldorion.cct.customtabs.CCTManager;
import net.goldorion.cct.customtabs.CustomCreativeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod(CCTMod.MODID)
public class CCTMod {

    public static final String MODID = "cct";
    public static final Logger LOG = LogUtils.getLogger();

    private static Map<CreativeModeTab, CustomCreativeTab> tabsMap = new HashMap<>();

    public CCTMod() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(CCTMod::registerCreativeTabs);
        bus.addListener(CCTMod::addItemsToTabs);

    }

    private static void registerCreativeTabs(CreativeModeTabEvent.Register event) {
        tabsMap = new HashMap<>();
        CCTManager.readCustomTabs().forEach(tab -> {
            if (tab.registry_name != null && !tab.registry_name.isEmpty()) {
                tabsMap.put(event.registerCreativeModeTab(new ResourceLocation(MODID, tab.registry_name), builder -> {
                    builder.icon(() -> Objects.requireNonNullElse(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tab.icon)), Items.AIR)
                            .getDefaultInstance()).title(Component.translatable("itemGroup." + tab.registry_name));
                    if (tab.enable_search_bar)
                        builder.withSearchBar();
                }), tab);
            }
        });
    }

    private static void addItemsToTabs(CreativeModeTabEvent.BuildContents event) {
        CustomCreativeTab tab = getTab(event);
        if (tab != null && tab.items != null && !tab.items.isEmpty()) {
            tab.items.forEach(e -> {
                if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(e))) {
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(e));
                    if (item != null)
                        event.accept(item);
                } else {
                    LOG.warn(e + " is not a registered item. It will be skipped.");
                }
            });
        }
    }

    private static CustomCreativeTab getTab(CreativeModeTabEvent.BuildContents event) {
        final CustomCreativeTab[] tab = new CustomCreativeTab[1];
        tabsMap.forEach((k, v) -> {
            if (k == event.getTab())
                tab[0] = v;
        });
        return tab[0];
    }

}