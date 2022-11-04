package survivalplugin.com.goudenhelden.plugin.Com.GoudenHelden;

import org.bukkit.plugin.java.JavaPlugin;
import survivalplugin.com.goudenhelden.plugin.Com.GoudenHelden.commands.CommandCoords;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.getCommand("coords").setExecutor(new CommandCoords(this));
        loadConfig();
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(false);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
