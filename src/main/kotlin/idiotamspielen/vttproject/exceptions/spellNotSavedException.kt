package idiotamspielen.vttproject.exceptions

/**
 * Exception thrown when a spell could not be saved to the file system.
 *
 * This exception is used in scenarios where a save operation for a spell
 * fails, providing an appropriate error message for debugging or user notification.
 *
 * @param message Detailed message explaining the reason for the failure.
 */
class SpellNotSavedException(message: String) : Exception(message)