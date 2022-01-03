package com.anugerah.movieclub.core.di

import androidx.room.Room
import com.anugerah.movieclub.core.BuildConfig
import com.anugerah.movieclub.core.data.source.MovieRepository
import com.anugerah.movieclub.core.data.source.local.LocalDataSource
import com.anugerah.movieclub.core.data.source.local.room.MovieDatabase
import com.anugerah.movieclub.core.data.source.remote.RemoteDataSource
import com.anugerah.movieclub.core.data.source.remote.network.ApiService
import com.anugerah.movieclub.core.domain.repository.IMovieRepository
import com.anugerah.movieclub.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.DB_PASS.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    val certificatePinner = CertificatePinner.Builder()
        .add(BuildConfig.BASE_DOMAIN, BuildConfig.SSL_PIN_1)
        .add(BuildConfig.BASE_DOMAIN, BuildConfig.SSL_PIN_2)
        .build()

    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}