package com.really.goodnews.model

import com.really.goodnews.service.local.db.UserDatabase

data class Post(var userId: Int,
                var id: Int,
                var title: String?,
                var body: String?, var username: String?, var companyName: String?)