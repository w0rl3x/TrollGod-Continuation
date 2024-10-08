package me.hollow.trollgod.client.modules.client;

import me.hollow.trollgod.api.property.Setting;
import me.hollow.trollgod.client.events.UpdateEvent;
import me.hollow.trollgod.client.gui.TrollGui;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;
import net.minecraft.client.settings.GameSettings;
import tcb.bces.listener.Subscribe;

import java.awt.*;

@ModuleManifest(label="ClickGui", category=Module.Category.CLIENT, key=157)
public class ClickGui
extends Module {
    public final Setting<String> prefix = this.register( new Setting <> ( "Prefix" , "." ));
    public final Setting<Boolean> customFov = this.register( new Setting <> ( "Custom Fov" , false ));
    public final Setting<Float> fov = this.register( new Setting <> ( "Fov" , 150.0f , - 180.0f , 180.0f ));
    public final Setting<Integer> red = this.register( new Setting <> ( "Red" , 255 , 0 , 255 ));
    public final Setting<Integer> green = this.register( new Setting <> ( "Green" , 255 , 0 , 255 ));
    public final Setting<Integer> blue = this.register( new Setting <> ( "Blue" , 255 , 0 , 255 ));
    public final Setting<Integer> hoverAlpha = this.register( new Setting <> ( "Hover Alpha" , 60 , 0 , 255 ));
    public final Setting<Integer> enabledAlpha = this.register( new Setting <> ( "Enabled Alpha" , 60 , 0 , 255 ));
    public final Setting<Integer> categoryRed = this.register( new Setting <> ( "Category Red" , 255 , 0 , 255 ));
    public final Setting<Integer> categoryGreen = this.register( new Setting <> ( "Category Green" , 255 , 0 , 255 ));
    public final Setting<Integer> categoryBlue = this.register( new Setting <> ( "Category Blue" , 255 , 0 , 255 ));
    public final Setting<Integer> categoryAlpha = this.register( new Setting <> ( "Category Alpha" , 40 , 0 , 255 ));
    public final Setting<Integer> alpha = this.register( new Setting <> ( "Alpha" , 40 , 0 , 255 ));
    private static ClickGui INSTANCE;

    public ClickGui() {
        INSTANCE = this;
    }

    public static ClickGui getInstance() {
        return INSTANCE;
    }

    public final int getColor(boolean hover) {
        return new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), hover ? this.hoverAlpha.getValue ( ) : this.enabledAlpha.getValue ( ) ).getRGB();
    }

    @Subscribe
    public void onTick(UpdateEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (!(this.mc.currentScreen instanceof TrollGui)) {
            this.setEnabled(false);
        }
        if ( this.customFov.getValue ( ) ) {
            this.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue ( ) );
        }
    }

    @Override
    public void onEnable() {
        if (this.mc.player != null) {
            this.mc.displayGuiScreen( new TrollGui() );
        }
    }

    @Override
    public void onDisable() {
        if (this.mc.currentScreen instanceof TrollGui) {
            this.mc.displayGuiScreen(null);
        }
    }
}

