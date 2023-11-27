package com.example.pizzapp

import com.example.pizzapp.models.CredentialsLogin
import com.example.pizzapp.models.TokenResponse
import com.example.pizzapp.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.net.ssl.TrustManager
import okhttp3.OkHttpClient
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.X509Certificate
interface ApiService {
    @POST("User/createUser")
    fun crearUsuario(@Body usuario: User): Call<TokenResponse>

    @POST("User/loginUser")
    fun login(@Body credenciales: CredentialsLogin): Call<TokenResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://10.0.2.2:8081/v1/"

    // Crea un trust manager que no valide la cadena de certificados
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate?> = arrayOfNulls(0)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
    })

    // Instala el all-trusting trust manager
    val sslContext: SSLContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    // Crea un sslSocketFactory para usarlo con nuestro trust manager que confía en todos
    val sslSocketFactory = sslContext.socketFactory

    // Cliente de OkHttp que confía en todos los SSL certs y acepta todos los hostnames
    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier(HostnameVerifier { _, _ -> true })
        .build()

    // Retrofit builder
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient) // Usa el cliente OkHttp que confía en todos
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}