package cats.gg.CatsWare.modules.PVP;

import cats.gg.CatsWare.modules.Module;
import cats.gg.CatsWare.util.ItemUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class InstantBurrow extends Module {
    public InstantBurrow() {
        super("InstantBurrow","InstantBurrow", "Glitches u inside block", Category.PVP);
    }

    private BlockPos originalPos;
    private int oldSlot = -1;

    @Override
    public void onEnable() {
        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) ||
                intersectsWithEntity(this.originalPos)) {
            toggle();
            return;
        }
        oldSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        if (ItemUtil.findHotbarBlock(BlockObsidian.class) == -1) {
            toggle();
            return;
        }
        ItemUtil.switchToSlot(ItemUtil.findHotbarBlock(BlockObsidian.class));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
        ItemUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, true, true, false);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + -6.395812, mc.player.posZ, false));
        ItemUtil.switchToSlot(oldSlot);
        Minecraft.getMinecraft().player.setSneaking(false);
        toggle();
    }

    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.setSneaking(false);
        super.onDisable();
    }
}
