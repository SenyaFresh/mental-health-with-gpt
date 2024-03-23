package ru.edu.hse.mentalhealthwithgpt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.edu.hse.common.AppRestarter
import ru.edu.hse.common.Core
import ru.edu.hse.common.CoreProvider
import ru.edu.hse.common.flow.DefaultLazyFlowLoaderFactory
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.common_impl.DefaultCoreProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext context: Context,
        appRestarter: AppRestarter
    ): CoreProvider {
        // TODO("Provide appRestarter")
        return DefaultCoreProvider(context, appRestarter)
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return Core.globalScope
    }

    @Provides
    @Singleton
    fun provideLazyFlowLoaderFactory(): LazyFlowLoaderFactory {
        return DefaultLazyFlowLoaderFactory(Dispatchers.IO)
    }


}