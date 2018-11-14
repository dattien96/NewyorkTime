package com.framgia.data.local.database

import android.arch.persistence.room.*
import com.framgia.data.model.NyTimeLocalEntity
import io.reactivex.Single

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
@Dao
abstract class NewsDao {

    @Query("SELECT * FROM nytime")
    abstract fun getLocalStories(): Single<List<NyTimeLocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStories(items: List<NyTimeLocalEntity>)

    @Delete
    abstract fun deleteStories(items: List<NyTimeLocalEntity>)

    @Query("SELECT * FROM nytime where nytime.url = :url")
    abstract fun findStoryExist(url: String): Single<NyTimeLocalEntity>
}
