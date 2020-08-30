package ru.grv.testtask.data.db.unitlocalized

import androidx.room.*
import io.reactivex.Observable
import ru.grv.testtask.domain.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getProfile(): Observable<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: ProfileEntity?)

    @Query("DELETE FROM profile")
    fun deleteProfile()
}