package com.github.hoshikurama.ticketmanager.api.ticket

/**
 * Location where an action was performed.
 * @property server specified by the plugin configuration file (for proxy networks)
 */
sealed interface ActionLocation {
    val server: String?

    /**
     * Represents where a player performed a ticket action.
     * @property world world name
     * @property x x-block position
     * @property y y-block position
     * @property z z-block position
     */
    @JvmRecord
    @Suppress("Unused")
    data class FromPlayer(
        override val server: String?,
        val world: String,
        val x: Int,
        val y: Int,
        val z: Int,
    ) : ActionLocation

    /**
     * Represents Console's location, which only has a server component.
     */
    class FromConsole(override val server: String?) : ActionLocation
}

//Player: "${server ?: ""} $world $x $y $z".trimStart()
// Console: "${server ?: ""} ${""} ${""} ${""} ${""}".trimStart()