package me.efekos.headhuntsmp.menu.items;

import me.efekos.headhuntsmp.classes.ColorTranslator;
import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.simpler.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.ietf.jgss.GSSManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class PlayerHead extends CustomItem {

    private UUID owner;

    public PlayerHead(Player owner){
        this(owner.getUniqueId());
    }

    public PlayerHead(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {

    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
    }

    @Override
    public void onDrop(PlayerDropItemEvent event) {

    }

    @Override
    public void onPickup(EntityPickupItemEvent event) {

    }

    @Override
    public @NotNull String getId() {
        return "head";
    }

    @Override
    public @NotNull ItemMeta getDefaultMeta() {
        OfflinePlayer p = Bukkit.getOfflinePlayer(owner);

        SkullMeta meta = (SkullMeta) new ItemStack(getMaterial()).getItemMeta();
        meta.setDisplayName(ColorTranslator.translateColorCodes(GameConfig.get().getString("messages.head.name")));
        meta.setLore(Arrays.asList(ColorTranslator.translateColorCodes(GameConfig.get().getString("messages.head.own").replace("%player%", p.getName()))));
        meta.setOwningPlayer(p);
        return meta;
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.PLAYER_HEAD;
    }
}
