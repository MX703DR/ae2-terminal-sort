package com.mx703.ae2terminalsort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.fml.ModList;

import appeng.api.config.SortDir;
import appeng.api.config.SortOrder;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
public final class CreativeSortOrder {
    public static SortOrder CREATIVE;
    public static SortOrder JEI;

    private static final ResourceLocation UNKNOWN_ID = ResourceLocation.fromNamespaceAndPath("unknown", "unknown");

    private static Map<Item, Integer> itemOrder;
    private static Map<Item, Integer> jeiItemOrder;
    private static final Map<AEKey, ResourceLocation> stableIds = new IdentityHashMap<>();

    private CreativeSortOrder() {
    }

    public static boolean isCreative(SortOrder order) {
        return order != null && "CREATIVE".equals(order.name());
    }

    public static boolean isJei(SortOrder order) {
        return order != null && "JEI".equals(order.name());
    }

    public static boolean isJeiAvailable() {
        return ModList.get().isLoaded("jei") && jeiItemOrder != null;
    }

    public static void setJeiItemOrder(Iterable<ItemStack> stacks) {
        var order = new HashMap<Item, Integer>();
        int index = 0;

        for (var stack : stacks) {
            if (!stack.isEmpty() && !order.containsKey(stack.getItem())) {
                order.put(stack.getItem(), index++);
            }
        }

        jeiItemOrder = order;
        AE2TerminalSort.LOGGER.debug("Indexed {} JEI items for AE2 terminal sorting", order.size());
    }

    public static void clearJeiItemOrder() {
        jeiItemOrder = null;
    }

    public static Comparator<AEKey> getComparator(SortDir direction) {
        var comparator = Comparator.<AEKey>comparingInt(CreativeSortOrder::getCreativeIndex)
                .thenComparing(CreativeSortOrder::getStableId)
                .thenComparingInt(Object::hashCode);
        return direction == SortDir.ASCENDING ? comparator : comparator.reversed();
    }

    public static Comparator<AEKey> getJeiComparator(SortDir direction) {
        var comparator = Comparator.<AEKey>comparingInt(CreativeSortOrder::getJeiIndex)
                .thenComparing(CreativeSortOrder::getStableId)
                .thenComparingInt(Object::hashCode);
        return direction == SortDir.ASCENDING ? comparator : comparator.reversed();
    }

    public static Comparator<AEKey> getModComparator(SortDir direction) {
        var comparator = Comparator.comparing(AEKey::getModId, String::compareToIgnoreCase)
                .thenComparing(CreativeSortOrder::getStableId)
                .thenComparingInt(Object::hashCode);
        return direction == SortDir.ASCENDING ? comparator : comparator.reversed();
    }

    public static Comparator<AEKey> getNameComparator(SortDir direction) {
        var comparator = Comparator.comparing(CreativeSortOrder::getStableId)
                .thenComparingInt(Object::hashCode);
        return direction == SortDir.ASCENDING ? comparator : comparator.reversed();
    }

    private static int getCreativeIndex(AEKey key) {
        if (key instanceof AEItemKey itemKey) {
            return getItemOrder().getOrDefault(itemKey.getItem(), Integer.MAX_VALUE);
        }

        return Integer.MAX_VALUE;
    }

    private static int getJeiIndex(AEKey key) {
        if (key instanceof AEItemKey itemKey && jeiItemOrder != null) {
            return jeiItemOrder.getOrDefault(itemKey.getItem(), Integer.MAX_VALUE);
        }

        return Integer.MAX_VALUE;
    }

    private static ResourceLocation getStableId(AEKey key) {
        var cached = stableIds.get(key);
        if (cached != null) {
            return cached;
        }

        var id = computeStableId(key);
        stableIds.put(key, id);
        return id;
    }

    private static ResourceLocation computeStableId(AEKey key) {
        if (key instanceof AEItemKey itemKey) {
            var id = BuiltInRegistries.ITEM.getKey(itemKey.getItem());
            return id != null ? id : UNKNOWN_ID;
        }

        var id = key.getId();
        return id != null ? id : UNKNOWN_ID;
    }

    private static Map<Item, Integer> getItemOrder() {
        if (itemOrder == null) {
            itemOrder = buildItemOrder();
        }
        return itemOrder;
    }

    private static Map<Item, Integer> buildItemOrder() {
        var order = new HashMap<Item, Integer>();
        int index = 0;

        for (var tab : CreativeModeTabs.allTabs()) {
            for (var stack : tab.getDisplayItems()) {
                if (!stack.isEmpty() && !order.containsKey(stack.getItem())) {
                    order.put(stack.getItem(), index++);
                }
            }
        }

        AE2TerminalSort.LOGGER.debug("Indexed {} creative tab items for AE2 terminal sorting", order.size());
        return order;
    }
}
