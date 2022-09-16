/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.ListValue

import net.minecraft.util.ResourceLocation

@ModuleInfo(name = "Cape", description = "Classic capes.", category = ModuleCategory.RENDER)
class Cape : Module() {

    val styleValue = ListValue("Style", arrayOf("Migrator", "Minecon2012", "Mojang"), "Migrator")

    private val capeCache = hashMapOf<String, CapeStyle>()

    fun getCapeLocation(value: String): ResourceLocation {
        if (capeCache[value.toUpperCase()] == null) {
            try {
                capeCache[value.toUpperCase()] = CapeStyle.valueOf(value.toUpperCase())
            } catch (e: Exception) {
                capeCache[value.toUpperCase()] = CapeStyle.MIGRATOR
            }
        }
        return capeCache[value.toUpperCase()]!!.location
    }

    enum class CapeStyle(val location: ResourceLocation) {
        MIGRATOR(ResourceLocation("liquidbounce+/cape/Migrator.png")),
        MINECON2012(ResourceLocation("liquidbounce+/cape/Minecon2012.png")),
        MOJANG(ResourceLocation("liquidbounce+/cape/Mojang.png")),
    }

    override val tag: String
        get() = styleValue.get()

}
