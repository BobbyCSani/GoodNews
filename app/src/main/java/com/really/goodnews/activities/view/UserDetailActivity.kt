package com.really.goodnews.activities.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.really.goodnews.activities.adapter.AlbumAdapter
import com.really.goodnews.activities.contract.IUserContract
import com.really.goodnews.activities.presenter.UserPresenter
import com.really.goodnews.databinding.ActivityUserDetailBinding
import com.really.goodnews.model.Album
import com.really.goodnews.model.Photo
import com.really.goodnews.model.UserRemote

class UserDetailActivity: AppCompatActivity(), IUserContract.View, AlbumAdapter.Listener, AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var presenter: IUserContract.Presenter
    private lateinit var adapter: AlbumAdapter
    private val listAlbum = arrayListOf<Album>()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = UserPresenter(this)
        adapter = AlbumAdapter(listAlbum, this)
        binding.userDetailRecycler.layoutManager = LinearLayoutManager(this)
        binding.userDetailRecycler.adapter = adapter
        binding.userDetailTopRefresh.isRefreshing = true
        id = intent.getIntExtra("userId", 0)
        id?.let {
            presenter.getUserById(it)
            presenter.getPostsCount(it)
            presenter.getAlbumByUser(it)
        }
        setListener()
    }

    private fun setListener(){
        binding.userDetailAppBar.addOnOffsetChangedListener(this)
        binding.userDetailBack.setOnClickListener { onBackPressed() }
        binding.userDetailTopRefresh.setOnRefreshListener {
            id?.let {
                presenter.getPostsCount(it)
                presenter.getAlbumByUser(it)
            }
        }
        binding.userDetailPostGuide.setOnClickListener {
            Intent(this, PostActivity::class.java).apply {
                putExtra("userId", id)
                startActivity(this)
            }
        }
    }

    override fun showUser(user: UserRemote) {
        runOnUiThread {
            binding.userDetailTopRefresh.isRefreshing = false
            binding.userDetailName.text = user.name
            binding.userDetailEmail.text = user.email?.lowercase()
            binding.userDetailCompanyName.text = user.company?.name
            binding.userDetailAddress.text = buildString {
                append(user.address?.suite)
                append(", ")
                append(user.address?.street)
                append(", ")
                append(user.address?.zipcode)
            }
        }
    }

    override fun showAlbums(list: ArrayList<Album>, qty: Int) {
        runOnUiThread {
            binding.userDetailTopRefresh.isRefreshing = false
            listAlbum.clear()
            listAlbum.addAll(list)
            adapter.notifyDataSetChanged()
            binding.userDetailAlbumCount.text = qty.toString()
        }
    }

    override fun setPostCount(count: String) {
        runOnUiThread { binding.userDetailPostCount.text = count }
    }

    override fun setPhoto(list: ArrayList<Photo>, position: Int) {
        runOnUiThread {
            listAlbum[position].photos= list
            adapter.notifyItemChanged(position)
        }
    }

    override fun showError() {
        runOnUiThread {
            binding.userDetailTopRefresh.isRefreshing = false
            Toast.makeText(this, "Something Error", Toast.LENGTH_LONG).show()
        }
    }

    override fun getPhoto(albumId: Int, position: Int) {
        presenter.getPhotoByAlbum(albumId, position)
    }

    override fun onclickPhoto(albumPosition: Int, photoPosition: Int) {
        PhotoDetailDialog(listAlbum[albumPosition].photos ?: arrayListOf(), photoPosition).show(supportFragmentManager, "")
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.userDetailTopRefresh.isEnabled = verticalOffset == 0
    }
}