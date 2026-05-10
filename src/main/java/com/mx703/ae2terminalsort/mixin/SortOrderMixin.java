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

import com.mx703.ae2terminalsort.CreativeSortOrder;

import appeng.api.config.SortOrder;

@Mixin(value = SortOrder.class, remap = false)
public abstract class SortOrderMixin {
    @Shadow
    @Final
    @Mutable
    private static SortOrder[] $VALUES;

    @Invoker("<init>")
    private static SortOrder ae2terminalsort$create(String name, int ordinal) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void ae2terminalsort$addCreativeSortOrder(CallbackInfo ci) {
        var values = Arrays.copyOf($VALUES, $VALUES.length + 2);
        CreativeSortOrder.CREATIVE = ae2terminalsort$create("CREATIVE", values.length - 1);
        CreativeSortOrder.JEI = ae2terminalsort$create("JEI", values.length - 2);
        values[values.length - 2] = CreativeSortOrder.JEI;
        values[values.length - 1] = CreativeSortOrder.CREATIVE;
        $VALUES = values;
    }
}
