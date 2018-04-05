package com.lai.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.lai.news.data.Article
import com.lai.news.data.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_view_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lailee on 04/03/2018.
 */
class NewsAdapter: BaseAdapter, Filterable {
    var context: Context? = null
    var layoutInflater:LayoutInflater? = null
    var originListNews = ArrayList<Article>()
    var filteredListNews = ArrayList<Article>()
    var myInterface: MyInterface

    constructor(myInterface: MyInterface, listNews: ArrayList<Article>) : super() {
        this.myInterface = myInterface
        this.context = this.myInterface.getContext1()
        layoutInflater = LayoutInflater.from(context)
        this.originListNews = listNews
        this.filteredListNews = listNews
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        println("get view: " + position.toString());
        val view : View?
        val vh: ViewHolder?

        val news = filteredListNews[position]

//        if (convertView == null) {
            vh = ViewHolder(layoutInflater!!)
            vh.view.tag = vh
            view = vh.view
//        } else {
//            view = convertView
//            vh = convertView.tag as ViewHolder
//        }

        val sdf = SimpleDateFormat("MMMM dd, HH:mm" , Locale.getDefault())
        vh.updateTime.text = String.format(context!!.getString(R.string.updated_time), sdf.format(news.publishedAt))
        vh.title.text = news.title
        vh.description.text = news.description

        Picasso.with(context).load(news.urlToImage).placeholder(R.drawable.progress_animation).error(R.drawable.icon)
                .into(vh.image)
        vh.detailIcon.setOnClickListener({
            myInterface.onClick(news)
        })
        return view
    }

    override fun getItem(position: Int): Any {
        return filteredListNews[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return filteredListNews.size
    }

    private class ViewHolder(inflater: LayoutInflater){
        val view: View
        val title: TextView
        val image: ImageView
        val description: TextView
        val updateTime: TextView
        val detailIcon: ImageView

        init {
            view = inflater!!.inflate(R.layout.list_view_item, null)
            title = view.tvTitle
            image = view.ivImage
            description = view.tvDescription
            updateTime = view.tvUpdatedTime
            detailIcon = view.ivArrowRight
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterString = constraint.toString().toLowerCase()
                val filterResults = Filter.FilterResults()
                println("performFiltering:" + filterString + ", current news size:" + originListNews.size)

                if (filterString.isEmpty()) {
                    filterResults.values = originListNews
                    filterResults.count = originListNews.size
                    return filterResults
                }

                var newListNews = ArrayList<Article>()

                val iterator = originListNews.iterator()
                iterator.forEach {
                    if (it.description!!.toLowerCase().contains(filterString)) {
                        newListNews.add(it)
                    }
                }
                println("newListNews:" + constraint.toString() + ", filtered news size:" + newListNews.size)
                filterResults.values = newListNews
                filterResults.count = newListNews.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredListNews = results!!.values as ArrayList<Article>
                notifyDataSetChanged()
            }
        }
    }
}