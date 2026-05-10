package com.mx703.ae2terminalsort.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.resources.ResourceLocation;

import com.mx703.ae2terminalsort.AE2TerminalSort;
import com.mx703.ae2terminalsort.CreativeSortIcon;

import appeng.client.gui.Icon;
import appeng.client.gui.style.Blitter;

@Mixin(value = Icon.class, remap = false)
public abstract class IconMixin {
    private static final ResourceLocation CREATIVE_SORT_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AE2TerminalSort.MOD_ID,
            "textures/guis/creative_sort.png");
    private static final ResourceLocation JEI_SORT_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AE2TerminalSort.MOD_ID,
            "textures/guis/jei_sort.png");

    @Shadow
    @Final
    @Mutable
    private static Icon[] $VALUES;

    @Invoker("<init>")
    private static Icon ae2terminalsort$create(String name, int ordinal, int x, int y, int width, int height) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void ae2terminalsort$addCreativeSortIcon(CallbackInfo ci) {
        var values = Arrays.copyOf($VALUES, $VALUES.length + 2);
        CreativeSortIcon.ICON = ae2terminalsort$create("AE2TERMINALSORT_CREATIVE_SORT", values.length - 2, 0, 0, 16,
                16);
        CreativeSortIcon.JEI_ICON = ae2terminalsort$create("AE2TERMINALSORT_JEI_SORT", values.length - 1, 0, 0, 16,
                16);
        values[values.length - 2] = CreativeSortIcon.ICON;
        values[values.length - 1] = CreativeSortIcon.JEI_ICON;
        $VALUES = values;
    }

    @Inject(method = "getBlitter", at = @At("HEAD"), cancellable = true)
    private void ae2terminalsort$getCreativeSortBlitter(CallbackInfoReturnable<Blitter> cir) {
        if (CreativeSortIcon.isCreativeSortIcon((Icon) (Object) this)) {
            cir.setReturnValue(Blitter.texture(CREATIVE_SORT_TEXTURE, 16, 16).src(0, 0, 16, 16));
        } else if (CreativeSortIcon.isJeiSortIcon((Icon) (Object) this)) {
            cir.setReturnValue(Blitter.texture(JEI_SORT_TEXTURE, 16, 16).src(0, 0, 16, 16));
        }
    }
}
