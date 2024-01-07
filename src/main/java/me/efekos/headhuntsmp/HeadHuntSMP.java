package me.efekos.headhuntsmp;

import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.headhuntsmp.events.PickupOwnHead;
import me.efekos.headhuntsmp.events.Place;
import me.efekos.headhuntsmp.events.PlayerCraftHead;
import me.efekos.headhuntsmp.events.PlayerKilled;
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.headhuntsmp.utils.AnchorRecipeManager;
import me.efekos.headhuntsmp.utils.Logger;
import me.efekos.headhuntsmp.utils.HeadRecipeManager;
import me.efekos.simpler.Metrics;
import me.efekos.simpler.items.ItemManager;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadHuntSMP extends JavaPlugin {

    private static HeadHuntSMP plugin;

    public static HeadHuntSMP getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Logger.Info("Plugin starting");

        Metrics metrics = new Metrics(this,18888);

        Logger.Log("Loading config");
        GameConfig.setup();

        Logger.Log("Loading recipes");
        if(GameConfig.get().getBoolean("extra-head.enabled")){
            try {
                if(GameConfig.get().getBoolean("extra-head.use-default")) HeadRecipeManager.loadDefaultRecipe(this);
                else HeadRecipeManager.loadConfigRecipe(this);

                Bukkit.addRecipe(HeadRecipeManager.getLastLoadedRecipe());
                Bukkit.addRecipe(AnchorRecipeManager.getLastLoadedRecipe());
            } catch (Exception e){
                Logger.Error("There was an error loading the recipes:");
                Logger.Error(e.getMessage());
            }
        }

        if(GameConfig.get().getBoolean("unban-anchor.enabled")){
            try {
                if(GameConfig.get().getBoolean("unban-anchor.use-default")) AnchorRecipeManager.loadDefaultRecipe(this);
                else AnchorRecipeManager.loadConfigRecipe(this);

                Bukkit.addRecipe(AnchorRecipeManager.getLastLoadedRecipe());
            } catch (Exception e){
                Logger.Error("There was an error loading the recipes:");
                Logger.Error(e.getMessage());
            }
        }

        Logger.Log("Loading events");
        getServer().getPluginManager().registerEvents(new PickupOwnHead(),this);
        getServer().getPluginManager().registerEvents(new PlayerKilled(),this);
        getServer().getPluginManager().registerEvents(new PlayerCraftHead(),this);
        getServer().getPluginManager().registerEvents(new Place(),this);

        Logger.Log("Loading data");
        PlayerDataManager.load();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        ItemManager.setPlugin(this);
        MenuManager.setPlugin(this);

        Logger.Success("Plugin Started!");
    }

    @Override
    public void onDisable() {
        Logger.Info("Plugin Stopping");

        Logger.Log("Saving data");
        PlayerDataManager.save();

        Logger.Success("Plugin Stopped!");
    }
}
