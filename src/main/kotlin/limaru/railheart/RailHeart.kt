package limaru.railheart

import limaru.railheart.announcements.Announcements
import limaru.railheart.extras.Extras
import limaru.railheart.platforms.Platforms
import limaru.railheart.signalling.Signalling
import limaru.railheart.webview.Webview
import org.bukkit.plugin.java.JavaPlugin

class RailHeart : JavaPlugin() {
  val announcements = Announcements()
  val platforms = Platforms()
  val signalling = Signalling()
  val webview = Webview()
  val extras = Extras()


  override fun onEnable() {
    logger.info("RailHeart Plugin has been enabled!")

    // enable modules
    announcements.onEnable()
    platforms.onEnable()
    signalling.onEnable()
    webview.onEnable()
    extras.onEnable()
  }

  override fun onDisable() {
    logger.info("RailHeart Plugin has been disabled!")

    // disable modules
    announcements.onDisable()
    platforms.onDisable()
    signalling.onDisable()
    webview.onDisable()
    extras.onDisable()
  }

  // get this class
  companion object {
    fun <T> getPlugin(clazz: Class<T>): RailHeart {
      return getPlugin(clazz)
    }
  }
}