package com.mx703.ae2terminalsort;

import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;

import java.util.Collection;

@JeiPlugin
public final class JeiSortPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(AE2TerminalSort.MOD_ID, "jei_sort_order");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        CreativeSortOrder.setJeiItemOrder(jeiRuntime.getIngredientFilter().getFilteredItemStacks());
        jeiRuntime.getIngredientManager().registerIngredientListener(new IIngredientManager.IIngredientListener() {
            @Override
            public <V> void onIngredientsAdded(IIngredientHelper<V> helper, Collection<ITypedIngredient<V>> ingredients) {
                CreativeSortOrder.setJeiItemOrder(jeiRuntime.getIngredientFilter().getFilteredItemStacks());
            }

            @Override
            public <V> void onIngredientsRemoved(IIngredientHelper<V> helper, Collection<ITypedIngredient<V>> ingredients) {
                CreativeSortOrder.setJeiItemOrder(jeiRuntime.getIngredientFilter().getFilteredItemStacks());
            }
        });
    }

    @Override
    public void onRuntimeUnavailable() {
        CreativeSortOrder.clearJeiItemOrder();
    }
}
