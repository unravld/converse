package net.unraveled.world;

import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.Nullable;

public interface Noise {
    final class TerrainSettings {
        @Nullable
        public BlockData stoneBlock = null;

        @Nullable
        public BlockData waterBlock = null;

        public int seaLevel = -1;
    }

    void getNoise(Biome biome, double[] buffer, int x, int z);

    default TerrainSettings getSettings() {
        return new TerrainSettings();
    }
}
