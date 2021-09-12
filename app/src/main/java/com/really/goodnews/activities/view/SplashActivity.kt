package com.really.goodnews.activities.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.really.goodnews.activities.contract.ISplashContract
import com.really.goodnews.activities.presenter.SplashPresenter
import com.really.goodnews.databinding.ActivitySplashBinding
import com.really.goodnews.service.local.db.UserDatabase
import com.really.goodnews.utility.needRemoteUser

class SplashActivity: AppCompatActivity(), ISplashContract.View {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var presenter: ISplashContract.Presenter
    private lateinit var userDb: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = SplashPresenter(this)
        userDb = Room.databaseBuilder(this, UserDatabase::class.java, "user_db").build()
        if (needRemoteUser) presenter.getRemoteUser(userDb)
    }

    override fun moveToMainActivity() {
        startActivity(Intent(this, PostActivity::class.java))
    }

}