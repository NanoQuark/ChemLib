package com.smashingmods.chemlib.client.events;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import com.mojang.datafixers.util.Either;
import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.api.utility.FluidEffectsTooltipUtility;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BucketItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = ChemLib.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void onRenderTooltip(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof BucketItem bucket
                && BuiltInRegistries.FLUID.getResourceKey(bucket.getFluid()).isPresent()
                && BuiltInRegistries.FLUID.getResourceKey(bucket.getFluid()).get().location().getNamespace().equals(ChemLib.MODID)) {

            gatherTooltipComponents(event, bucket);
        }
    }

    public static void gatherTooltipComponents(RenderTooltipEvent.GatherComponents event, BucketItem bucket) {
        Function<FormattedText, Either<FormattedText, TooltipComponent>> formattedTextFunction = Either::left;

        for (FormattedText textElement : FluidEffectsTooltipUtility.getBucketEffectTooltipComponents(event.getItemStack())) {
            event.getTooltipElements().add(formattedTextFunction.apply(textElement));
        }
        String namespace = BuiltInRegistries.FLUID.getResourceKey(bucket.getFluid()).get().location().getNamespace();
        event.getTooltipElements().add(formattedTextFunction.apply(MutableComponent.create(new LiteralContents(StringUtils.capitalize(namespace))).withStyle(ChemLib.MOD_ID_TEXT_STYLE)));
    }
}