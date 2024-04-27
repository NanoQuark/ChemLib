package com.smashingmods.chemlib.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.api.ChemicalItemType;
import com.smashingmods.chemlib.api.MatterState;
import com.smashingmods.chemlib.registry.ItemRegistry;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemTagGenerator extends ItemTagsProvider {

    public ItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, TagsProvider<Block> pBlockTagProvider, ExistingFileHelper pFileHelper) {
        super(pOutput, pLookupProvider, pBlockTagProvider.contentsGetter(), ChemLib.MODID, pFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        ItemRegistry.getChemicalItems().forEach(item -> {
            String type = item.getItemType().getSerializedName();
            String name = item.getChemicalName();
            TagKey<Item> key = ItemTags.create(new ResourceLocation("c", String.format("%ss/%s", type, name)));
            tag(key).add(item);
        });

        ItemRegistry.getChemicalBlockItems().forEach(item -> {
            if (item.getMatterState().equals(MatterState.SOLID)) {
                String name = item.getChemicalName();
                TagKey<Item> key = ItemTags.create(new ResourceLocation("c", String.format("storage_blocks/%s", name)));
                tag(key).add(item);
            }
        });

        ItemRegistry.getChemicalItemByNameAndType("potassium_nitrate", ChemicalItemType.COMPOUND).ifPresent(compound -> {
            ResourceLocation niter = new ResourceLocation("c", "dusts/niter");
            TagKey<Item> key = ItemTags.create(niter);
            tag(key).add(compound);
        });

        ItemRegistry.getChemicalItemByNameAndType("hydroxylapatite", ChemicalItemType.COMPOUND).ifPresent(compound -> {
            ResourceLocation niter = new ResourceLocation("c", "dusts/apatite");
            TagKey<Item> key = ItemTags.create(niter);
            tag(key).add(compound);
        });

        ItemRegistry.getChemicalItemByNameAndType("cellulose", ChemicalItemType.COMPOUND).ifPresent(compound -> {
            ResourceLocation sawdust = new ResourceLocation("c", "sawdust");
            TagKey<Item> key = ItemTags.create(sawdust);
            tag(key).add(compound);
        });

        ItemRegistry.getChemicalItemByNameAndType("mercury_sulfide", ChemicalItemType.COMPOUND).ifPresent(compound -> {
            ResourceLocation sawdust = new ResourceLocation("c", "dusts/cinnabar");
            TagKey<Item> key = ItemTags.create(sawdust);
            tag(key).add(compound);
        });
    }

    @Override
    @Nonnull
    public String getName() {
        return ChemLib.MODID + ":tags";
    }
}
