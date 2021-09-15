package me.hollow.trollgod.client.modules;

import me.hollow.trollgod.api.property.Setting;

@ModuleManifest(label="ModuleTemplate", category=Module.Category.CLIENT, color=-16764673)
public class ModuleTemplate
extends Module {
    private final Setting<Boolean> bln = this.register( new Setting <> ( "bln" , false ));
    private final Setting<Float> flt = this.register( new Setting <> ( "flt" , 5.0f , - 5.0f , 5.0f ));
    private static ModuleTemplate INSTANCE;

    public ModuleTemplate() {
        INSTANCE = this;
    }

    public static ModuleTemplate getInstance() {
        return INSTANCE;
    }




}