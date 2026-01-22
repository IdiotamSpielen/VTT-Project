package repositories

interface Repository<T> {
    fun save(item: T)
    fun getAll(): List<T>
    fun search(query: String): List<T>
    fun delete(item: T)
}