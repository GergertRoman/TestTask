package ru.grv.testtask.data.db.unitlocalized

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import ru.grv.testtask.domain.entity.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(booksList: List<BookEntity?>)

    @Query("SELECT * FROM book")
    fun getAllBooks(): Observable<List<BookEntity>>

    @Query("DELETE FROM book")
    fun deleteAllBooks()
}