package limaru.railheart.signalling

import com.bergerkiller.bukkit.tc.events.SignActionEvent
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent
import com.bergerkiller.bukkit.tc.signactions.SignAction
import com.bergerkiller.bukkit.tc.signactions.SignActionType

class SignActionRegisterTrain : SignAction() {
  override fun match (info: SignActionEvent): Boolean {
    return info.isType("rhregister");
  }

  override fun execute (info: SignActionEvent) {
    if (info.isTrainSign
      && info.isAction(SignActionType.GROUP_ENTER, SignActionType.REDSTONE_ON)
      && info.isPowered && info.hasGroup()
    ) {
      // line 0: [RHRegister]
      // line 1: <train route>

      // get route
      val route = info.getLine(1)

      // get the next train's number based on the timetable
      // we cannot process further if there is no next train
      val timetable = Timetable(route)
      val nextNumber = timetable.nextTrainNumber() ?: return

      //
    }
  }

  override fun build(event: SignChangeActionEvent?): Boolean {
    TODO("Not yet implemented")
  }

}