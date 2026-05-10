package com.mx703.ae2terminalsort.mixin;

import java.util.EnumSet;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.network.chat.Component;

import com.mx703.ae2terminalsort.AE2TerminalSortConfig;
import com.mx703.ae2terminalsort.CreativeSortIcon;
import com.mx703.ae2terminalsort.CreativeSortOrder;

import appeng.api.config.Setting;
import appeng.api.config.Settings;
import appeng.api.config.SortOrder;
import appeng.client.gui.Icon;
import appeng.client.gui.widgets.SettingToggleButton;
import appeng.core.localization.ButtonToolTips;
import appeng.util.EnumCycler;

@Mixin(value = SettingToggleButton.class, remap = false)
public abstract class SettingToggleButtonMixin<T extends Enum<T>> {
    @Shadow
    @Final
    private EnumSet<T> validValues;

    @Shadow
    @Final
    private Setting<T> buttonSetting;

    @Shadow
    private T currentValue;

    @Invoker("registerApp")
    private static <E extends Enum<E>> void ae2terminalsort$registerApp(Icon icon, Setting<E> setting, E value,
            ButtonToolTips title, Component... tooltipLines) {
        throw new AssertionError();
    }

    @Inject(method = "<init>(Lappeng/api/config/Setting;Ljava/lang/Enum;Ljava/util/function/Predicate;Lappeng/client/gui/widgets/SettingToggleButton$IHandler;)V", at = @At("TAIL"))
    private void ae2terminalsort$registerCreativeSortAppearance(Setting<T> setting, T value, Predicate<T> isValidValue,
            SettingToggleButton.IHandler<SettingToggleButton<T>> onPress, CallbackInfo ci) {
        if (CreativeSortOrder.CREATIVE != null) {
            ae2terminalsort$registerApp(
                    CreativeSortIcon.ICON,
                    Settings.SORT_BY,
                    CreativeSortOrder.CREATIVE,
                    ButtonToolTips.SortBy,
                    Component.translatable("gui.tooltips.ae2terminalsort.CreativeOrder"));
            if (!AE2TerminalSortConfig.enableCreativeSortOrder()) {
                validValues.remove(CreativeSortOrder.CREATIVE);
            }
        }
        if (CreativeSortOrder.JEI != null) {
            ae2terminalsort$registerApp(
                    CreativeSortIcon.JEI_ICON,
                    Settings.SORT_BY,
                    CreativeSortOrder.JEI,
                    ButtonToolTips.SortBy,
                    Component.translatable("gui.tooltips.ae2terminalsort.JeiOrder"));
            if (!AE2TerminalSortConfig.enableJeiSortOrder() || !CreativeSortOrder.isJeiAvailable()) {
                validValues.remove(CreativeSortOrder.JEI);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "getNextValue", at = @At("HEAD"), cancellable = true)
    private void ae2terminalsort$getNextValue(boolean backwards, CallbackInfoReturnable<T> cir) {
        if (buttonSetting == Settings.SORT_BY && CreativeSortOrder.CREATIVE != null && CreativeSortOrder.JEI != null) {
            var values = EnumSet.copyOf(validValues);
            if (AE2TerminalSortConfig.enableCreativeSortOrder()) {
                values.add((T) CreativeSortOrder.CREATIVE);
            } else {
                values.remove(CreativeSortOrder.CREATIVE);
            }
            if (AE2TerminalSortConfig.enableJeiSortOrder() && CreativeSortOrder.isJeiAvailable()) {
                values.add((T) CreativeSortOrder.JEI);
            } else {
                values.remove(CreativeSortOrder.JEI);
            }
            cir.setReturnValue(EnumCycler.rotateEnum(currentValue, backwards, values));
        }
    }
}
