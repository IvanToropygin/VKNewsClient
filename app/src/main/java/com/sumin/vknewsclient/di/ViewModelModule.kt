package com.sumin.vknewsclient.di

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.presentation.mainScreen.MainViewModel
import com.sumin.vknewsclient.presentation.newsScreen.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}