package com.really.goodnews.model

import com.really.goodnews.service.local.db.UserDatabase

data class Comment(var postId: Int?,
                   var userId: Int?,
                   var id: String?,
                   var name: String?,
                   var email: String?,
                   var body: String?, var author: String?)