package me.efekos.headhuntsmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void Log(String msg){
        Bukkit.getConsoleSender().sendMessage("[HeadHuntSMPOnline] " + msg);
    }
    public static void Error(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadHuntSMPOnline] [ERROR] "+msg);
    }
    public static void Info(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[HeadHuntSMPOnline] [INFO] "+msg);
    }
    public static void Success(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HeadHuntSMPOnline] [SUCCESS] "+msg);
    }
    public static void Warn(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[HeadHuntSMPOnline] [WARNING] "+msg);
    }
}
