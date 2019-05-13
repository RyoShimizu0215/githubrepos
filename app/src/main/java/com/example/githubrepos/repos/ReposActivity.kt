package com.example.githubrepos.repos

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import com.example.githubrepos.R
import com.example.githubrepos.data.Injection
import com.google.android.material.snackbar.Snackbar

class ReposActivity : AppCompatActivity() {

    private lateinit var mReposPresenter: ReposPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repos_act)

        if (!isConnected(applicationContext)) {
            Snackbar.make(window.decorView, "Not connected to network", Snackbar.LENGTH_LONG).show()
            return
        }

        val reposFragment = supportFragmentManager.findFragmentById(R.id.container)
            as ReposFragment? ?: ReposFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.container)
        }

        mReposPresenter =
            ReposPresenter(
                Injection.provideReposRepository(applicationContext),
                reposFragment
            )
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.getActiveNetworkInfo()
        if (networkInfo != null) {
            return cm.getActiveNetworkInfo().isConnected()
        }
        return false
    }

    fun AppCompatActivity.replaceFragmentInActivity(fragment: androidx.fragment.app.Fragment, @IdRes frameId: Int) {
        supportFragmentManager.transact {
            replace(frameId, fragment)
        }
    }

    private inline fun androidx.fragment.app.FragmentManager.transact(action: androidx.fragment.app.FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            action()
        }.commit()
    }
}
