package limaru.railheart

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.logging.Level

class CustomConfig @JvmOverloads constructor(private val name: String, plugin: Plugin = RailHeart.getPlugin(RailHeart::class.java)) {
  var file: File
    private set
  private val config: YamlConfiguration?
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  init {
    file = File(plugin.dataFolder, name)
    if (!file.exists()) {
      val logger = plugin.logger
      logger.log(
        Level.INFO,
        if (file.parentFile.mkdirs()) "[Iciwi] New config file created" else "[Iciwi] Config file already exists, initialising files..."
      )
      plugin.saveResource(name, false)
    }
    config = YamlConfiguration()
    try {
      config.load(file)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun save() {
    try {
      config!!.save(file)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun saveDefaultConfig() {
    if (!file.exists()) {
      plugin.saveResource(name, false)
    }
  }

  fun get(): YamlConfiguration? {
    if (config == null) reload()
    return config
  }

  operator fun get(path: String?): Any {
    return config!![path!!]!!
  }

  fun getString(path: String?): String {
    return config!!.getString(path!!)!!
  }

  fun getBoolean(path: String?): Boolean {
    return config!!.getBoolean(path!!)
  }

  fun getInt(path: String?): Int {
    return config!!.getInt(path!!)
  }

  fun getDouble(path: String?): Double {
    return config!!.getDouble(path!!)
  }

  fun getLong(path: String?): Long {
    return config!!.getLong(path!!)
  }

  fun getConfigurationSection(path: String?): ConfigurationSection {
    return config!!.getConfigurationSection(path!!)!!
  }

  operator fun set(path: String?, value: Any?) {
    config!![path!!] = value
  }

  fun reload() {
    file = File(plugin.dataFolder, name)
    try {
      config!!.load(file)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}
