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
import appeng.menu.me.common.GridInventoryEntry;

@Mixin(targets = "appeng.client.gui.me.common.Repo", remap = false)
public abstract class RepoMixin {
    @Inject(method = "getComparator", at = @At("HEAD"), cancellable = true)
    private void ae2terminalsort$getComparator(SortOrder sortOrder, SortDir sortDir,
            CallbackInfoReturnable<Comparator<? super GridInventoryEntry>> cir) {
        if (CreativeSortOrder.isCreative(sortOrder)) {
            cir.setReturnValue(Comparator.comparing(GridInventoryEntry::getWhat,
                    AE2TerminalSortConfig.enableCreativeSortOrder()
                            ? CreativeSortOrder.getComparator(sortDir)
                            : CreativeSortOrder.getNameComparator(sortDir)));
        } else if (CreativeSortOrder.isJei(sortOrder)) {
            cir.setReturnValue(Comparator.comparing(GridInventoryEntry::getWhat,
                    AE2TerminalSortConfig.enableJeiSortOrder() && CreativeSortOrder.isJeiAvailable()
                            ? CreativeSortOrder.getJeiComparator(sortDir)
                            : CreativeSortOrder.getNameComparator(sortDir)));
        } else if (sortOrder == SortOrder.NAME && AE2TerminalSortConfig.optimizeNameSort()) {
            cir.setReturnValue(Comparator.comparing(GridInventoryEntry::getWhat,
                    CreativeSortOrder.getNameComparator(sortDir)));
        } else if (sortOrder == SortOrder.MOD && AE2TerminalSortConfig.optimizeModSort()) {
            cir.setReturnValue(Comparator.comparing(GridInventoryEntry::getWhat,
                    CreativeSortOrder.getModComparator(sortDir)));
        }
    }
}
