package com.mobbelldev.gameverse.core.di

import android.content.Context
import androidx.room.Room
import com.mobbelldev.gameverse.core.BuildConfig
import com.mobbelldev.gameverse.core.data.source.local.room.GameDao
import com.mobbelldev.gameverse.core.data.source.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        val phrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.ENCRYPT.toCharArray())
        val factory = SupportFactory(phrase)
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            GAME_DB
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()

    companion object {
        private const val GAME_DB = "Game.db"
    }
}