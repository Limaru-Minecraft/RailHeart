package limaru.railheart.extras

import com.bergerkiller.bukkit.tc.signactions.SignAction
import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Extras : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)
  private val swapSign: SwapSign = SwapSign()

  override fun onEnable() {
    // Register SignActions
    SignAction.register<SignAction>(swapSign)
    plugin.logger.info("Extras module has been enabled!")
  }

  override fun onDisable() {
    // Unregister SignActions
    SignAction.unregister(swapSign)
    plugin.logger.info("Extras module has been disabled!")
  }
}