package com.speech.di

import com.speech.ui.conversation.ConversationActivity
import dagger.Component

@Component(modules = [ConversationActivityModule::class])
interface ConversationActivityComponent {
    fun inject(conversationActivity: ConversationActivity)
}