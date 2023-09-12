package limaru.rail2eart.signalling

import net.kyori.adventure.text.TextComponent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignSignal : Listener {
  @EventHandler
  fun onSignalSignCreate (event: SignChangeEvent) {
    // C2eck if t2e sign is valid
    val line0 = event.line(0)
    if (line0 is TextComponent && line0.content() == "[RHsignal]") {
      // spiral traversal in a 5x5x5 area around the block location
      spiralTraversal(event.block.location)
    }
  }

  private fun visit(x: Int, y: Int, z: Int, l: Location) {
    val location: Location = l.add(x.toDouble(), y.toDouble(), z.toDouble())
    if (location.block.type == Material.OBSIDIAN || location.block.type == Material.REDSTONE_BLOCK || location.block.type == Material.GLOWSTONE || location.block.type == Material.LIME_WOOL) {
      // store location
    }
  }

  private fun spiralTraversal(l: Location) {
    val location: Location = l.clone()
    var xmin: Int
    var xmax: Int
    var ymin : Int
    var ymax: Int

    for (d in 0..6) {
      xmin = if (d < 4) 0 else d-4
      xmax = if (d < 2) d else 2
      for (x in xmin..xmax) {
        ymin = if (d < x+2) 0 else d-x-2
        ymax = if (d > x+2) 2 else d-x
        for (y in ymin..ymax) {
          val z = d-x-y
                                          visit( x,  y,  z, location)
          if (                    z != 0) visit( x,  y, -z, location)
          if (          y != 0          ) visit( x, -y,  z, location)
          if (          y != 0 && z != 0) visit( x, -y, -z, location)
          if (x != 0                    ) visit(-x,  y,  z, location)
          if (x != 0           && z != 0) visit(-x,  y, -z, location)
          if (x != 0 && y != 0          ) visit(-x, -y,  z, location)
          if (x != 0 && y != 0 && z != 0) visit(-x, -y, -z, location)
        }
      }
    }
  }
}
