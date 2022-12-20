package ch.epfl.sweng.ctf.repositories

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import ch.epfl.sweng.ctf.models.Fruit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Fruits repository
 * Stores and retrieves fruits from a local database
 *
 * @author Alexandre Chau
 */
interface FruitsRepository {
    /**
     * Adds the given Fruit to the database
     */
    suspend fun addFruit(fruit: Fruit)

    /**
     * Returns the list of fruits that match the given search keyword
     */
    suspend fun searchFruits(keyword: String?): List<Fruit>

    /**
     * Returns the number of fruits in the database
     */
    suspend fun countFruits(): Int

    /**
     * Removes all entries in the fruits database
     */
    fun removeAllFruits()
}

/**
 * Implementation of a Fruits repository using the Room SQLite database
 */
class FruitsRepositoryImpl(ctx: Context) : FruitsRepository {
    /**
     * Adapter class for [ch.epfl.sweng.ctf.models.Fruit] with
     * Room database annotations for storage
     */
    @Entity
    data class Fruit(@PrimaryKey val name: String)

    /**
     * Database Access Object (DAO) to interface the lower-level Room SQLite database
     */
    @Dao
    interface FruitDao {
        /**
         * Inserts a fruit into the database with SQL
         */
        @Query("INSERT INTO fruit (name) VALUES (:fruitName)")
        fun insert(fruitName: String)

        /**
         * Perform an arbitrary query into the database with SQL
         */
        @RawQuery
        fun query(query: SimpleSQLiteQuery): List<Fruit>

        /**
         * Counts the number of fruits in the database with SQL
         */
        @Query("SELECT COUNT(name) FROM fruit")
        fun count(): Int

        /**
         * Removes all fruits from DB
         */
        @Query("DELETE FROM fruit")
        fun deleteAll()
    }

    /**
     * Database declaration, instantiated by Room
     */
    @Database(entities = [Fruit::class], version = 1)
    abstract class FruitsDatabase : RoomDatabase() {
        abstract fun fruitDao(): FruitDao
    }

    // FruitsDatabase instance
    private val roomDB = Room.databaseBuilder(ctx, FruitsDatabase::class.java, "fruits").build()

    // Get instance of DAO to access database tables
    private val fruitDao = roomDB.fruitDao()

    override suspend fun addFruit(fruit: ch.epfl.sweng.ctf.models.Fruit) {
        fruitDao.insert(fruit.name)
    }

    override suspend fun searchFruits(keyword: String?): List<ch.epfl.sweng.ctf.models.Fruit> {
        val term = if (keyword == null) "%" else "%$keyword%"
        val results =
            fruitDao.query(SimpleSQLiteQuery("SELECT * FROM fruit WHERE (name NOT LIKE 'flag%' AND name LIKE '$term');"))
        // transform results back from Fruit adapters to global Fruit models
        return results.map { fruit -> ch.epfl.sweng.ctf.models.Fruit(fruit.name) }
    }

    override suspend fun countFruits(): Int {
        return fruitDao.count()
    }

    override fun removeAllFruits() {
        GlobalScope.launch {
            fruitDao.deleteAll()
        }
    }
}