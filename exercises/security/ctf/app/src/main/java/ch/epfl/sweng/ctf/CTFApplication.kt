package ch.epfl.sweng.ctf

import android.app.Application
import ch.epfl.sweng.ctf.repositories.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Main application definition
 * Initializes module dependencies and startup operations
 *
 * @author Alexandre Chau
 */
@Suppress("unused")
class CTFApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val context = this

        // Koin DI: create main module
        val appModule = module {
            single<ChallengesRepository> { ChallengesRepositoryImpl(get(), get()) }
            single<FlagsRepository> { FlagsRepositoryImpl(context) } // this class must be absent in public repo
            single<FruitsRepository> { FruitsRepositoryImpl(context) }
        }

        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }

        Config.registerNotificationChannel(applicationContext)
    }

    companion object {
        init {
            // Load C++ "native-lib" on application startup
            System.loadLibrary("native-lib")
        }
    }
}