package me.hollow.trollgod.client.events;

import net.minecraft.entity.player.EntityPlayer;
import tcb.bces.event.Event;

import java.util.UUID;

public class ConnectionEvent
extends Event {
    private final int stage;
    private final UUID uuid;
    private final EntityPlayer entity;
    private final String name;

    public ConnectionEvent(int stage, UUID uuid, String name) {
        this.stage = stage;
        this.uuid = uuid;
        this.name = name;
        this.entity = null;
    }

    public ConnectionEvent(int stage, EntityPlayer entity, UUID uuid, String name) {
        this.stage = stage;
        this.entity = entity;
        this.uuid = uuid;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getStage() {
        return this.stage;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public EntityPlayer getEntity() {
        return this.entity;
    }
}

