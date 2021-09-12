package com.really.goodnews.activities.presenter

import com.really.goodnews.activities.contract.ISplashContract
import com.really.goodnews.model.User
import com.really.goodnews.model.UserRemote
import com.really.goodnews.service.consumer.UserApiManager
import com.really.goodnews.service.local.db.UserDatabase
import com.really.goodnews.utility.toLocal

class SplashPresenter(private val view: ISplashContract.View): ISplashContract.Presenter {

    private val userApi by lazy { UserApiManager.instance }

    override fun getRemoteUser(userDb: UserDatabase) {
        userApi.getAllUser { response ->
            if (response.isNullOrEmpty()) view.moveToMainActivity()
            else saveUserToLocal(response, userDb)
        }
    }

    override fun saveUserToLocal(list: ArrayList<UserRemote>, userDb: UserDatabase) {
        list.forEach {
            userDb.userDao().insertUsers(User(it.id, it.username, it.name, it.company?.name, it.email))
        }
        val list = userDb.userDao().getUsers()
        view.moveToMainActivity()
    }
}