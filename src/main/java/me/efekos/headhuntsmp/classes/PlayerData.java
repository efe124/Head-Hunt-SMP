package me.efekos.headhuntsmp.classes;

import java.util.UUID;

public class PlayerData {
    Integer remainingHeads;
    UUID uuid;
    String name;

    public PlayerData(Integer remainingHeads, UUID uuid, String name) {
        this.remainingHeads = remainingHeads;
        this.uuid = uuid;
        this.name = name;
    }

    public Integer getRemainingHeads() {
        return remainingHeads;
    }

    public void setRemainingHeads(Integer remainingHeads) {
        this.remainingHeads = remainingHeads;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
