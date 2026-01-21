package database

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
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
}