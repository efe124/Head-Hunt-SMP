package me.efekos.headhuntsmp.events;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;

import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PlayerCraftHead implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        try{
            if(e.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(HeadHuntSMP.getPlugin(),"extra-head"), PersistentDataType.STRING)){

                ItemStack stack = e.getItem();
                Player player = e.getPlayer();
                PlayerData data = HeadHuntSMP.PLAYER_DATA.get(player.getUniqueId());
                if(player.isSneaking()){
                    data.setRemainingHeads(data.getRemainingHeads()+stack.getAmount());

                    player.sendMessage(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("messages.new-heads","&aAdded &2%added% &anew heads! You have &2%new% &aheads now.").replace("%added%",stack.getAmount()+"").replace("%new%",data.getRemainingHeads()+"")));

                    stack.setAmount(stack.getAmount()-stack.getAmount());
                } else {
                    data.setRemainingHeads(data.getRemainingHeads()+1);

                    player.sendMessage(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("messages.new-heads","&aAdded &2%added% &anew heads! You have &2%new% &aheads now.").replace("%added%","1").replace("%new%",data.getRemainingHeads()+"")));

                    stack.setAmount(stack.getAmount()-1);
                }
                HeadHuntSMP.PLAYER_DATA.update(data.getUniqueId(),data);
            }
        } catch (Exception ignored){}
    }
}
