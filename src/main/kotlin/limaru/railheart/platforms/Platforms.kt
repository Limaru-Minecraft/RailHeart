package limaru.railheart.platforms

import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Platforms : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  override fun onEnable() {
    plugin.logger.info("Platforms module has been enabled!")
  }

  override fun onDisable() {
    plugin.logger.info("Platforms module has been disabled!")
  }
}