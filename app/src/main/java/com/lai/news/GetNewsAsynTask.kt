package com.lai.news

import android.content.Context
import android.os.AsyncTask
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * Created by lailee on 04/03/2018.
 */
class GetNewsAsyncTask: AsyncTask<String, String, String> {

    var context: Context? = null
    var listNews = ArrayList<News>()
    var adapter:NewsAdapter? = null
    var mWeakActivity: WeakReference<NewsFragment>? = null
    var fragment:NewsFragment? = null
    var page:Int? = 1

    constructor(context: Context, listNews: ArrayList<News>, adapter: NewsAdapter, newsFragment: NewsFragment, page: Int) {
        this.context = context
        this.listNews = listNews
        this.adapter = adapter
        mWeakActivity = WeakReference(newsFragment)
        this.fragment = mWeakActivity!!.get()!!
        this.page = page
    }
    override fun onPreExecute() {
        fragment!!.swipeRefreshLayout!!.isRefreshing = true
    }

    override fun doInBackground(vararg params: String?): String {
        if (BuildConfig.DEBUG) println("Url:" + params[0])
        try{
            val urlConnect = URL(params[0]).openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 1000*10

            var inString = convertStreamToString(urlConnect.inputStream)

            publishProgress(inString)
        } catch(ex:Exception){}

        return ""
    }

    override fun onProgressUpdate(vararg values: String?) {
        try{
            var gsonBuilder = GsonBuilder()
            var gson = gsonBuilder.create()
            var news:News = gson.fromJson(values[0], News::class.java)
            if(news.status.equals("ok")) {
                if(news.totalResults!! > 0) {
                    val articles = news.articles
                    for(article:Article in articles!!) {
                        var sourceName:String? = article.source!!.name
                        var author:String? = article.author
                        var title:String? = article.title
                        var description:String? = article.description
                        var url:String? = article.url
                        var urlToImage:String? = article.urlToImage
                        var publishedAt: Date? = article.publishedAt
                        var checkNull = false
                        if(sourceName == null || author == null || title == null || description == null || url == null
                                || urlToImage == null || publishedAt == null) {
                            checkNull = true
                        }
                        if (!checkNull) {
                            listNews.add(News(sourceName!!, author!!, title!!, description!!, url!!, urlToImage!!, publishedAt!!, "data"))
                        }
                        //else originListNews.add(News("Source", "Author", "Title", "Description", "URL", "imageURL", Date(), "data"))
                    }
                } else {
                    Toast.makeText(context!!, R.string.no_data, Toast.LENGTH_SHORT).show()
                }
            } else if(status.equals("error")) {
                Toast.makeText(context!!, R.string.retrieve_data_error, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context!!, R.string.retrieve_data_error, Toast.LENGTH_SHORT).show()
            }
        } catch(ex:Exception){
            println(ex.localizedMessage)
        }
    }
    override fun onPostExecute(result: String?) {
        adapter!!.notifyDataSetChanged()
        fragment!!.swipeRefreshLayout!!.isRefreshing = false
    }

    fun convertStreamToString(inputStream: InputStream):String{
        val buffferReader = BufferedReader(InputStreamReader(inputStream))
        var line:String
        var AllString:String = ""
        try{
            do{
                line = buffferReader.readLine()
                if(line != null) AllString += line
            } while (line != null)
            inputStream.close()
        } catch(ex:Exception){}
        return AllString
    }
}