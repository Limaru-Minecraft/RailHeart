// main class
package net.limaru.railheart;

import org.bukkit.plugin.java.JavaPlugin;

class RailHeart : JavaPlugin() 
{
  override fun onEnable()
  {
    // plugin startup logic
    logger.info("RailHeart Core has been enabled!")
  }

  override fun onDisable()
  {
    // plugin shutdown logic
    logger.info("RailHeart Core has been disabled!")
  }
}