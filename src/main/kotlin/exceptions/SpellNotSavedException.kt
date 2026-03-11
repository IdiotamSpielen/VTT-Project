package exceptions

import utils.L

/**
 * Exception thrown when a spell cannot be saved to the repository.
 *
 * @param errorKey The localization key for the error message.
 */
class SpellNotSavedException(val errorKey: L) : Exception()
