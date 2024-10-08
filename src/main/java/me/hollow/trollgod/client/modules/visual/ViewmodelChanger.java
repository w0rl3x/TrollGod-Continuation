package me.hollow.trollgod.client.modules.visual;

import me.hollow.trollgod.api.property.Setting;
import me.hollow.trollgod.client.modules.Module;
import me.hollow.trollgod.client.modules.ModuleManifest;

@ModuleManifest(label="ModelChanger", listen=false, category=Module.Category.VISUAL, color=-16558593)
public class ViewmodelChanger
extends Module {
    public final Setting<Boolean> pauseOnEat = this.register( new Setting <> ( "Pause" , true ));
    public final Setting<Float> translateX = this.register( new Setting <> ( "X" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> translateY = this.register( new Setting <> ( "Y" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> translateZ = this.register( new Setting <> ( "Z" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> rotateX = this.register( new Setting <> ( "Rotate X" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> rotateY = this.register( new Setting <> ( "Rotate Y" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> rotateZ = this.register( new Setting <> ( "Rotate Z" , 0.0f , - 5.0f , 5.0f ));
    public final Setting<Float> scale = this.register( new Setting <> ( "Scale" , 10.0f , 9.0f , 10.0f ));
    public static ViewmodelChanger INSTANCE;

    public ViewmodelChanger() {
        INSTANCE = this;
    }
}

