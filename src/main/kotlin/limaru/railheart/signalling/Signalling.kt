package limaru.railheart.signalling

import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Signalling : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  override fun onEnable() {
    plugin.logger.info("Signalling module has been enabled!")
  }

  override fun onDisable() {
    plugin.logger.info("Signalling module has been disabled!")
  }
}