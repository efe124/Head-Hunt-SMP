package me.efekos.headhuntsmp.menu.items;

import me.efekos.headhuntsmp.classes.PlayerData;
import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TransferQueue;

public class ExtraHead extends CustomItem {
    @Override
    public void onLeftClick(PlayerInteractEvent event) {

    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.fetch(player);
        if(player.isSneaking()){
            data.setRemainingHeads(data.getRemainingHeads()+stack.getAmount());

            player.sendMessage(TranslateManager.translateColors(GameConfig.get().getString("messages.new-heads").replace("%added%",stack.getAmount()+"").replace("%new%",data.getRemainingHeads()+"")));

            stack.setType(Material.AIR);
        } else {
            data.setRemainingHeads(data.getRemainingHeads()+1);

            player.sendMessage(TranslateManager.translateColors(GameConfig.get().getString("messages.new-heads").replace("%added%","1").replace("%new%",data.getRemainingHeads()+"")));

            stack.setAmount(stack.getAmount()-1);
        }
        PlayerDataManager.update(data.getUuid(),data);
    }

    @Override
    public void onDrop(PlayerDropItemEvent event) {

    }

    @Override
    public void onPickup(EntityPickupItemEvent event) {

    }

    @Override
    public @NotNull String getId() {
        return "extrahead";
    }

    @Override
    public @NotNull ItemMeta getDefaultMeta() {
        ItemMeta meta = new ItemStack(getMaterial()).getItemMeta();

        meta.setDisplayName(TranslateManager.translateColors(Objects.requireNonNull(GameConfig.get().getString("extra-head.name"))));
        meta.setLore(Arrays.asList(TranslateManager.translateColors(Objects.requireNonNull(GameConfig.get().getString("extra-head.description")))));

        return meta;
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.PLAYER_HEAD;
    }
}
