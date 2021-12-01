package com.example.flo.dataclass

import com.google.gson.annotations.SerializedName

data class Auth(@SerializedName("userIdx")val userIdx: Int,
                @SerializedName("jwt")val jwt : String
                )


//@SerializedName("isSuccess") Json으로 오는 키값을 지정할수 있다.
//이름은 같아도 타입이 다르면 오류
data class AuthResponse(@SerializedName("isSuccess")val isSuccess: Boolean,
                        @SerializedName("code")val code:Int,
                        @SerializedName("message")val message: String,
                        @SerializedName("result")val result : Auth?
                        )



//users의 reponse json 값
