package domain

import kotlinx.serialization.Serializable

@Serializable
data class LibraryEntry(
    val id: Int,
    val name: String,
    val type: String,
    val systemId: String,

    val config: LibraryConfig
)