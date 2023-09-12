package limaru.railheart

class RailHeartUtils {
  fun getRHTag (trainNumber : String, trainRoute : String) : String {
    val PREFIX = "RH"

    //
    return PREFIX + trainNumber + trainRoute
  }
}