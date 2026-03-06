package services

import models.Spell
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class FileHandlerTest {

    @TempDir
    lateinit var tempDir: Path
    private lateinit var fileHandler: FileHandler<Spell>

    @BeforeEach
    fun setUp() {
        fileHandler = FileHandler(Spell::class.java, "spells", basePath = tempDir)
    }

    @Test
    fun `saveToFile creates a json file`() {
        // GIVEN
        val spell = createDummySpell("Fireball")

        // WHEN
        fileHandler.saveToFile(spell)

        // THEN
        val expectedPath = tempDir.resolve("spells/Fireball.json")
        assertTrue(Files.exists(expectedPath), "The file should exist at $expectedPath")
        assertTrue(Files.size(expectedPath) > 0, "File should not be empty")
    }

    @Test
    fun `getSavedObjectInformation retrieves previously saved objects`() {
        // GIVEN
        val spell1 = createDummySpell("Fireball")
        val spell2 = createDummySpell("IceStorm")

        fileHandler.saveToFile(spell1)
        fileHandler.saveToFile(spell2)

        // WHEN
        val loadedSpells = fileHandler.getSavedObjectInformation()

        // THEN
        assertEquals(2, loadedSpells.size, "Should retrieve exactly 2 spells")

        // Check for specific entries as file system iteration order is not guaranteed
        val loadedNames = loadedSpells.map { it.name }
        assertTrue(loadedNames.contains("Fireball"))
        assertTrue(loadedNames.contains("IceStorm"))
    }

    @Test
    fun `saveToFile throws IllegalArgumentException if object is null`() {
        // WHEN / THEN
        assertThrows(IllegalArgumentException::class.java) {
            fileHandler.saveToFile(null)
        }
    }

    @Test
    fun `search finds objects ignoring case`() {
        // GIVEN
        val spell1 = createDummySpell("Magic Missile")
        val spell2 = createDummySpell("Magic Mouth")
        val spell3 = createDummySpell("Fireball")

        fileHandler.saveToFile(spell1)
        fileHandler.saveToFile(spell2)
        fileHandler.saveToFile(spell3)

        // WHEN
        val results = fileHandler.search("magic")

        // THEN
        assertEquals(2, results.size)
        assertTrue(results.any { it.name == "Magic Missile" })
        assertTrue(results.any { it.name == "Magic Mouth" })
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