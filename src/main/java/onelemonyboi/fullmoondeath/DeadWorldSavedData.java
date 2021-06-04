package onelemonyboi.fullmoondeath;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.*;

public class DeadWorldSavedData extends WorldSavedData {
    public Set<UUID> isDead;

    public static DeadWorldSavedData getInstance(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD).getSavedData().getOrCreate(DeadWorldSavedData::new, "deadworldsaveddata");
    }

    public DeadWorldSavedData() {
        super("deadworldsaveddata");
        this.isDead = new HashSet<>();
    }

    @Override
    public void read(CompoundNBT nbt) {
        isDead = new HashSet<>();

        if (nbt.get("isDead") instanceof ListNBT) {
            ListNBT listNBT = (ListNBT) nbt.get("isDead");
            for (INBT inbt : listNBT.toArray(new INBT[0])) {
                isDead.add(NBTUtil.readUniqueId(inbt));
            }
        }
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        for (UUID uuid : isDead) {
            listNBT.add(NBTUtil.func_240626_a_(uuid));
        }
        compound.put("isDead", listNBT);
        return compound;
    }
}
