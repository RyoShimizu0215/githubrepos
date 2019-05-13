package com.example.githubrepos.repos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.example.githubrepos.BR.repo
import com.example.githubrepos.R
import com.example.githubrepos.data.Repository
import com.example.githubrepos.databinding.RepoItemBinding

class ReposFragment : androidx.fragment.app.Fragment(), ReposContract.View {
    override lateinit var presenter: ReposContract.Presenter
    lateinit var mView: View

    public var itemListener: ReposItemListener = object :
        ReposItemListener {
        override fun onClick(clickedRepo: Repository) {
            presenter.loadReadme(clickedRepo)
        }
    }

    private val listAdapter = ReposAdapter(ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.repos_frag, container, false)

        with(root) {
            val listView = findViewById<ListView>(R.id.repos_list).apply { adapter = listAdapter }
        }
        mView = root
        return root
    }

    override fun showRepos(repos: List<Repository>) {
        listAdapter.repos = repos
    }

    override fun showReadme(readmeURL: String) {
        val bundle = Bundle()
        bundle.putString("URL", readmeURL)

        val fragment = ReadmeFragment()
        fragment.setArguments(bundle)

        val manager = fragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.addToBackStack("")
        transaction?.replace(R.id.container, fragment)
        transaction?.commit()
    }


    private class ReposAdapter(repos: List<Repository>, private val itemListener: ReposItemListener)
        : BaseAdapter() {

        var repos: List<Repository> = repos
            set(repos) {
                field = repos
                notifyDataSetChanged()
            }

        override fun getCount(): Int {
            return repos.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return repos[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val binding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context),
                 R.layout.repo_item, parent, false) as RepoItemBinding
            binding.root.setOnClickListener { itemListener.onClick(repos[position]) }
            binding.setRepo(repos[position])
            return binding.root
        }
    }

    interface ReposItemListener {
        fun onClick(clickedRepo: Repository)
    }

    companion object {
        fun newInstance() = ReposFragment()
    }
}