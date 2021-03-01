package northern.ui

import com.eclipsesource.json.Json
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.InputStreamReader
import java.net.URI
import java.nio.charset.StandardCharsets
import java.time.LocalDate

object VpnController {

    val servers: Servers

    init {
        val json = Json.parse(InputStreamReader(this.javaClass.classLoader.getResourceAsStream("servers.json")!!))
        servers = Servers(
            json.asObject().get("creationDate").asString().split(".")
                .let { LocalDate.of(it[2].toInt(), it[1].toInt(), it[0].toInt()) },
            json.asObject().get("countries").asArray().map { jv ->
                Server(
                    jv.asObject().get("country").asString(),
                    jv.asObject().get("name").asString(),
                    jv.asObject().get("link").asString()
                )
            }.groupBy { server: Server -> server.country }
        )
    }

    fun connect(country: String) {
        val server = servers.servers[country]!!.random()
        FileUtils.copyURLToFile(URI(server.link).toURL(), File("/tmp/${server.name}"))
        showNotification("Connecting to ${server.name}")
        Runtime.getRuntime().exec(arrayOf("openvpn3", "session-start", "--config", "/tmp/${server.name}"))
    }

    private fun getSessionPath(): String? {
        return IOUtils.readLines(
            Runtime.getRuntime().exec(arrayOf("openvpn3", "sessions-list")).inputStream,
            StandardCharsets.UTF_8
        ).firstOrNull { s ->
            s.contains("Path:")
        }?.let { s -> s.trim().split(": ")[1] }
    }

    private fun showNotification(msg: String) {
        Runtime.getRuntime().exec(arrayOf("notify-send", msg))
    }

    fun disconnect() {
        getSessionPath()?.let {
            showNotification("Disconnecting")
            Runtime.getRuntime().exec(arrayOf("openvpn3", "session-manage", "--disconnect", "-o", getSessionPath()))
        }
    }

    fun isConnected() = getSessionPath() != null

    fun getSessionInfo(): String {
        return IOUtils.readLines(
            Runtime.getRuntime().exec(arrayOf("openvpn3", "sessions-list")).inputStream,
            StandardCharsets.UTF_8
        ).joinToString("\n")
    }
}