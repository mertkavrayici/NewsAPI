package com.androiddevs.mvvmnewsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.ui.viewmodels.BreakingNewsViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter (val context: Context, var newsList: List<Article>, val viewModel: BreakingNewsViewModel): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    var formatPattern = "yyyy-MM-dd'T'HH:mm:ss"
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description

            var formattedTime=setTime(differ.currentList.get(position).publishedAt)

            tvPublishedAt.text = formattedTime

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setTime(date:String?):String{

        val simpleDateFormat=SimpleDateFormat(formatPattern)
        simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT+3:00")
        val outputDate=SimpleDateFormat(" dd/MM/yyyy HH:mm")
        outputDate.timeZone= TimeZone.getDefault()
        var fromTime=simpleDateFormat.parse(date)
        var toTime=outputDate.format(fromTime!!)
        return toTime.toString()
    }


    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}













