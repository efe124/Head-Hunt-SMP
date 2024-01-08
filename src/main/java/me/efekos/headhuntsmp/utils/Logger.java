package me.efekos.headhuntsmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(String msg) {
        Bukkit.getConsoleSender().sendMessage("[HeadHuntSMPOnline] " + msg);
    }

    public static void error(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadHuntSMPOnline] [ERROR] " + msg);
    }

    public static void info(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[HeadHuntSMPOnline] [INFO] " + msg);
    }

    public static void success(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HeadHuntSMPOnline] [SUCCESS] " + msg);
    }

    public static void warn(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[HeadHuntSMPOnline] [WARNING] " + msg);
    }
}
