package com.smashingmods.chemlib.datagen;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.smashingmods.chemlib.registry.BlockRegistry;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockLootTables extends BlockLootSubProvider {

    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        BlockRegistry.BLOCKS.getEntries().stream().forEach(block -> dropSelf(block.get()));
    }

    @SuppressWarnings("unchecked")
	@Override
    @Nonnull
    protected Iterable<Block> getKnownBlocks() {
        return (List<Block>) BlockRegistry.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList();
        
    }
}
