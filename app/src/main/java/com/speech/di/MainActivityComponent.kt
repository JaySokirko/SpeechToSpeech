package com.speech.di

import com.speech.ui.speech.MainActivity
import dagger.Component

@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}