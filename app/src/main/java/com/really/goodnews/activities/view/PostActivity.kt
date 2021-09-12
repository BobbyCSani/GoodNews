package com.really.goodnews.activities.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.really.goodnews.R
import com.really.goodnews.activities.adapter.PostAdapter
import com.really.goodnews.activities.contract.IPostContract
import com.really.goodnews.activities.presenter.PostPresenter
import com.really.goodnews.databinding.ActivityMainBinding
import com.really.goodnews.model.Post
import com.really.goodnews.model.User
import com.really.goodnews.service.local.db.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity(), IPostContract.View, PostAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: IPostContract.Presenter
    private lateinit var adapter: PostAdapter
    private lateinit var userDb: UserDatabase

    private val listPost = arrayListOf<Post>()
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PostPresenter(this)
        userDb = Room.databaseBuilder(this, UserDatabase::class.java, "user_db").build()
        adapter = PostAdapter(listPost, this, userDb)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.refresh.isRefreshing = true
        userId = intent.getIntExtra("userId", 0)
        if (userId != 0) presenter.getPostByUser(userId)
        else presenter.getAllPost()
        setListener()
    }

    private fun setListener(){
        binding.refresh.setOnRefreshListener {
            if (userId != 0) presenter.getPostByUser(userId)
            else presenter.getAllPost()
        }
    }

    override fun showData(list: ArrayList<Post>) {
        super.showData(list)
        runOnUiThread {
            binding.refresh.isRefreshing = false
            listPost.clear()
            listPost.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showEmpty() {
        super.showEmpty()
        runOnUiThread {
            Toast.makeText(this, "Data Empty", Toast.LENGTH_LONG).show()
        }
    }

    override fun setUsername(user: User, position: Int) {
        runOnUiThread {
            listPost[position].username = user.username
            listPost[position].companyName = user.companyName
            adapter.notifyItemChanged(position)
        }
    }

    override fun onClickPost(post: Post) {
        Intent(this, PostDetailActivity::class.java).apply {
            putExtra("postId", post.id)
            putExtra("username", post.username)
            putExtra("userId", post.userId)
            startActivity(this)
        }
    }

    override fun onClickUser(userId: Int) {
        Intent(this, UserDetailActivity::class.java).apply {
            putExtra("userId", userId)
            startActivity(this)
        }
    }

    override fun sharePost(post: Post) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            val shareUrl = "https://www.goodnews.com/post/${post.id}"
            val text = "${post.title}\n\n${post.body}\n$shareUrl"
            putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(this, "Share Post"))
        }
    }

    override fun getUser(id: Int, position: Int) {
        GlobalScope.launch {
            presenter.getUsername(userDb, id, null, position)
        }
    }
}