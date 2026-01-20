package exceptions

import util.L

/**
 * Exception thrown when a spell could not be saved to the file system.
 *
 * This exception is used in scenarios where a save operation for a spell
 * fails, providing an appropriate error message for debugging or user notification.
 *
 * @param message Detailed message explaining the reason for the failure.
 */
class SpellNotSavedException(val errorKey: L) : Exception(errorKey.key)