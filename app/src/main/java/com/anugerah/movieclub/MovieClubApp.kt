package com.anugerah.movieclub

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.anugerah.movieclub.core.di.databaseModule
import com.anugerah.movieclub.core.di.networkModule
import com.anugerah.movieclub.core.di.repositoryModule
import com.anugerah.movieclub.core.utils.PreferenceHelper
import com.anugerah.movieclub.di.useCaseModule
import com.anugerah.movieclub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieClubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieClubApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }

        val isDarkMode = PreferenceHelper.getSetting(this)
        if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}