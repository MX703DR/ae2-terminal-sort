package com.mx703.ae2terminalsort;

import appeng.client.gui.Icon;

public final class CreativeSortIcon {
    public static Icon ICON;
    public static Icon JEI_ICON;

    private CreativeSortIcon() {
    }

    public static boolean isCreativeSortIcon(Icon icon) {
        return icon != null && "AE2TERMINALSORT_CREATIVE_SORT".equals(icon.name());
    }

    public static boolean isJeiSortIcon(Icon icon) {
        return icon != null && "AE2TERMINALSORT_JEI_SORT".equals(icon.name());
    }
}
