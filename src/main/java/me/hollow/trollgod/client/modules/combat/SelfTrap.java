package me.hollow.trollgod.client.modules.combat;

import me.hollow.trollgod.api.property.Setting;
import me.hollow.trollgod.api.util.BlockUtil;
import me.hollow.trollgod.api.util.ItemUtil;
import me.hollow.trollgod.api.util.Timer;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.*;

@ModuleManifest(label="SelfTrap", category=Module.Category.COMBAT, listen=false)
public class SelfTrap
extends Module {
    private final Setting<Integer> blocksPerTick = this.register( new Setting <> ( "BlocksPerTick" , 8 , 1 , 20 ));
    private final Setting<Integer> delay = this.register( new Setting <> ( "Delay" , 50 , 0 , 250 ));
    private final Setting<Integer> disableTime = this.register( new Setting <> ( "DisableTime" , 200 , 50 , 300 ));
    private final Setting<Boolean> disable = this.register( new Setting <> ( "AutoDisable" , true ));
    private final Timer offTimer = new Timer();
    private final Timer timer = new Timer();
    private final Map<BlockPos, Integer> retries = new HashMap <> ( );
    private int blocksThisTick = 0;
    private int obbySlot = -1;

    @Override
    public void onEnable() {
        if (this.isNull()) {
            this.disable();
        }
        this.offTimer.reset();
    }

    @Override
    public void onDisable() {
        this.retries.clear();
    }

    @Override
    public void onUpdate() {
        if (this.check()) {
            return;
        }
        for (BlockPos position : this.getPositions()) {
            if (BlockUtil.isPositionPlaceable(position, false) != 3) continue;
            this.placeBlock(position);
        }
    }

    private List<BlockPos> getPositions() {
        ArrayList<BlockPos> positions = new ArrayList <> ( );
        positions.add(new BlockPos(this.mc.player.posX, this.mc.player.posY + 2.0, this.mc.player.posZ));
        int placeability = BlockUtil.isPositionPlaceable( positions.get(0) , false);
        switch (placeability) {
            case 0: {
                return new ArrayList <> ( );
            }
            case 3: {
                return positions;
            }
            case 1: {
                if (BlockUtil.isPositionPlaceable( positions.get(0) , false) == 3) {
                    return positions;
                }
            }
            case 2: {
                positions.add(new BlockPos(this.mc.player.posX + 1.0, this.mc.player.posY + 1.0, this.mc.player.posZ));
                positions.add(new BlockPos(this.mc.player.posX + 1.0, this.mc.player.posY + 2.0, this.mc.player.posZ));
            }
        }
        positions.sort(Comparator.comparingDouble(Vec3i::getY));
        return positions;
    }

    private void placeBlock(BlockPos pos) {
        if (this.blocksThisTick < this.blocksPerTick.getValue()) {
            int lastSlot = this.mc.player.inventory.currentItem;
            this.obbySlot = ItemUtil.getBlockFromHotbar(Blocks.OBSIDIAN);
            this.mc.player.inventory.currentItem = this.obbySlot;
            Objects.requireNonNull ( this.mc.getConnection ( ) ).sendPacket( new CPacketHeldItemChange(this.obbySlot) );
            BlockUtil.placeBlock(pos);
            mc.player.inventory.currentItem = lastSlot;
            this.timer.reset();
            ++this.blocksThisTick;
        }

    }

    private boolean check() {
        if (this.isNull()) {
            this.disable();
            return true;
        }
        this.blocksThisTick = 0;
        if ( this.disable.getValue ( ) && this.offTimer.hasReached( this.disableTime.getValue ( ) )) {
            this.disable();
            return true;
        }
        return !this.timer.hasReached( this.delay.getValue ( ) );
    }
}

