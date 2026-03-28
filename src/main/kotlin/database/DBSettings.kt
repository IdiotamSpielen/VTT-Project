package database

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.Logger
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import org.slf4j.LoggerFactory


object DBSettings {
    private val dbFolder = Paths.get(System.getProperty("user.home"), "Documents", "VTT")
    private val dbPath = dbFolder.resolve("vtt.db").toString()
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    val db by lazy {

        if (!Files.exists(dbFolder)) {
            Files.createDirectories(dbFolder)
            logger.info("Created database $dbFolder")
        }

        val db = Database.connect("jdbc:sqlite:$dbPath", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        db
    }

    /**
     * Clears all data from the database by dropping and recreating tables.
     * This is intended for debug purposes only.
     */
    fun clearDatabase() {
        transaction(db) {
            SchemaUtils.drop(SpellsTable, ImageAssetsTable, MapsTable)
            SchemaUtils.create(SpellsTable, ImageAssetsTable, MapsTable)
            logger.info("Database cleared and tables recreated.")
        }
    }
}