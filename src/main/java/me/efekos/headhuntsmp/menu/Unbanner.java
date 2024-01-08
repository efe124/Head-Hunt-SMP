package me.efekos.headhuntsmp.menu;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;
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
import org.bukkit.profile.PlayerProfile;

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
        return HeadHuntSMP.gameConfig.getString("messages.unban.title", "Unbanner");
    }

    private boolean gonnaGive = true;

    @Override
    public void onClick(InventoryClickEvent event) {
        switch (event.getCurrentItem().getType()) {
            case BARRIER:
                event.getWhoClicked().closeInventory();
                break;
            case PLAYER_HEAD:
                SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
                PlayerData deadData = HeadHuntSMP.PLAYER_DATA.get(meta.getOwningPlayer().getUniqueId());
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
        loc.getWorld().playSound(loc, Sound.BLOCK_BEACON_DEACTIVATE, SoundCategory.PLAYERS, 100, 1);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc.add(.5, 0, .5), 100, .6, .6, .6, new Particle.DustOptions(Color.BLUE, 3.0F));

        if (gonnaGive) event.getPlayer().getInventory().addItem(AnchorRecipeManager.createAnchor());
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void fill() {
        inventory.setItem(49, createItem(Material.BARRIER, TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("messages.unban.close", "&cClose"))));

        for (BanEntry<PlayerProfile> entry : Bukkit.getBanList(BanList.Type.PROFILE).getBanEntries()) {
            PlayerData data = HeadHuntSMP.PLAYER_DATA.get(entry.getBanTarget().getUniqueId());

            ItemStack stack = createItem(Material.PLAYER_HEAD, data.getName());
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(data.getUniqueId()));
            stack.setItemMeta(meta);

            inventory.addItem(stack);
        }

    }
}
