package me.efekos.headhuntsmp.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class HashMapUtils {
    public static void storeToPersistentDataContainer(HashMap<String, String> map, PersistentDataContainer container, JavaPlugin plugin) {
        map.keySet().forEach(s -> {
            container.set(new NamespacedKey(plugin, s), PersistentDataType.STRING, map.get(s));
        });
    }
}
