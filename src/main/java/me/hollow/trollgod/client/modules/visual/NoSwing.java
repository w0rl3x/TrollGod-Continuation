package me.hollow.trollgod.client.modules.visual;

import me.hollow.trollgod.api.property.Setting;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;
import me.hollow.trollgod.client.modules.combat.AutoCrystal;

@ModuleManifest(label="NoSwing", category= Module.Category.VISUAL, color=52224)
public class NoSwing
extends Module {
    private static NoSwing INSTANCE;

    public NoSwing() {
        INSTANCE = this;
    }

    public static NoSwing getInstance() {
        return INSTANCE;
    }
}