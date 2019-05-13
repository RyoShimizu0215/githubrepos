package com.example.githubrepos.repos

import com.example.githubrepos.data.Repository
import com.example.githubrepos.util.BasePresenter
import com.example.githubrepos.util.BaseView

interface ReposContract {

    interface View : BaseView<Presenter> {

        fun showRepos(repos: List<Repository>)
        fun showReadme(readmeURL: String)

    }

    interface Presenter : BasePresenter {

        fun loadRepos()
        fun loadReadme(repository: Repository)

    }
}