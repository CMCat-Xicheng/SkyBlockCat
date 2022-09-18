
package net.ccbluex.liquidbounce.features.module.modules.world;


import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "GhostBlock", description = "Turn blocks to air client-side by right click pickaxe.", category = ModuleCategory.WORLD)
class GhostBlock : Module() {
    @SubscribeEvent
    fun onInteract(event : PlayerInteractEvent) {
        val lookingAtblock = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
        if( mc.objectMouseOver != null && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && (lookingAtblock != Blocks.chest) && (lookingAtblock != Blocks.lever) && (lookingAtblock != Blocks.trapped_chest) && (lookingAtblock != Blocks.wooden_button) && (lookingAtblock != Blocks.stone_button) && (lookingAtblock != Blocks.skull) ) {
            if(mc.thePlayer.getHeldItem().item is ItemPickaxe) {
                mc.theWorld.setBlockToAir(mc.objectMouseOver.getBlockPos());
            }
        }
    }

}
