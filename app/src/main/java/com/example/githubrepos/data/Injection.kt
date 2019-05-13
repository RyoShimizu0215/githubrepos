package com.example.githubrepos.data

import android.content.Context
import com.example.githubrepos.data.remote.ReposRepository
import com.example.githubrepos.data.source.remote.ReposRemoteDataSource

object Injection {
    fun provideReposRepository(context: Context): ReposRepository {
        return ReposRepository.getInstance(ReposRemoteDataSource.getInstance())
    }
}