package idiotamspielen.vttproject.exceptions

/**
 * Exception thrown to indicate an invalid spell during creation or validation.
 *
 * This exception is used in the context of spell creation processes
 * within the application, particularly when the provided spell data does not meet
 * the necessary validation criteria. It signifies that the spell being processed
 * is defective or non-conforming to expected standards.
 *
 * @param message Detailed message describing the reason for the exception.
 */
class InvalidSpellException(message: String) : Exception(message)