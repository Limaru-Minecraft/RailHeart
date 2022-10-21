package net.limaru.railheart

import org.bukkit.plugin.java.JavaPlugin

class RailHeart : JavaPlugin() {
  override fun onEnable() {
    // Plugin startup logic
    logger.info("Enabled RailHeart Plugin!")
  }

  override fun onDisable() {
    // Plugin shutdown logic
    logger.info("Disabled RailHeart Plugin!")
  }
}