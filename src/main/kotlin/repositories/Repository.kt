package repositories

import org.jetbrains.exposed.v1.jdbc.transactions.transaction
interface Repository<T> {
    fun save(item: T): Int?
    fun getAll(): List<T>
    fun getRecent(limit: Int = 10): List<T>
    fun search(query: String): List<T>
    fun delete(item: T)
}