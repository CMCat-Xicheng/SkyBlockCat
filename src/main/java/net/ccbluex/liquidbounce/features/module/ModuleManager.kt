/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.module

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.KeyEvent
import net.ccbluex.liquidbounce.event.Listenable
import net.ccbluex.liquidbounce.features.module.modules.combat.*
import net.ccbluex.liquidbounce.features.module.modules.exploit.*
import net.ccbluex.liquidbounce.features.module.modules.misc.*
import net.ccbluex.liquidbounce.features.module.modules.movement.*
import net.ccbluex.liquidbounce.features.module.modules.player.*
import net.ccbluex.liquidbounce.features.module.modules.render.*
import net.ccbluex.liquidbounce.features.module.modules.world.*
import net.ccbluex.liquidbounce.features.module.modules.world.Timer
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer
import net.ccbluex.liquidbounce.utils.ClientUtils
import java.util.*


class ModuleManager : Listenable {

    public val modules = TreeSet<Module> { module1, module2 -> module1.name.compareTo(module2.name) }
    private val moduleClassMap = hashMapOf<Class<*>, Module>()

    public var shouldNotify : Boolean = false
    public var toggleSoundMode = 0
    public var toggleVolume = 0F

    init {
        LiquidBounce.eventManager.registerListener(this)
    }

    /**
     * Register all modules
     */
    fun registerModules() {
        ClientUtils.getLogger().info("[ModuleManager] Loading modules...")

        registerModules(
                Patcher::class.java,
                Aimbot::class.java,
                KillAura::class.java,
                Velocity::class.java,
                NoSlow::class.java,
                GhostBlock::class.java
                Fly::class.java,
                ClickGUI::class.java,
                InvMove::class.java,
                Sprint::class.java,
                Teams::class.java,
                AntiBot::class.java,
                ChestStealer::class.java,
                FastBreak::class.java,
                FastPlace::class.java,
                ESP::class.java,
                Fullbright::class.java,
                ItemESP::class.java,
                StorageESP::class.java,
                Spammer::class.java,
                Blink::class.java,
                NameProtect::class.java,
                NoHurtCam::class.java,
                XRay::class.java,
                FreeCam::class.java,     
                HitBox::class.java,
                Plugins::class.java,
                AutoClicker::class.java,
                BlockESP::class.java,
                Chams::class.java,
                Phase::class.java,
                NoFOV::class.java,
                Animations::class.java,
                TNTBlock::class.java,
                TrueSight::class.java,
                AntiBlind::class.java,
                CameraClip::class.java,
                Reach::class.java,
                Rotations::class.java,
                HUD::class.java,
                TNTESP::class.java,
                NoSlowBreak::class.java,
                Ambience::class.java,
                EnchantEffect::class.java,
                Cape::class.java,
                DamageParticle::class.java,
                Lightning::class.java,
                ItemPhysics::class.java,
                ColorMixer::class.java,
                Crosshair::class.java,
                MultiActions::class.java,
                TargetMark::class.java,
                SafeWalk::class.java,
                GhostHand::class.java,
                Freeze::class.java,
                AntiCactus::class.java,
                Eagle::class.java,
                Parkour::class.java,
                AntiAFK::class.java,
                AutoFish::class.java,
                AutoWalk::class.java,
                HoverDetails::class.java,
                AutoBreak::class.java,
                Nuker::class.java,
                NewGUI::class.java,
        )

        registerModule(Fucker)
        registerModule(ChestAura)
        registerModule(ChestAura2)

        ClientUtils.getLogger().info("[ModuleManager] Successfully loaded ${modules.size} modules.")
    }

    /**
     * Register [module]
     */
    fun registerModule(module: Module) {
        modules += module
        moduleClassMap[module.javaClass] = module

        module.onInitialize()
        generateCommand(module)
        LiquidBounce.eventManager.registerListener(module)
    }

    /**
     * Register [moduleClass]
     */
    private fun registerModule(moduleClass: Class<out Module>) {
        try {
            registerModule(moduleClass.newInstance())
        } catch (e: Throwable) {
            ClientUtils.getLogger().error("Failed to load module: ${moduleClass.name} (${e.javaClass.name}: ${e.message})")
        }
    }

    /**
     * Register a list of modules
     */
    @SafeVarargs
    fun registerModules(vararg modules: Class<out Module>) {
        modules.forEach(this::registerModule)
    }

    /**
     * Unregister module
     */
    fun unregisterModule(module: Module) {
        modules.remove(module)
        moduleClassMap.remove(module::class.java)
        LiquidBounce.eventManager.unregisterListener(module)
    }

    /**
     * Generate command for [module]
     */
    internal fun generateCommand(module: Module) {
        val values = module.values

        if (values.isEmpty())
            return

        LiquidBounce.commandManager.registerCommand(ModuleCommand(module, values))
    }

    /**
     * Legacy stuff
     *
     * TODO: Remove later when everything is translated to Kotlin
     */

    /**
     * Get module by [moduleClass]
     */
    fun <T : Module> getModule(moduleClass: Class<T>): T? = moduleClassMap[moduleClass] as T?

    operator fun <T : Module> get(clazz: Class<T>) = getModule(clazz)

    /**
     * Get module by [moduleName]
     */
    fun getModule(moduleName: String?) = modules.find { it.name.equals(moduleName, ignoreCase = true) }

    /**
     * Module related events
     */

    /**
     * Handle incoming key presses
     */
    @EventTarget
    private fun onKey(event: KeyEvent) = modules.filter { it.keyBind == event.key }.forEach { it.toggle() }

    override fun handleEvents() = true
}
