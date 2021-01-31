package ru.grv.testtask.data.db.unitlocalized

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.grv.testtask.domain.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getProfile(): Single<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: ProfileEntity?)

    @Query("DELETE FROM profile")
    fun deleteProfile()
}