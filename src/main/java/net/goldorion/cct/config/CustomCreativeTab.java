package net.goldorion.cct.config;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CustomCreativeTab {

    public String registry_name;
    public String icon;
    public boolean enable_search_bar;
    public List<String> items;

    public CustomCreativeTab(String registry_name, String icon, boolean enable_search_bar, List<String> items) {
        this.registry_name = registry_name;
        this.icon = icon;
        this.enable_search_bar = enable_search_bar;
        this.items = items;
    }

    @Override
    public String toString() {
        return registry_name;
    }
}
