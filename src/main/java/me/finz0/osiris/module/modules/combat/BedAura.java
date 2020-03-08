package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.util.BlockUtils;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class BedAura extends Module {
    public BedAura() {
        super("BedAura", Category.COMBAT, "Right clicks beds");
        rSetting(range = new Setting("Range", this, 4.5, 0, 10, false, "BedAuraRange"));
        rSetting(rotate = new Setting("Rotate", this, true, "BedAuraRotate"));
        rSetting(dimensionCheck = new Setting("DimensionCheck", this, true, "BedAuraDimensionCheck"));
    }

    private Setting range;
    private Setting dimensionCheck;
    private Setting rotate;

    public void onUpdate(){
        mc.world.loadedTileEntityList.stream()
                .filter(e -> e instanceof TileEntityBed)
                .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.getValDouble())
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                .forEach(bed -> {

                    if(dimensionCheck.getValBoolean() && mc.player.dimension == 0) return;

                    if(rotate.getValBoolean()) BlockUtils.faceVectorPacketInstant(new Vec3d(bed.getPos().add(0.5, 0.5, 0.5)));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));

                });
    }
}
