package me.hollow.trollgod.client.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.trollgod.TrollGod;
import me.hollow.trollgod.api.util.MessageUtil;
import me.hollow.trollgod.client.command.Command;
import me.hollow.trollgod.client.command.commands.FriendCommand;
import me.hollow.trollgod.client.managers.FriendManager;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import sun.security.jca.GetInstance;

import java.util.*;

@ModuleManifest(label="PearlNotify", category=Module.Category.MISC, color=-16764673)
public class PearlNotify
extends Module {
    private final HashMap<EntityPlayer, UUID> list = new HashMap();
    private Entity enderPearl;
    private boolean flag;

    @Override
    public void onEnable() {
        flag = true;
    }

    @Override
    public void onUpdate() {
        if (this.mc.world == null || this.mc.player == null) {
            return;
        }
        enderPearl = null;
        for (final Entity e : this.mc.world.loadedEntityList) {
            if (e instanceof EntityEnderPearl) {
                enderPearl = e;
                break;
            }
        }
        if (enderPearl == null) {
            flag = true;
            return;
        }
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer entity : this.mc.world.playerEntities) {
            if (closestPlayer == null) {
                closestPlayer = entity;
            } else {
                if (closestPlayer.getDistance(enderPearl) <= entity.getDistance(enderPearl)) {
                    continue;
                }
                closestPlayer = entity;
            }
        }
        if (closestPlayer == this.mc.player) {
            flag = false;
        }
        if (closestPlayer != null && flag) {
            String faceing = enderPearl.getHorizontalFacing().toString();
            if (faceing.equals("west")) {
                faceing = "east";
            } else if (faceing.equals("east")) {
                faceing = "west";
            }
            MessageUtil.sendClientMessage(TrollGod.INSTANCE.getFriendManager().isFriend(closestPlayer.getName()) ? (ChatFormatting.AQUA + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!") : (ChatFormatting.RED + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!"), true);
            flag = false;
        }
    }
}