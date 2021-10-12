package com.ozgurerdogan.kotlin_composeretrofit.service

import com.ozgurerdogan.kotlin_composeretrofit.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
//"https://api.nomics.com/v1/prices?key=1d349761d06c57485b9d6b30424fb8cf21f0fa45"
    //BASE_URL="https://api.nomics.com/v1/"
    //Extention="prices?key=1d349761d06c57485b9d6b30424fb8cf21f0fa45"

    @GET("prices?key=1d349761d06c57485b9d6b30424fb8cf21f0fa45")
    fun getData(): Call<List<CryptoModel>>
}