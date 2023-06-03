package com.petrolpark.destroy.block.entity;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.block.instance.CentrifugeCogInstance;
import com.petrolpark.destroy.block.instance.DynamoCogInstance;
import com.petrolpark.destroy.block.renderer.AgingBarrelRenderer;
import com.petrolpark.destroy.block.renderer.BubbleCapRenderer;
import com.petrolpark.destroy.block.renderer.CentrifugeRenderer;
import com.petrolpark.destroy.block.renderer.CoolerRenderer;
import com.petrolpark.destroy.block.renderer.DynamoRenderer;
import com.petrolpark.destroy.block.renderer.PollutometerRenderer;
import com.petrolpark.destroy.block.renderer.PumpjackRenderer;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.world.level.block.Block;

public class DestroyBlockEntityTypes {

    private static CreateRegistrate REGISTRATE = Destroy.registrate();

    public static final BlockEntityEntry<AgingBarrelBlockEntity> AGING_BARREL = REGISTRATE
        .blockEntity("aging_barrel", AgingBarrelBlockEntity::new)
        .validBlocks(DestroyBlocks.AGING_BARREL)
        .renderer(() -> AgingBarrelRenderer::new)
        .register();

    public static final BlockEntityEntry<BubbleCapBlockEntity> BUBBLE_CAP = REGISTRATE
        .blockEntity("bubble_cap", BubbleCapBlockEntity::new)
        .validBlocks(DestroyBlocks.BUBBLE_CAP)
        .renderer(() -> BubbleCapRenderer::new)
        .register();

    public static final BlockEntityEntry<CentrifugeBlockEntity> CENTRIFUGE = REGISTRATE
        .blockEntity("centrifuge", CentrifugeBlockEntity::new)
        .instance(() -> CentrifugeCogInstance::new)
        .validBlocks(DestroyBlocks.CENTRIFUGE)
        .renderer(() -> CentrifugeRenderer::new)
        .register();

    public static final BlockEntityEntry<CoolerBlockEntity> COOLER = REGISTRATE
        .blockEntity("cooler", CoolerBlockEntity::new)
        .validBlocks(DestroyBlocks.COOLER)
        .renderer(() -> CoolerRenderer::new)
        .register();

    public static final BlockEntityEntry<DynamoBlockEntity> DYNAMO = REGISTRATE
        .blockEntity("dynamo", DynamoBlockEntity::new)
        .instance(() -> DynamoCogInstance::new)
        .validBlocks(DestroyBlocks.DYNAMO)
        .renderer(() -> DynamoRenderer::new)
        .register();

    public static final BlockEntityEntry<PollutometerBlockEntity> POLLUTOMETER = REGISTRATE
        .blockEntity("pollutometer", PollutometerBlockEntity::new)
        .validBlocks(DestroyBlocks.POLLUTOMETER)
        .renderer(() -> PollutometerRenderer::new)
        .register();

    public static final BlockEntityEntry<PumpjackBlockEntity> PUMPJACK = REGISTRATE
		.blockEntity("pumpjack", PumpjackBlockEntity::new)
		//.instance(() -> PumpjackInstance::new, false) Can't use instancing because that can't render cutout for some reason
		.validBlocks(DestroyBlocks.PUMPJACK)
		.renderer(() -> PumpjackRenderer::new)
		.register();

    public static final BlockEntityEntry<PumpjackCamBlockEntity> PUMPJACK_CAM = REGISTRATE
		.blockEntity("pumpjack_cam", PumpjackCamBlockEntity::new)
		.validBlocks(DestroyBlocks.PUMPJACK_CAM)
		.register();

    public static final BlockEntityEntry<SandCastleBlockEntity> SAND_CASTLE = REGISTRATE
        .blockEntity("sand_castle", SandCastleBlockEntity::new)
        .validBlocks(DestroyBlocks.SAND_CASTLE)
        .register();

    private static final BlockEntityBuilder<VatSideBlockEntity, CreateRegistrate> VAT_SIDE_BUILDER = REGISTRATE
        .blockEntity("vat_side", VatSideBlockEntity::new);
    
    static {
        VatMaterial.registerDestroyVatMaterials();
        for (NonNullSupplier<? extends Block> block : VatMaterial.BLOCK_MATERIALS.keySet()) {
            VAT_SIDE_BUILDER.validBlock(block);
        };
    };

    public static final BlockEntityEntry<VatSideBlockEntity> VAT_SIDE = VAT_SIDE_BUILDER.register();

    public static void register() {};
    
}
