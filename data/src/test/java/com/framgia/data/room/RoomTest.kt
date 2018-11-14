package com.framgia.data.room

import android.arch.persistence.room.Room
import com.framgia.data.local.database.NewsDao
import com.framgia.data.local.database.NyTimeDatabase
import com.framgia.data.model.NyTimeLocalEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class RoomTest {

    private lateinit var dataBase: NyTimeDatabase
    private lateinit var dao: NewsDao

    /**
     * Here we use Room.inMemoryDatabaseBuilder instead of Room.databaseBuilder
     * We create an virtual db , data is saved in memory and will be release when process is killed
     */
    @Before
    fun setUp() {
        dataBase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application,
            NyTimeDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        dao = dataBase.localNyTimeDao()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun testGetAndInsertStories() {
        dao.insertStories(listOf(NyTimeLocalEntity("my url", "my title")))
        dao.getLocalStories().test().run {

            assertComplete()
            assertValue {
                it.size == 1 &&
                        it[0].title == "my title" &&
                        it[0].url == "my url"
            }
        }
    }


    @Test
    fun testFindStories() {
        dao.findStoryExist("my url").test().assertNoValues() // before insert, we don't have data

        //insert
        dao.insertStories(listOf(NyTimeLocalEntity("my url", "my title")))

        //test with valid data
        dao.findStoryExist("my url").test().run {
            assertComplete()
            assertValue {
                it.title == "my title" &&
                        it.url == "my url"
            }
        }
    }
}
