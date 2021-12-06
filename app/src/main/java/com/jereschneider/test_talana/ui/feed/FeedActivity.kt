package com.jereschneider.test_talana.ui.feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager

import com.jereschneider.test_talana.R
import com.jereschneider.test_talana.databinding.ActivityFeedBinding
import com.jereschneider.test_talana.models.Post
import com.jereschneider.test_talana.ui.details.DetailsActivity
import com.jereschneider.test_talana.utils.observe
import android.view.Menu
import com.jereschneider.test_talana.models.Favourite

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    private var showOnlyFav = false
    private lateinit var listPost : List<Post>
    private lateinit var listFav : List<Favourite>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Feed"

        updateAnim(R.raw.loading, true)
        setupRecyclerView()

        viewModel.getUser()
        viewModel.getPosts()

        observe(viewModel.favourites){
            listFav = it
            (binding.postList.adapter as FeedListAdapter).updateFavourite(it)
        }

        observe(viewModel.users){
            (binding.postList.adapter as FeedListAdapter).updateUser(it)
        }

        observe(viewModel.posts){
            if(!it.isNullOrEmpty()){
                listPost = it
                binding.animation.visibility = View.INVISIBLE
                (binding.postList.adapter as FeedListAdapter).updatePost(it)
            }
            else
                updateAnim(R.raw.failure, false)
        }

        observe(viewModel.error){
            if (binding.postList.size < 1){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                updateAnim(R.raw.failure, false)
            }
        }
    }

    private fun updateAnim(raw : Int, loop : Boolean){
        binding.animation.visibility = View.VISIBLE
        binding.animation.setAnimation(raw)
        binding.animation.loop(loop)
        binding.animation.playAnimation()
    }

    private fun setupRecyclerView() {
        binding.postList.layoutManager = LinearLayoutManager(this)
        binding.postList.adapter = FeedListAdapter(
            this,emptyList(),emptyList(),emptyList(),
            ::onSelect,
            ::onSetFav)
    }
    private fun onSetFav(idPost : Long) = viewModel.setFavourite(idPost)

    private fun onSelect(item: Post) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ID_POST, item.id)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feed_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.folder_fav -> {
                if (!listPost.isNullOrEmpty()){
                    showOnlyFav = !showOnlyFav
                    val listPostFav = listPost.filter { post ->
                        listFav.find { favourite -> favourite.id_post == post.id } != null
                    }
                    if (showOnlyFav){
                        supportActionBar?.title = "Favoritos"
                        (binding.postList.adapter as FeedListAdapter).updatePost(listPostFav)
                    }
                    else{
                        supportActionBar?.title = "Feed"
                        (binding.postList.adapter as FeedListAdapter).updatePost(listPost)
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}