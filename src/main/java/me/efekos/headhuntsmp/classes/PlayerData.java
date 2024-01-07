package me.efekos.headhuntsmp.classes;

import me.efekos.simpler.config.Storable;

import java.util.UUID;

public class PlayerData implements Storable {
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

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    public void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
