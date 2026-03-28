package viewmodels

import repositories.Spell
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import repositories.SpellRepository
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import services.FeedbackHandler

class SpellCreationViewTest {

    @Test
    fun `createSpell calls save on repository when input is valid`() {
        //GIVEN

        val mockRepo = mock(SpellRepository::class.java)
        val viewModel = SpellCreationViewmodel(repository = mockRepo)

        viewModel.updateState(SpellUiState(
            name = "Test Spell",
            level = "3",
            school = "Evo",
            castingTime = "1h",
            range = "Touch",
            components = "V",
            duration = "1m",
            description = "Boom"
        ))

        //WHEN
        viewModel.createSpell()

        val spellCaptor = argumentCaptor<Spell>()

        verify(mockRepo, times(1)).save(spellCaptor.capture())
        assertEquals(FeedbackHandler.FeedbackType.SUCCESS, viewModel.feedbackHandler.currentType)
    }

    @Test
    fun `createSpell handles Database Crash gracefully`() {
        val mockRepo = mock(SpellRepository::class.java)
        val spellCaptor = argumentCaptor<Spell>()
        `when`(mockRepo.save(spellCaptor.capture())).thenThrow(RuntimeException("DB is burning!"))
        val viewModel = SpellCreationViewmodel(repository = mockRepo)

        viewModel.updateState(SpellUiState(
            name = "Crash Spell",
            level = "3",
            school = "Evo",
            castingTime = "1h",
            range = "Touch",
            components = "V",
            duration = "1m",
            description = "Boom"
        ))

        viewModel.createSpell()
        assertEquals(FeedbackHandler.FeedbackType.ERROR, viewModel.feedbackHandler.currentType)
    }
}