package com.speech.service.translation

interface ITranslator {
    fun getTranslatedText(text: String, targetLanguage: String): String
}