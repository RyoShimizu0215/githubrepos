package com.example.githubrepos.repos

import com.example.githubrepos.data.Repository
import com.example.githubrepos.data.remote.ReposDataSource
import com.example.githubrepos.data.remote.ReposRepository
import com.example.githubrepos.repos.ReposContract

class ReposPresenter(val reposRepository: ReposRepository, val reposView: ReposContract.View)
    : ReposContract.Presenter {

    init {
        reposView.presenter = this
    }

    override fun start() {
        loadRepos()
    }

    override fun loadRepos() {
        loadReposImpl()
    }

    private fun loadReposImpl() {
        reposRepository.getRepos(object : ReposDataSource.LoadReposCallback {
            override fun onReposLoaded(repos: List<Repository>) {
                processRepos(repos)
            }
        })
    }

    private fun processRepos(repos: List<Repository>) {
        if (repos.isEmpty()) {
            return
        } else {
            reposView.showRepos(repos)
        }
    }

    override fun loadReadme(repository: Repository) {
        reposView.showReadme(repository.readmeURL)
    }

}