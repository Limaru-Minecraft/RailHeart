package limaru.railheart.announcements

import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Announcements : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  override fun onEnable() {
    plugin.logger.info("Announcement module has been enabled!")

  }

  override fun onDisable() {
    plugin.logger.info("Announcement module has been disabled!")
  }

}