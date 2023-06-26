package me.efekos.headhuntsmp.files;

import com.google.gson.Gson;
import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;
import me.efekos.headhuntsmp.config.GameConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PlayerDataManager {
    private static ArrayList<PlayerData> datas = new ArrayList<>();

    public static void create(PlayerData newData){
        datas.add(newData);
        save();
    }

    public static PlayerData get(UUID id){
        for (PlayerData data:datas){
            if(data.getUuid().equals(id)) return data;
        }
        return null;
    }

    public static PlayerData fetch(Player player){
        return fetch(player.getUniqueId());
    }

    public static PlayerData get(OfflinePlayer player){
        return get(player.getName());
    }

    public static PlayerData fetch(UUID id){
        if(get(id) == null){
            Player p = Bukkit.getPlayer(id);

            assert p != null;
            int defheads = GameConfig.get().getInt("default-heads");
            create(new PlayerData(defheads!=0?defheads:9,id,p.getName()));
        }
        return get(id);
    }

    public static PlayerData get(String name){
        for(PlayerData data:datas){
            if(data.getName().equals(name))return data;
        }
        return null;
    }

    public static PlayerData fetch(String name){
        if(get(name)==null){
            Player p =Bukkit.getPlayer(name);

            assert p!=null;
            create(new PlayerData(9,p.getUniqueId(),p.getName()));
        }
        return get(name);
    }

    public static void update(UUID id,PlayerData newData){
        for (PlayerData data:datas){
            if(data.getUuid().equals(id)){
                data.setRemainingHeads(newData.getRemainingHeads());
                data.setName(newData.getName());
            }
        }
        save();
    }

    public static void save(){
        Gson gson = new Gson();

        String path = HeadHuntSMP.getPlugin().getDataFolder().getAbsolutePath()+"\\data\\PlayerData.json";

        File file = new File(path);
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            Writer writer = new FileWriter(file,false);
            gson.toJson(datas,writer);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void load(){

        Gson gson = new Gson();
        File file = new File(HeadHuntSMP.getPlugin().getDataFolder().getAbsolutePath()+"\\data\\PlayerData.json");
        if(file.exists()){
            try {
                Reader reader = new FileReader(file);
                PlayerData[] n = gson.fromJson(reader,PlayerData[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (Exception e){
                e.getCause();
            }
        }

    }

    public static ArrayList<PlayerData> getAll() {
        return datas;
    }
}