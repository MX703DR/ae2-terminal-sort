package com.mx703.ae2terminalsort.mixin;

import java.util.Comparator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mx703.ae2terminalsort.AE2TerminalSortConfig;
import com.mx703.ae2terminalsort.CreativeSortOrder;

import appeng.api.config.SortDir;
import appeng.api.config.SortOrder;
import appeng.api.stacks.AEKey;

@Mixin(targets = "appeng.client.gui.me.common.KeySorters", remap = false)
public abstract class KeySortersMixin {
    @Inject(method = "getComparator", at = @At("HEAD"), cancellable = true)
    private static void ae2terminalsort$getCreativeComparator(SortOrder order, SortDir dir,
            CallbackInfoReturnable<Comparator<AEKey>> cir) {
        if (CreativeSortOrder.isCreative(order)) {
            cir.setReturnValue(AE2TerminalSortConfig.enableCreativeSortOrder()
                    ? CreativeSortOrder.getComparator(dir)
                    : CreativeSortOrder.getNameComparator(dir));
        } else if (CreativeSortOrder.isJei(order)) {
            cir.setReturnValue(AE2TerminalSortConfig.enableJeiSortOrder() && CreativeSortOrder.isJeiAvailable()
                    ? CreativeSortOrder.getJeiComparator(dir)
                    : CreativeSortOrder.getNameComparator(dir));
        } else if (order == SortOrder.NAME && AE2TerminalSortConfig.optimizeNameSort()) {
            cir.setReturnValue(CreativeSortOrder.getNameComparator(dir));
        } else if (order == SortOrder.MOD && AE2TerminalSortConfig.optimizeModSort()) {
            cir.setReturnValue(CreativeSortOrder.getModComparator(dir));
        }
    }
}
