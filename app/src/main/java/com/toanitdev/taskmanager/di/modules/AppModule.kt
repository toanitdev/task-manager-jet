package com.toanitdev.taskmanager.di.modules

import android.content.Context
import androidx.room.Room
import com.toanitdev.taskmanager.data.datasources.local.room.RoomDataSource
import com.toanitdev.taskmanager.data.datasources.local.room.TodoDatabase
import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSource
import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSourceImpl
import com.toanitdev.taskmanager.data.repositories.ProjectRepositoryImpl
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl) : RemoteDataSource
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindProjectRepository(repositoryImpl: ProjectRepositoryImpl) : ProjectRepository
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context) : TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "AppDatabase").fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDataSource(todoDatabase: TodoDatabase) : RoomDataSource {
        return RoomDataSource(todoDatabase)
    }

}