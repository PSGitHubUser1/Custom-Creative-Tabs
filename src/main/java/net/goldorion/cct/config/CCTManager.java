package net.goldorion.cct.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.goldorion.cct.CCTMod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CCTManager {

    public static final File FOLDER = new File(FMLPaths.CONFIGDIR.get().toFile(), "cct");

    private static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();

    public static List<CustomCreativeTab> readCustomTabs() {
        List<CustomCreativeTab> TABS = new ArrayList<>();
        if (FOLDER.exists()) {
            Arrays.stream(Objects.requireNonNull(FOLDER.listFiles())).filter(f -> f.getName().endsWith(".json")).forEach(file -> {
                try {
                    CustomCreativeTab tab = gson.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), CustomCreativeTab.class);

                    TABS.add(tab);
                } catch (IOException e) {
                    CCTMod.LOG.error("Error while loading custom creative tabs", e);
                }
            });
        } else {
            FOLDER.mkdir();
        }
        return TABS;
    }

    public static boolean generateTabFile(CustomCreativeTab tab) {
        try {
            FileUtils.writeStringToFile(new File(FOLDER, tab.registry_name + ".json"), gson.toJson(tab, CustomCreativeTab.class), StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            CCTMod.LOG.error("Error while generating the custom creative tab for " + tab.registry_name, e);
            return false;
        }
    }
}