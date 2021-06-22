package com.androiddevs.mvvmnewsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.ui.activities.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.viewmodels.BreakingNewsViewModel
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.view.*

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {


    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var newsRecylerView: RecyclerView
    val emptyList = ArrayList<Article>()


    val TAG = "BreakingNewsFragment"

    companion object {
        var newHomeFragmentReference: BreakingNewsFragment? = null

        public fun getInstance(): BreakingNewsFragment? {
            if (newHomeFragmentReference == null)
                newHomeFragmentReference = BreakingNewsFragment()

            return newHomeFragmentReference
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it)[BreakingNewsViewModel::class.java]
        }

    }

    override fun onViewCreated(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(R.layout.fragment_breaking_news, container, false)

        newsRecylerView = view.rvBreakingNews
        newsAdapter = NewsAdapter(activity as Context, emptyList, viewModel)
        newsRecylerView.adapter = newsAdapter
        newsRecylerView.layoutManager = LinearLayoutManager(activity)

        observeData()

        return view


    }
    private fun observeData(){
        this.viewModel.breakingNews.observe(activity as NewsActivity, Observer {





            newsRecylerView.visibility = View.VISIBLE


        })



    }
}