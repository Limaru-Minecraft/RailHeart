package limaru.railheart.signalling

import com.bergerkiller.bukkit.tc.signactions.SignAction
import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Signalling : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)
  private val signActionRegisterTrain: SignActionRegisterTrain = SignActionRegisterTrain()

  override fun onEnable() {

    // Register SignActions
    SignAction.register<SignAction>(signActionRegisterTrain)

    plugin.logger.info("Signalling module has been enabled!")
  }

  override fun onDisable() {

    // Unregister SignActions
    SignAction.unregister(signActionRegisterTrain)

    plugin.logger.info("Signalling module has been disabled!")
  }
}