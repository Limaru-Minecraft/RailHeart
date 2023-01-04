package net.limaru.railheart

import org.bukkit.plugin.java.JavaPlugin

class RailHeart : JavaPlugin() {
  override fun onEnable() {
    // Plugin startup logic
    logger.info("Enabled Project RailHeart!")
  }

  override fun onDisable() {
    // Plugin shutdown logic
    logger.info("Disabled Project RailHeart!")
  }
}