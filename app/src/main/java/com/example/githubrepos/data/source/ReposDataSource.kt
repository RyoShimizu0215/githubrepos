package com.example.githubrepos.data.remote

import com.example.githubrepos.data.Repository

interface ReposDataSource {

    interface LoadReposCallback {
        fun onReposLoaded(repos: List<Repository>)
    }

    fun getRepos(callback: LoadReposCallback)

}