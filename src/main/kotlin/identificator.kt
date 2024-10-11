import io.github.kroune.CONFIGURATION_DIRECTORY
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

object Identifier {
    private var bannedIdentificators: MutableMap<String, Boolean> = mutableMapOf()
    private var identificators: MutableMap<Long, String> = mutableMapOf()
    private var reversedIdentificators: MutableMap<String, Long> = mutableMapOf()

    init {
        val configDir = File(CONFIGURATION_DIRECTORY)
        configDir.mkdirs()
        val configFile = File(CONFIGURATION_DIRECTORY, "config.json")
        if (configFile.exists()) {
            Json.decodeFromString<Configuration>(configFile.readText()).let {
                identificators = it.identificators
                reversedIdentificators = it.reversedIdentificators
                bannedIdentificators = it.bannedIdentificators
            }
        }
    }

    @Serializable
    private class Configuration(
        val identificators: MutableMap<Long, String>,
        val reversedIdentificators: MutableMap<String, Long>,
        val bannedIdentificators: MutableMap<String, Boolean>,
    )

    private fun save() {
        val configDir = File(CONFIGURATION_DIRECTORY)
        configDir.mkdirs()
        val configFile = File(CONFIGURATION_DIRECTORY, "data.json")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
        val configuration = Configuration(identificators, reversedIdentificators, bannedIdentificators)
        val text = Json.encodeToString<Configuration>(configuration)
        configFile.writeText(text)
    }


    fun getIdentification(id: Long): String {
        val result = identificators[id]
        if (result == null) {
            val uuid = UUID.randomUUID().toString()
            identificators[id] = uuid
            reversedIdentificators[uuid] = id
            save()
            return uuid
        }
        return result
    }

    fun ban(uuid: String) {
        bannedIdentificators[uuid] = true
        save()
    }

    fun unban(uuid: String) {
        bannedIdentificators.remove(uuid)
        save()
    }

    fun isBanned(uuid: String): Boolean {
        return bannedIdentificators[uuid] == true
    }

    fun exists(uuid: String): Boolean {
        return reversedIdentificators.containsKey(uuid)
    }
}