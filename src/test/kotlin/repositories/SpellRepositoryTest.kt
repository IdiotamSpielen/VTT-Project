package repositories

import database.SpellsTable
import domain.Spell
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager

class SpellRepositoryTest {
    private val repository = SpellRepository()
    private lateinit var keepAliveConnection: Connection

    @BeforeEach
    fun setUp() {
        val jdbcUrl = "jdbc:sqlite:file:test?mode=memory&cache=shared"
        keepAliveConnection = DriverManager.getConnection(jdbcUrl)

        Database.connect(jdbcUrl, "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(SpellsTable)
        }
    }

    @AfterEach
    fun tearDown() {
        transaction {
            SpellsTable.deleteAll()
            SchemaUtils.drop(SpellsTable)
        }
        keepAliveConnection.close()
    }

    @Test
    fun `save adds a spell to the database`() {
        val spell = createDummySpell("Fireball")
        repository.save(spell)

        val allSpells = repository.getAll()
        assertEquals(1, allSpells.size)
        assertEquals("Fireball", allSpells.first().name)
    }

    @Test
    fun `save updates existing spell when name matches`() {
        // GIVEN:
        val originalSpell = createDummySpell("Fireball").copy(description = "Macht 8d6 Schaden")
        repository.save(originalSpell)

        assertEquals("Macht 8d6 Schaden", repository.search("Fireball").first().description)

        // WHEN:
        val updatedSpell = createDummySpell("Fireball").copy(description = "Macht jetzt 10d6 Schaden!")
        repository.save(updatedSpell)

        // THEN:
        val allSpells = repository.getAll()

        assertEquals(1, allSpells.size, "Should still be only 1 spell")
        assertEquals("Macht jetzt 10d6 Schaden!", allSpells.first().description, "Description should be updated")
    }

    @Test
    fun `search finds spells ignoring case`() {
        repository.save(createDummySpell("Magic Missile"))
        repository.save(createDummySpell("Magic Mouth"))
        repository.save(createDummySpell("Fireball"))

        val results = repository.search("magic")

        assertEquals(2, results.size)
        assertTrue(results.any { it.name == "Magic Missile" })
        assertTrue(results.any { it.name == "Magic Mouth" })
    }

    @Test
    fun `search returns empty list when no matches found`() {
        repository.save(createDummySpell("Ice Storm"))

        val results = repository.search("fire")

        assertTrue(results.isEmpty(), "Result list should be empty, not null")
    }

    @Test
    fun `delete removes spell from database`() {
        // GIVEN
        val spell = createDummySpell("To Be Deleted")
        repository.save(spell)
        assertEquals(1, repository.getAll().size)

        // WHEN
        repository.delete(spell)

        // THEN
        val results = repository.getAll()
        assertEquals(0, results.size, "Database should be empty after delete")
    }

    private fun createDummySpell(name: String): Spell {
        return Spell(
            name = name,
            school = "Evocation",
            duration = "Instant",
            components = "V,S",
            level = 1,
            range = "120 feet",
            castingTime = "1 action",
            description = "Test description",
            ingredients = "",
            ritual = false,
            concentration = false
        )
    }
}