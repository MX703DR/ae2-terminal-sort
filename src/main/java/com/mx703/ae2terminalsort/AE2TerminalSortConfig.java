package com.mx703.ae2terminalsort;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

public final class AE2TerminalSortConfig {
    public static final ModConfigSpec CLIENT_SPEC;
    private static final BooleanValue ENABLE_CREATIVE_SORT_ORDER;
    private static final BooleanValue ENABLE_JEI_SORT_ORDER;
    private static final BooleanValue OPTIMIZE_NAME_SORT;
    private static final BooleanValue OPTIMIZE_MOD_SORT;

    static {
        var builder = new ModConfigSpec.Builder();

        ENABLE_CREATIVE_SORT_ORDER = builder
            .translation("config.ae2terminalsort.enableCreativeSortOrder")
            .comment(
                "Enables the AE2 terminal sort option that follows the vanilla creative tab item order.",
                "启用 AE2 终端中按原版创造物品栏顺序排序的选项。")
            .define("enableCreativeSortOrder", true);
        ENABLE_JEI_SORT_ORDER = builder
            .translation("config.ae2terminalsort.enableJeiSortOrder")
            .comment(
                "Enables the AE2 terminal sort option that follows JEI's item list order.",
                "启用 AE2 终端中按 JEI 物品列表顺序排序的选项。",
                "This option is only shown when JEI is installed and its runtime is available.",
                "只有安装 JEI 且 JEI runtime 可用时才会显示这个选项。")
            .define("enableJeiSortOrder", true);
        OPTIMIZE_NAME_SORT = builder
            .translation("config.ae2terminalsort.optimizeNameSort")
            .comment(
                "Makes AE2 terminal name sorting use registry-name based ordering instead of item display names.",
                "让 AE2 终端的名称排序改用基于注册名的顺序，而不是物品显示名称。",
                "This improves responsiveness in terminals that store many NBT-heavy items.",
                "这会改善存有大量 NBT 物品的终端响应速度。",
                "If disabled, opening a terminal that stores many items may make the game stop responding.",
                "关闭后，打开存储大量物品的终端可能会直接导致游戏未响应。")
            .define("optimizeNameSort", true);
        OPTIMIZE_MOD_SORT = builder
            .translation("config.ae2terminalsort.optimizeModSort")
            .comment(
                "Makes AE2 terminal mod sorting use registry-name based ordering within each mod.",
                "让 AE2 终端的模组排序在同一模组内改用基于注册名的顺序。",
                "This improves responsiveness in terminals that store many NBT-heavy items.",
                "这会改善存有大量 NBT 物品的终端响应速度。",
                "If disabled, opening a terminal that stores many items may make the game stop responding.",
                "关闭后，打开存储大量物品的终端可能会直接导致游戏未响应。")
            .define("optimizeModSort", true);

        CLIENT_SPEC = builder.build();
    }

    private AE2TerminalSortConfig() {
    }

    public static boolean enableCreativeSortOrder() {
        return ENABLE_CREATIVE_SORT_ORDER.get();
    }

    public static boolean enableJeiSortOrder() {
        return ENABLE_JEI_SORT_ORDER.get();
    }

    public static boolean optimizeNameSort() {
        return OPTIMIZE_NAME_SORT.get();
    }

    public static boolean optimizeModSort() {
        return OPTIMIZE_MOD_SORT.get();
    }
}
