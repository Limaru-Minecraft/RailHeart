package limaru.railheart.signalling

import com.bergerkiller.bukkit.common.utils.TimeUtil
import limaru.railheart.RailHeart
import java.io.File

class Timetable (routeName : String) {
  private val plugin = RailHeart.getPlugin(RailHeart.Companion::class.java)
  private val timetable = File(plugin.dataFolder.path.plus("timetables").plus("routeName")).readLines()

  private val trainNumbers = timetable[0].split(",")  // train numbers
  private val routeWaypoints = timetable.map { it.split(",")[0] }  // train route waypoints

  fun nextTrainNumber () : String? {
    val time = System.currentTimeMillis()
    val spawnTimes = timetable[1].split(",")
    for (i in 1 until spawnTimes.size) {
      if (TimeUtil.getTime(spawnTimes[i-1]) < time && TimeUtil.getTime(spawnTimes[i]) >= time) {
        return trainNumbers[i]
      }
    }
    return null
  }
}