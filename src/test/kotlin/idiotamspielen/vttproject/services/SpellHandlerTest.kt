package idiotamspielen.vttproject.services

import idiotamspielen.vttproject.exceptions.InvalidSpellException
import idiotamspielen.vttproject.models.Spell
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class SpellHandlerTest {
    @TempDir
    lateinit var tempDir: Path

    private lateinit var originalHome: String
    private lateinit var fileHandler: FileHandler<Spell>
    private lateinit var spellHandler: SpellHandler

    @BeforeEach
    fun setUp() {
        originalHome = System.getProperty("user.home")
        System.setProperty("user.home", tempDir.toString())
        fileHandler = FileHandler(Spell::class.java, "spells")
        spellHandler = SpellHandler(fileHandler)
    }

    @AfterEach
    fun tearDown() {
        System.setProperty("user.home", originalHome)
    }

    @Test
    fun `createAndSaveSpell saves valid spell`() {
        val spell = Spell(
            name = "Fireball",
            school = "Evocation",
            duration = "Instant",
            components = "V,S,M",
            level = 3,
            range = "150 feet",
            castingTime = "1 action",
            description = "A bright streak flashes...",
            ingredients = "A tiny ball of bat guano and sulfur",
            ritual = false,
            concentration = false
        )

        assertDoesNotThrow { spellHandler.createAndSaveSpell(spell) }
        val savedPath = tempDir.resolve("Documents/VTT/library/data/spells/${spell.name}.json")
        assertTrue(Files.exists(savedPath))
        assertTrue(fileHandler.isSaved())
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException for empty name`() {
        val spell = Spell(
            name = "",
            school = "Evocation",
            duration = "Instant",
            components = "V,S,M",
            level = 3,
            range = "150 feet",
            castingTime = "1 action",
            description = "desc",
            ingredients = "ing",
            ritual = false,
            concentration = false
        )

        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(spell)
        }
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException for level out of range`() {
        val spell = Spell(
            name = "Fireball",
            school = "Evocation",
            duration = "Instant",
            components = "V,S,M",
            level = 10,
            range = "150 feet",
            castingTime = "1 action",
            description = "desc",
            ingredients = "ing",
            ritual = false,
            concentration = false
        )

        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(spell)
        }
    }
}
