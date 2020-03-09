package nahguam.fractal

import io.ktor.application.call
import io.ktor.http.content.file
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respondOutputStream
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File

fun server(): ApplicationEngine {
  return embeddedServer(Netty, 8080, "localhost") {
    routing {
      static("static") {
        staticRootFolder = File("src/main/resources")
        file("index.html")
      }
      route("/tile") {
        route("/{z}") {
          route("/{x}") {
            get("/{y}.png") {
              val z = call.parameters["z"]!!.toDouble()
              val x = call.parameters["x"]!!.toDouble()
              val y = call.parameters["y"]!!.toDouble()
              call.respondOutputStream { tile(z, x, y) }
            }
          }
        }
      }
    }
  }
}
