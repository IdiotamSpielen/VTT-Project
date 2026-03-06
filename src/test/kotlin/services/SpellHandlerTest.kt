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

    private lateinit var mockRepository: repositories.SpellRepository
    private lateinit var spellHandler: SpellHandler

    @BeforeEach
    fun setUp() {
        mockRepository = mock(repositories.SpellRepository::class.java)
        spellHandler = SpellHandler(mockRepository)
    }

    @Test
    fun `createAndSaveSpell calls save on repository when spell is valid`() {
        //GIVEN
        val spell = createValidSpell()

        //WHEN
        spellHandler.createAndSaveSpell(spell)
        //THEN
        verify(mockRepository).save(spell)
    }

    @Test
    fun `createAndSaveSpell throws SpellNotSavedException if repository throws Exception`() {

        // GIVEN
        val spell = createValidSpell()

        doThrow(RuntimeException()).`when`(mockRepository).save(spell)

        //WHEN / THEN
        assertThrows(SpellNotSavedException::class.java) {
            spellHandler.createAndSaveSpell(spell)
        }

        verify(mockRepository).save(spell)
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException and DOES NOT save when name is empty`() {
        // GIVEN
        val invalidSpell = createValidSpell().copy(name = "")

        // WHEN / THEN
        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(invalidSpell)
        }

        verify(mockRepository, never()).save(any())
    }

    @Test
    fun `createAndSaveSpell throws InvalidSpellException for level out of range`() {
        val invalidSpell = createValidSpell().copy(level = 10)

        assertThrows(InvalidSpellException::class.java) {
            spellHandler.createAndSaveSpell(invalidSpell)
        }

        verify(mockRepository, never()).save(any())
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
