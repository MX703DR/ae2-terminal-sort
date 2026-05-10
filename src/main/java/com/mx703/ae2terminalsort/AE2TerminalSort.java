package com.mx703.ae2terminalsort;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(AE2TerminalSort.MOD_ID)
public final class AE2TerminalSort {
    public static final String MOD_ID = "ae2terminalsort";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AE2TerminalSort(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, AE2TerminalSortConfig.CLIENT_SPEC);
    }
}
