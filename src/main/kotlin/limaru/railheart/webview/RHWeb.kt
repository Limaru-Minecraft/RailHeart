package limaru.railheart.webview

class RHWeb {

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