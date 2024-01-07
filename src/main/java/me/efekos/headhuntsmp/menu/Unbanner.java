package me.efekos.headhuntsmp.menu;

import me.efekos.headhuntsmp.classes.PlayerData;
import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.headhuntsmp.utils.AnchorRecipeManager;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Unbanner extends Menu {
    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    public Unbanner(MenuData data) {
        super(data);
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public String getTitle() {
        return GameConfig.get().getString("messages.unban.title");
    }

    private boolean gonnaGive = true;

    @Override
    public void onClick(InventoryClickEvent event) {
        switch (event.getCurrentItem().getType()){
            case BARRIER:
                event.getWhoClicked().closeInventory();
                break;
            case PLAYER_HEAD:
                SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
                PlayerData deadData = PlayerDataManager.get(meta.getOwningPlayer().getUniqueId());
                deadData.setRemainingHeads(10);
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(deadData.getName());
                gonnaGive = false;
                event.getWhoClicked().closeInventory();
                break;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Location loc = (Location) data.get("blockLocation");

        loc.getBlock().setType(Material.AIR);
        loc.getWorld().playSound(loc, Sound.BLOCK_BEACON_DEACTIVATE,SoundCategory.PLAYERS,100,1);
        loc.getWorld().spawnParticle(Particle.REDSTONE,loc.add(.5,0,.5),100,.6,.6,.6,new Particle.DustOptions(Color.BLUE,3.0F));

        if(gonnaGive) event.getPlayer().getInventory().addItem(AnchorRecipeManager.createAnchor());
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void fill() {
        inventory.setItem(49,createItem(Material.BARRIER, TranslateManager.translateColors(GameConfig.get().getString("messages.unban.close"))));

        for (BanEntry entry : Bukkit.getBanList(BanList.Type.NAME).getBanEntries()) {
            PlayerData data = PlayerDataManager.get(entry.getTarget());

            ItemStack stack = createItem(Material.PLAYER_HEAD,data.getName());
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(data.getUuid()));
            stack.setItemMeta(meta);

            inventory.addItem(stack);
        }

    }
}
