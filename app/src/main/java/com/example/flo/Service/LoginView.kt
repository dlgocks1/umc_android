package com.example.flo.Service

import com.example.flo.dataclass.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth : Auth)
    fun onLoginFailure(code : Int, message : String)
}