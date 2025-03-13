package com.example.books

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import com.example.books.di.appModule
import org.koin.android.ext.koin.androidContext

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            //androidLogger(if (BuildConfig.DEBUG) Level.INFO else Level.NONE)

            androidContext(this@BookApplication)

            modules(
                appModule,
            )
        }
    }
}