package com.craftix.mixin;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG;

@Mixin(ChunkGenerator.class)
public class ChunkGenMix {

	@Inject(method = "trySetStructureStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/structure/Structure;createStructureStart(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/structure/StructureTemplateManager;JLnet/minecraft/util/math/ChunkPos;ILnet/minecraft/world/HeightLimitView;Ljava/util/function/Predicate;)Lnet/minecraft/structure/StructureStart;"), cancellable = true)
	private void injected(StructureSet.WeightedEntry p_223105_, StructureAccessor p_223106_, DynamicRegistryManager p_223107_, NoiseConfig randomState, StructureTemplateManager p_223109_, long seed, Chunk chunkAccess, ChunkPos chunkPos, ChunkSectionPos p_223113_, CallbackInfoReturnable<Boolean> cir) {
		var key = p_223105_.structure().getKey();
	//	System.out.println("works!!!!!!!!");
		if (key.isPresent() && key.get().getValue().toString().contains("medieval_buildings")) {
			int x = chunkPos.getCenterX();
			int z = chunkPos.getCenterZ();
			ChunkGenerator chunkGenerator = (ChunkGenerator) (Object) this;


			if (!isLegal(x, z, chunkAccess, chunkGenerator, chunkAccess.getHeightLimitView(), randomState) || !isLegal(x - 16, z, chunkAccess, chunkGenerator, chunkAccess.getHeightLimitView(), randomState) || !isLegal(x + 16, z, chunkAccess, chunkGenerator, chunkAccess.getHeightLimitView(), randomState) || !isLegal(x, z - 16, chunkAccess, chunkGenerator, chunkAccess.getHeightLimitView(), randomState) || !isLegal(x, z + 16, chunkAccess, chunkGenerator, chunkAccess.getHeightLimitView(), randomState)) {
				cir.setReturnValue(false);
				return;
			}


		}

	}


	@Unique
	private boolean isLegal(int x, int z, Chunk chunkAccess, ChunkGenerator chunkGenerator, HeightLimitView levelHeightAccessor, NoiseConfig state) {
		int k = chunkGenerator.getHeightInGround(x, z, WORLD_SURFACE_WG, levelHeightAccessor, state);

		return k <= 78 && k >= 55;
	}

}