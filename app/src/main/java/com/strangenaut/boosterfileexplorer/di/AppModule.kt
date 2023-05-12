package com.strangenaut.boosterfileexplorer.di

import android.app.Application
import androidx.room.Room
import com.strangenaut.boosterfileexplorer.feature_filereader.data.datasource.FileDatabase
import com.strangenaut.boosterfileexplorer.feature_filereader.data.repository.FileRepositoryImpl
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.repository.FileRepository
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFileDatabase(app: Application): FileDatabase {
        return Room.databaseBuilder(
            app,
            FileDatabase::class.java,
            FileDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFileRepository(db: FileDatabase): FileRepository {
        return FileRepositoryImpl(db.fileDao)
    }

    @Provides
    @Singleton
    fun provideFileUseCases(repository: FileRepository): FileUseCases {
        return FileUseCases(
            getNestedFileItems = GetNestedFileItems(),
            orderFiles = OrderFiles(),
            compareFiles = CompareFiles(),
            getFileHashInfoList = GetFileHashInfoList(repository),
            insertNestedFilesHashInfoList = InsertNestedFilesHashInfoList(repository),
        )
    }
}