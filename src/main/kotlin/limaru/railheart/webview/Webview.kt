package limaru.railheart.webview

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import limaru.railheart.RHModule
import limaru.railheart.RailHeart

class Webview : RHModule {
  private val plugin: RailHeart = RailHeart.getPlugin(RailHeart::class.java)

  override fun onEnable() {
    plugin.logger.info("Webview module has been enabled!")
    val port : Int = plugin.config.getInt("webserver-port", 8080)
    webserver(port)
  }

  override fun onDisable() {
    plugin.logger.info("Webview module has been disabled!")
  }

  fun webserver(port: Int) {
    embeddedServer(Netty, port) {
      routing {
        get("/") {
          call.respondText("Hello, world!")
        }
      }
    }.start(wait = true)
  }
}