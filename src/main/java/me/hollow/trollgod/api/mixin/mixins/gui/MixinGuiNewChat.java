package me.hollow.trollgod.api.mixin.mixins.gui;

import me.hollow.trollgod.api.util.MathUtil;
import me.hollow.trollgod.api.util.font.RainbowText;
import me.hollow.trollgod.client.modules.client.Manage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value={GuiNewChat.class})
public abstract class MixinGuiNewChat
        extends Gui {
    @Shadow
    private boolean isScrolled;
    private float percentComplete;
    private int newLines;
    private long prevMillis = System.currentTimeMillis();
    private boolean configuring;
    private float animationPercent;
    private int lineBeingDrawn;

    @Shadow
    public abstract float getChatScale();

    private void updatePercentage(long diff) {
        if (this.percentComplete < 1.0f) {
            this.percentComplete += 0.004f * (float) diff;
        }
        this.percentComplete = MathUtil.clamp(this.percentComplete, 0.0f, 1.0f);
    }

    @Inject(method = {"drawChat"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void modifyChatRendering(CallbackInfo ci) {
        if (this.configuring) {
            ci.cancel();
            return;
        }
        long current = System.currentTimeMillis();
        long diff = current - this.prevMillis;
        this.prevMillis = current;
        this.updatePercentage(diff);
        float t = this.percentComplete;
        this.animationPercent = MathUtil.clamp(1.0f - (t -= 1.0f) * t * t * t, 0.0f, 1.0f);
    }

    @Inject(method = {"drawChat"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void translate(CallbackInfo ci) {
        float y = 1.0f;
        if (!this.isScrolled) {
            y += (9.0f - 9.0f * this.animationPercent) * this.getChatScale();
        }
        GlStateManager.translate((float) 0.0f, (float) y, (float) 0.0f);
    }

    @ModifyArg(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 0, remap = false), index = 0)
    private int getLineBeingDrawn(int line) {
        this.lineBeingDrawn = line;
        return line;
    }

    @ModifyArg(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"), index = 3)
    private int modifyTextOpacity(int original) {
        if (this.lineBeingDrawn <= this.newLines) {
            int opacity = original >> 24 & 0xFF;
            opacity = (int) ((float) opacity * this.animationPercent);
            return original & 0xFFFFFF | opacity << 24;
        }
        return original;
    }

    @Inject(method = {"printChatMessageWithOptionalDeletion"}, at = {@At(value = "HEAD")})
    private void resetPercentage(CallbackInfo ci) {
        this.percentComplete = 0.0f;
    }

    @ModifyVariable(method = {"setChatLine"}, at = @At(value = "STORE"), ordinal = 0)
    private List<ITextComponent> setNewLines(List<ITextComponent> original) {
        this.newLines = original.size() - 1;
        return original;
    }

    @ModifyVariable(method = {"getChatComponent"}, at = @At(value = "STORE", ordinal = 0), ordinal = 3)
    private int modifyX(int original) {
        return original - 0;
    }

    @ModifyVariable(method = {"getChatComponent"}, at = @At(value = "STORE", ordinal = 0), ordinal = 4)
    private int modifyY(int original) {
        return original + 1;
    }

}

