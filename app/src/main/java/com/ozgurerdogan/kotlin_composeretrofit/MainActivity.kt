package com.ozgurerdogan.kotlin_composeretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozgurerdogan.kotlin_composeretrofit.model.CryptoModel
import com.ozgurerdogan.kotlin_composeretrofit.service.CryptoAPI
import com.ozgurerdogan.kotlin_composeretrofit.ui.theme.Kotlin_ComposeRetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_ComposeRetrofitTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    val BASE_URL="https://api.nomics.com/v1/"

    var cryptoModels=remember { mutableStateListOf<CryptoModel>() }

    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    val call=retrofit.getData()

    call.enqueue(object :Callback<List<CryptoModel>>{

        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful){
                response.body()?.let {
                    cryptoModels.addAll(it)
                }
            }
        }


        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }

    })

    Scaffold(topBar = { AppBar()}){
        CryptoList(cryptos = cryptoModels)
    }

}

@Composable
fun CryptoList(cryptos:List<CryptoModel>){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptos){crypto->
            CryptoRow(crypto = crypto)
        }
    }
}

@Composable
fun CryptoRow(crypto:CryptoModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)) {
        Text(text = crypto.currency,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp))

        Text(text = crypto.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(4.dp)
        )
    }

}

@Composable
fun AppBar(){
    TopAppBar(contentPadding = PaddingValues(10.dp)) {
        Text(text = "Retrofit Compose",fontSize = 26.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Kotlin_ComposeRetrofitTheme {
        CryptoRow(CryptoModel("btc","123456"))
    }
}

