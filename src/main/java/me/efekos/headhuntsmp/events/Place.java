package me.efekos.headhuntsmp.events;

import me.efekos.headhuntsmp.menu.Unbanner;
import me.efekos.headhuntsmp.utils.AnchorRecipeManager;
import me.efekos.headhuntsmp.utils.Logger;
import me.efekos.simpler.items.ItemManager;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class Place implements Listener {
    @EventHandler
    public void onPlaceHead(BlockPlaceEvent e){
        ItemStack stack = e.getItemInHand();

        if(Objects.equals(stack.getItemMeta().getPersistentDataContainer().get(ItemManager.itemTypeKey, PersistentDataType.STRING), "head")) e.setCancelled(true);
    }

    @EventHandler
    public void onPlaceAnchor(BlockPlaceEvent e){
        Logger.info("a");
        ItemStack stack = e.getItemInHand();

        if(stack.getItemMeta().getPersistentDataContainer().has(AnchorRecipeManager.namespace,PersistentDataType.STRING)){
            MenuData data = MenuManager.getMenuData(e.getPlayer());

            data.set("blockLocation",e.getBlock().getLocation());

            MenuManager.Open(e.getPlayer(), Unbanner.class);
        }
    }
}
