package services

import exceptions.InvalidSpellException
import exceptions.SpellNotSavedException
import models.Spell
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.IOException

class SpellHandlerTest {

    private lateinit var mockFileHandler: FileHandler<Spell>
    private lateinit var spellHandler: SpellHandler

    @BeforeEach
    fun setUp() {

        @Suppress("UNCHECKED_CAST")
        mockFileHandler = mock(FileHandler::class.java) as FileHandler<Spell>
        spellHandler = SpellHandler(mockFileHandler)
    }

    @Test
    fun `createAndSaveSpell calls saveToFile on FileHandler when spell is valid`() {
        //GIVEN
        val spell = createValidSpell()

        //WHEN
        spellHandler.createAndSaveSpell(spell)
        //THEN
        verify(mockFileHandler).saveToFile(spell)
    }

    @Test
    fun `createAndSaveSpell throws SpellNotSavedException if FileHandler throws IOException`() {

        // GIVEN
        val spell = createValidSpell()

        doThrow(IOException()).`when`(mockFileHandler).saveToFile(spell)

        //WHEN / THEN
        assertThrows(SpellNotSavedException::class.java) {
            spellHandler.createAndSaveSpell(spell)
        }

        verify(mockFileHandler).saveToFile(spell)
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException and DOES NOT save when name is empty`() {
        // GIVEN
        val invalidSpell = createValidSpell().copy(name = "")

        // WHEN / THEN
        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(invalidSpell)
        }

        verify(mockFileHandler, never()).saveToFile(any())
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException for level out of range`() {
        val invalidSpell = createValidSpell().copy(level = 10)

        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(invalidSpell)
        }

        verify(mockFileHandler, never()).saveToFile(any())
    }

    private fun createValidSpell(): Spell {
        return Spell(
            name = "Fireball",
            school = "Evocation",
            duration = "Instant",
            components = "V,S,M",
            level = 3,
            range = "150 feet",
            castingTime = "1 action",
            description = "A bright streak flashes...",
            ingredients = "Sulfur",
            ritual = false,
            concentration = false
        )
    }
}
