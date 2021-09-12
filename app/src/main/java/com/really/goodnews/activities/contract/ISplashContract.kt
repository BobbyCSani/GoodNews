package com.really.goodnews.activities.contract

import com.really.goodnews.model.User
import com.really.goodnews.model.UserRemote
import com.really.goodnews.service.local.db.UserDatabase

interface ISplashContract {

    interface Presenter {
        fun getRemoteUser(userDb: UserDatabase)
        fun saveUserToLocal(list: ArrayList<UserRemote>, userDb: UserDatabase)
    }

    interface View {
        fun moveToMainActivity()
    }
}