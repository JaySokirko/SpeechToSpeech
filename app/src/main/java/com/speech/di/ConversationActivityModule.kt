package com.speech.di

import androidx.fragment.app.Fragment
import com.speech.ui.conversation.ForeignSpeakerFragment
import com.speech.ui.conversation.NativeSpeakerFragment
import dagger.Module
import dagger.Provides

@Module
class ConversationActivityModule{

    @Provides
    fun provideFragments(): List<Fragment>{
        return listOf(NativeSpeakerFragment(), ForeignSpeakerFragment())
    }
}