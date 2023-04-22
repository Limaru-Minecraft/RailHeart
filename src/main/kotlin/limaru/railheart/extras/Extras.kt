package limaru.railheart.extras

import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Extras : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  override fun onEnable() {
    plugin.logger.info("Extras module has been enabled!")
  }

  override fun onDisable() {
    plugin.logger.info("Extras module has been disabled!")
  }
}