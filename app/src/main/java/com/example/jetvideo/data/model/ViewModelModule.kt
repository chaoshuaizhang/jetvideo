package com.example.jetvideo.data.model

import com.example.jetvideo.data.repository.PagedViewModel
import com.example.jetvideo.dto.FeedItemEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelModule {
    @Binds
    abstract fun bindHomeViewModel(vm: FeedViewModel): PagedViewModel<Int, FeedItemEntity>
}