package com.example.flo.Service

//SignUpActivity와 Service를 연결시켜주는 인터페이스
interface SignUpView{
    fun onSignUpLoading()
    fun onSignUpSuccess()
    fun onSignUpFailure(code : Int, message : String)
}

