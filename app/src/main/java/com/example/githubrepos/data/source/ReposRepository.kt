package com.example.githubrepos.data.remote

import android.util.Log
import com.example.githubrepos.data.Repository

class ReposRepository(
    val reposRemoteDataSource: ReposDataSource
) : ReposDataSource {

    var cachedRepos: LinkedHashMap<String, Repository> = LinkedHashMap()

    override fun getRepos(callback: ReposDataSource.LoadReposCallback) {
        getReposFromRemmoteDataSource(callback)
    }

    private fun getReposFromRemmoteDataSource(callback: ReposDataSource.LoadReposCallback) {
        reposRemoteDataSource.getRepos(object : ReposDataSource.LoadReposCallback {
            override fun onReposLoaded(repos: List<Repository>) {
                callback.onReposLoaded(repos)
            }
        })
    }

    companion object {

        private var INSTANCE: ReposRepository? = null

        @JvmStatic fun getInstance(reposRemoteDataSource: ReposDataSource): ReposRepository {
            return INSTANCE ?: ReposRepository(reposRemoteDataSource).apply { INSTANCE = this }
        }
    }

}