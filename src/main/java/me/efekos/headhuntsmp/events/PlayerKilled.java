package me.efekos.headhuntsmp.events;

import me.efekos.headhuntsmp.classes.PlayerData;
import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.headhuntsmp.menu.items.PlayerHead;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.items.ItemManager;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerKilled implements Listener {
    @EventHandler
    public void onPlayerDied(PlayerDeathEvent e){
        if(e.getEntity().getKiller()==null)return;

        Player killer = e.getEntity().getKiller();
        Player p = e.getEntity();

        PlayerHead head = new PlayerHead(p);
        ItemManager.giveItem(killer,head);

        PlayerData data = PlayerDataManager.fetch(p);
        data.setRemainingHeads(data.getRemainingHeads()-1);
        PlayerDataManager.update(data.getUuid(),data);

        if(data.getRemainingHeads() == 0){
            ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwningPlayer(p);
            stack.setItemMeta(meta);

            e.getDrops().add(stack);

            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(p.getName(), TranslateManager.translateColors(GameConfig.get().getString("messages.no-heads")), null, "HeadHuntSMP");
            p.kickPlayer(TranslateManager.translateColors(GameConfig.get().getString("messages.no-heads")));
        } else
        p.sendMessage(TranslateManager.translateColors(GameConfig.get().getString("messages.left-heads").replace("%count%",data.getRemainingHeads()+"")));
    }
}
