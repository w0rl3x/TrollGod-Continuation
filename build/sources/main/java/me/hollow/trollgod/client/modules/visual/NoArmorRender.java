package me.hollow.trollgod.client.modules.visual;

import me.hollow.trollgod.api.property.Setting;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;

@ModuleManifest(label="NoArmorRender", listen=false, category=Module.Category.VISUAL, color=39168)
public class NoArmorRender
extends Module {
    public final Setting<Boolean> helmet = this.register( new Setting <> ( "Helmet" , true ));
    public final Setting<Boolean> chestplate = this.register( new Setting <> ( "Chestplate" , true ));
    public final Setting<Boolean> thighHighs = this.register( new Setting <> ( "Leggings" , true ));
    public final Setting<Boolean> boots = this.register( new Setting <> ( "Boots" , true ));
    public static NoArmorRender INSTANCE;

    public NoArmorRender() {
        INSTANCE = this;
    }
}

