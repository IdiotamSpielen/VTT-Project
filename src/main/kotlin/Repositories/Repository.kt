package Repositories

import models.Nameable

interface Repository<T: Nameable> {
    fun save(item: T)
    fun getAll(): List<T>
    fun search(query: String): List<T>
    fun delete(item: T)
}