package com.example.githubrepos.data.source.remote

import android.os.AsyncTask
import android.util.Log
import com.example.githubrepos.data.Repository
import com.example.githubrepos.data.remote.ReposDataSource
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ReposRemoteDataSource private constructor() : ReposDataSource {

//    private val REPOS_SERVICE_DATA = LinkedHashMap<String, Repository>()
    private val REPOS_SERVICE_DATA = ArrayList<Repository>()
    private lateinit var mCallback: ReposDataSource.LoadReposCallback

    override fun getRepos(callback: ReposDataSource.LoadReposCallback) {
        mCallback = callback
        AsyncGithubRequest().execute("https://api.github.com/users/Neos21/repos?per_page=100")
    }

    companion object {

        private lateinit var INSTANCE: ReposRemoteDataSource
        private var needsNewInstance = true

        @JvmStatic fun getInstance(): ReposRemoteDataSource {
            if (needsNewInstance) {
                INSTANCE = ReposRemoteDataSource()
                needsNewInstance= false
            }
            return INSTANCE
        }
    }

    inner class AsyncGithubRequest : AsyncTask<String, String, String>() {

        private fun getJsonStr(url: String?): String? {
            Log.i("SLY", "getJsonStr() url : " + url)
            var connection: HttpsURLConnection? = null
            var reader: BufferedReader? = null
            var buffer: StringBuffer? = null
            try {
                val url = URL(url)
                connection =url.openConnection() as HttpsURLConnection
                connection.connect()

                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    Log.i("SLY", "getJsonStr() line : " + line)
                    buffer.append(line)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
            }
            return buffer.toString()
        }

        private fun parseJSON(jsonStr: String?): ArrayList<Triple<String, String, String>> {
            val jsonArray = JSONArray(jsonStr)
            val repoResources = ArrayList<Triple<String, String, String>>()
            for (i_obj in 0..jsonArray.length() - 1) {
                val jsonObject = jsonArray.getJSONObject(i_obj)
                val title = jsonObject.getString("name")
//                val url = jsonObject.getString("url")
                val htmlURL = jsonObject.getString("html_url")
                val readmeURL = htmlURL + "/blob/master/README.md"
                Log.i("SLY", "title: " + title)
                Log.i("SLY", "html_url: " + htmlURL)
                Log.i("SLY", "readme_url: " + readmeURL)
//                val readmeJsonStr = getJsonStr(url + "/readme")
//                val readmeJsonArray = JSONArray(readmeJsonStr)
//                val readmeJsonObject = readmeJsonArray.getJSONObject(0)
//                val readmeURL = readmeJsonObject.getString("url")
                repoResources.add(Triple(title, htmlURL, readmeURL))
            }
            return repoResources
        }

        override fun doInBackground(vararg params: String?): String? {
            return getJsonStr(params[0])
        }

        override fun onPostExecute(result: String?) {
            val repoResouces = parseJSON(result)
            for (resouse in repoResouces) {
                REPOS_SERVICE_DATA.add(Repository(resouse.first, resouse.second, resouse.third))
            }
            mCallback.onReposLoaded(REPOS_SERVICE_DATA)
        }

    }
}

