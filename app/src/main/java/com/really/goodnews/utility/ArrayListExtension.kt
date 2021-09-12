package com.really.goodnews.utility

import com.really.goodnews.model.User
import com.really.goodnews.model.UserRemote

fun ArrayList<UserRemote>.toLocal(): Array<User>{
    val locals = arrayOf<User>()
    forEach {
        locals.plus(User(it.id, it.username, it.name, it.company?.name, it.email))
    }
    return locals
}