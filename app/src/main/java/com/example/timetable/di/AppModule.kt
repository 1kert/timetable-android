package com.example.timetable.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    @Singleton
//    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
//        context,
//        AppDatabase::class.java,
//        "my_database"
//    ).build()
//
//    @Provides
//    @Singleton
//    fun providesTextDao(appDatabase: AppDatabase): TextDao = appDatabase.textDao()
}
