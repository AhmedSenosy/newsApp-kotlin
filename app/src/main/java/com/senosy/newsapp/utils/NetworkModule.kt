package com.senosy.newsapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.senosy.newsapp.BuildConfig
import com.senosy.newsapp.data.remote.ApiClient
import com.senosy.newsapp.data.remote.EndPoints.APIKEY
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object NetworkModule {
    fun providesJson(): Gson {
        val builder = GsonBuilder()
        return builder.setLenient().create()
    }

    private fun provideOkHttpClientBuilder(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.sslSocketFactory(
            provideSSLSocketFactory()!!,
            (provideTrustManager()[0] as X509TrustManager?)!!
        )
        builder.hostnameVerifier(HostnameVerifier { _: String?, _: SSLSession? -> true })
        builder.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor { chain ->
            val originalHttpUrl: HttpUrl = chain.request().url
            val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api-key", APIKEY).build();
            val requestBuilder: Request.Builder = chain.request().newBuilder()
                .url(url)
            val newRequest = requestBuilder
                .addHeader("Accept", "application/json")
                .addHeader("Content_Type", "multipart/form-data")
                .build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(provideHttpLoggingInterceptor())
        return builder.build()
    }

    fun providesRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClientBuilder())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun providesApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    fun provideSSLSocketFactory(): SSLSocketFactory? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, provideTrustManager(), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sslContext!!.socketFactory
    }

    fun provideTrustManager(): Array<TrustManager?> {
        return arrayOf(
            object : X509TrustManager {
                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }




}