package com.speech.di

import androidx.fragment.app.Fragment
import com.speech.ui.speech.ForeignSpeakerFragment
import com.speech.ui.speech.NativeSpeakerFragment
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule{

    @Provides
    fun provideFragments(): List<Fragment>{
        return listOf(NativeSpeakerFragment(), ForeignSpeakerFragment())
    }
}