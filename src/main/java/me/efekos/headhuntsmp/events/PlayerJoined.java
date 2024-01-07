package me.efekos.headhuntsmp.events;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoined implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        PlayerData data = HeadHuntSMP.PLAYER_DATA.get(player.getUniqueId());
        if(data==null)HeadHuntSMP.PLAYER_DATA.add(new PlayerData(10,player.getUniqueId(),player.getName()));
        else {
            data.setName(player.getName());
            HeadHuntSMP.PLAYER_DATA.update(data.getUniqueId(),data);
        }
    }
}
