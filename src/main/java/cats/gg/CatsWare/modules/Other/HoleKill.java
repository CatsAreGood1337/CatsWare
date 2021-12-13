package cats.gg.CatsWare.modules.Other;

import cats.gg.CatsWare.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HoleKill extends Module {
    public HoleKill() {super("HoleKill", "HoleKill", "Kills you if you sit in the hole more than 5 seconds", Category.Other);
    }

    long last = -1;

    public static BlockPos getEntityBlockPos(Entity entity) {
        return new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ));
    }

    public static boolean checkHole(BlockPos hole) {
        BlockPos one = hole.add(1,0,0),
                two = hole.add(-1,0,0),
                three = hole.add(0,0,1),
                four = hole.add(0,0,-1),
                head = hole.add(0,1,0),
                ground = hole.add(0,-1,0);
        return
                mc.world.getBlockState(head).getBlock() == Blocks.AIR &&
                mc.world.getBlockState(one).getBlock() != Blocks.AIR &&
                mc.world.getBlockState(two).getBlock() != Blocks.AIR &&
                mc.world.getBlockState(three).getBlock() != Blocks.AIR &&
                mc.world.getBlockState(four).getBlock() != Blocks.AIR &&
                mc.world.getBlockState(ground).getBlock() != Blocks.AIR;
    }

    @Override
    public void onUpdate() {
        if (checkHole(getEntityBlockPos(mc.player))) {
            if (last == -1) {
                last = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - last > 5000) {
                mc.player.sendChatMessage("/kill");
                last = -1;
            }
        }else last = -1;
    }
}



