package com.craftix.medieval_buildings.shared.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkGenerator.class)
public abstract class ChunkGeneratorMix {

    @Inject(method = "tryGenerateStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/Structure;generate(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/biome/BiomeSource;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;JLnet/minecraft/world/level/ChunkPos;ILnet/minecraft/world/level/LevelHeightAccessor;Ljava/util/function/Predicate;)Lnet/minecraft/world/level/levelgen/structure/StructureStart;"), cancellable = true)
    private void injected(StructureSet.StructureSelectionEntry p_223105_, StructureManager p_223106_, RegistryAccess p_223107_, RandomState randomState, StructureTemplateManager p_223109_, long p_223110_, ChunkAccess chunkAccess, ChunkPos chunkPos, SectionPos p_223113_, CallbackInfoReturnable<Boolean> cir) {
        var key = p_223105_.structure().unwrapKey();
        //  System.out.println("works!!!!!!!!");
        if (key.isPresent() && key.get().location().toString().contains("medieval_buildings")) {
            int x = chunkPos.getMiddleBlockX();
            int z = chunkPos.getMiddleBlockZ();
            ChunkGenerator chunkGenerator = (ChunkGenerator) (Object) this;

            if (!isLegal(x, z, chunkAccess, chunkGenerator, chunkAccess.getHeightAccessorForGeneration(), randomState) || !isLegal(x - 16, z, chunkAccess, chunkGenerator, chunkAccess.getHeightAccessorForGeneration(), randomState) || !isLegal(x + 16, z, chunkAccess, chunkGenerator, chunkAccess.getHeightAccessorForGeneration(), randomState) || !isLegal(x, z - 16, chunkAccess, chunkGenerator, chunkAccess.getHeightAccessorForGeneration(), randomState) || !isLegal(x, z + 16, chunkAccess, chunkGenerator, chunkAccess.getHeightAccessorForGeneration(), randomState)) {
                cir.setReturnValue(false);
                return;
            }
        }
    }

    @Unique
    private boolean isLegal(int x, int z, ChunkAccess chunkAccess, ChunkGenerator chunkGenerator, LevelHeightAccessor levelHeightAccessor, RandomState state) {
        int k = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, state);

        return k <= 78 && k >= 55;
    }
}