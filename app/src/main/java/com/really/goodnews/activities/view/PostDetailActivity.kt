package com.really.goodnews.activities.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.appbar.AppBarLayout
import com.really.goodnews.activities.adapter.CommentAdapter
import com.really.goodnews.activities.contract.IPostContract
import com.really.goodnews.activities.presenter.PostPresenter
import com.really.goodnews.databinding.ActivityPostDetailBinding
import com.really.goodnews.model.Comment
import com.really.goodnews.model.Post
import com.really.goodnews.model.User
import com.really.goodnews.service.local.db.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDetailActivity: AppCompatActivity(), IPostContract.View, AppBarLayout.OnOffsetChangedListener {

    private lateinit var presenter: IPostContract.Presenter
    private lateinit var adapter: CommentAdapter
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var userDb: UserDatabase

    private val listComment = arrayListOf<Comment>()
    private var id = 1
    private var username = ""
    private var userId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PostPresenter(this)
        userDb = Room.databaseBuilder(this, UserDatabase::class.java, "user_db").build()
        adapter = CommentAdapter(listComment)
        binding.postDetailRecycler.layoutManager = LinearLayoutManager(this)
        binding.postDetailRecycler.adapter = adapter
        checkIntent()
        setListener()
        binding.postDetailTopRefresh.isRefreshing = true
        presenter.getPostById(id)
        presenter.getCommentByPostId(id)
        binding.postDetailAppBar.addOnOffsetChangedListener(this)
    }

    private fun checkIntent(){
        id = intent.data?.pathSegments?.get(1)?.toInt() ?: intent.getIntExtra("postId", 0)
        username = intent.getStringExtra("username") ?: ""
        userId = intent.getIntExtra("userId", 0)
    }

    private fun setListener(){
        binding.postDetailBack.setOnClickListener { onBackPressed() }
        binding.postDetailTopRefresh.setOnRefreshListener {
            presenter.getPostById(id)
            presenter.getCommentByPostId(id)
        }
        binding.postDetailUsername.setOnClickListener {
            Intent(this, UserDetailActivity::class.java).apply {
                putExtra("userId", userId)
                startActivity(this)
            }
        }
    }

    override fun showPost(post: Post) {
        super.showPost(post)
        runOnUiThread {
            binding.postDetailTopRefresh.isRefreshing = false
            binding.postDetailTitle.text = post.title
            binding.postDetailBody.text = post.body
            binding.postDetailUsername.text = "Created by ${username}"
        }
    }

    override fun showComments(list: ArrayList<Comment>) {
        super.showComments(list)
        runOnUiThread {
            binding.postDetailTopRefresh.isRefreshing = false
            listComment.clear()
            listComment.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.postDetailTopRefresh.isEnabled = verticalOffset == 0
        binding.postDetailBottomRefresh.isEnabled = verticalOffset != 0
    }
}