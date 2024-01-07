package me.efekos.headhuntsmp.events;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;
 
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.headhuntsmp.menu.items.PlayerHead;
import me.efekos.simpler.translation.TranslateManager;
import me.efekos.simpler.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PickupOwnHead implements Listener {
    @EventHandler
    public void onPlayerPickup(EntityPickupItemEvent e){
        if(e.getEntityType()!= EntityType.PLAYER)return; // entity is a player
        if(e.getItem().getItemStack().getType()!= Material.PLAYER_HEAD) return; // he picked up a head

        Player p = (Player) e.getEntity();
        ItemStack stack = e.getItem().getItemStack();
        ItemMeta meta = stack.getItemMeta();
        PersistentDataContainer container =meta.getPersistentDataContainer();

        if(!container.has(ItemManager.itemUuidKey, PersistentDataType.STRING)) return; // container has an id

        UUID id = UUID.fromString(container.get(ItemManager.itemUuidKey,PersistentDataType.STRING));

        if(!ItemManager.getItems().containsKey(id)) return; // there is an item with this id

        PlayerHead item = (PlayerHead) ItemManager.getItems().get(id);

        if(item.getOwner() != p.getUniqueId()) return; // head is his

        PlayerData data = PlayerDataManager.fetch(p.getUniqueId());

        data.setRemainingHeads(data.getRemainingHeads()+stack.getAmount());

        PlayerDataManager.update(data.getUuid(),data);

        p.sendMessage(TranslateManager.translateColors(GameConfig.get().getString("messages.new-heads").replace("%added%", stack.getAmount() + "").replace("%new%", data.getRemainingHeads() + "")));

        new BukkitRunnable(){
            @Override
            public void run() {
                p.getInventory().remove(stack);
            }
        }.runTaskLater(HeadHuntSMP.getPlugin(),1);
    }
}
