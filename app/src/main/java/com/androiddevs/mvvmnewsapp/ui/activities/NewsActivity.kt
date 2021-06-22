package com.androiddevs.mvvmnewsapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.fragments.BreakingNewsFragment
import com.androiddevs.mvvmnewsapp.ui.fragments.SportsFragment
import com.androiddevs.mvvmnewsapp.ui.viewmodels.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news.*

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private  var newsTypeMap:LinkedHashMap<String,String>
    private var queryMap:LinkedHashMap<String,String>
    private val viewModel: BreakingNewsViewModel by viewModels()
    private var newsTypeInitFlag:Boolean = true
    private var queryInitFlag:Boolean = true

    init {
        newsTypeMap = LinkedHashMap<String,String>()
        newsTypeMap.put("Top Headline","sports")
        newsTypeMap.put("Spor","everything")
        newsTypeMap.put("Teknoloji","everything")
        newsTypeMap.put("Sağlık","everything")

        queryMap = LinkedHashMap<String,String>()
        queryMap.put("Türkiye","tr")
        queryMap.put("Almanya","us")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)




        supportActionBar?.title = "Mvvm News App"


        configureSpinners()
        configureBottomNavigation()

        setUp()
        fetchNewsDataFromServer()






    }
    private fun configureSpinners(){

        val newsTypeAdapter: ArrayAdapter<String> = ArrayAdapter(this@NewsActivity, R.layout.layout_spinner_custom_textview,newsTypeMap.keys.toTypedArray())
        newsTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner_newsType.adapter = newsTypeAdapter

        spinner_newsType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(!newsTypeInitFlag)
                    fetchNewsDataFromServer()
                else
                    newsTypeInitFlag = false
            }

        }

        val queryAdapter: ArrayAdapter<String> = ArrayAdapter(this@NewsActivity,R.layout.layout_spinner_custom_textview,queryMap.keys.toTypedArray())
        queryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner_newsGenre.adapter = queryAdapter
        spinner_newsGenre.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(!queryInitFlag)
                    fetchNewsDataFromServer()
                else
                    queryInitFlag = false
            }

        }
    }
    private fun fetchNewsDataFromServer(){
        val newsType = newsTypeMap.toList().get(spinner_newsType.selectedItemPosition).second
        val newsGenre = queryMap.toList().get(spinner_newsGenre.selectedItemPosition).second

        this.viewModel.getBreakingNews(newsType,newsGenre)
    }
    private fun setUp(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.newsFragmentContainer,BreakingNewsFragment.getInstance()?:BreakingNewsFragment())
        fragmentTransaction.commit()

    }
    private fun configureBottomNavigation(){
        navigation.setOnNavigationItemSelectedListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.BreakingNewsFragment -> {
                    fragmentTransaction.replace(R.id.newsFragmentContainer,BreakingNewsFragment.getInstance()?:BreakingNewsFragment())
                    supportActionBar?.title = "Son Dakika"
                    news_filter_pallete.visibility = View.VISIBLE
                }

            }
            fragmentTransaction.commit()

            true
        }
    }

}
