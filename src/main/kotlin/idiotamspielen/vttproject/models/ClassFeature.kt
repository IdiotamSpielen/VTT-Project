package idiotamspielen.vttproject.models

//Not to be confused with Feat which describes Feats
class ClassFeature(
    override val name: String,
    private val description: String? = null
) : Nameable
