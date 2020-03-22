package com.speech.ui.speech

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.di.DaggerMainActivityComponent
import com.speech.di.MainActivityModule
import com.speech.ui.adapters.FragmentPagerAdapter
import com.speech.util.EventObserver
import com.speech.util.FOREIGN_SPEAKER_FRAGMENT
import com.speech.util.NATIVE_SPEAKER_FRAGMENT
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentList: List<@JvmSuppressWildcards Fragment>
    private lateinit var backgroundAnimation: AnimationDrawable
    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule())
            .build()
            .inject(this)

        setupAnimatedBackground()
        setupFragmentPagerAdapter()

        EventObserver.commonObserver.observe(this, Observer { event ->
            if (event == EventObserver.Event.SPEAK_NATIVE_INTENT_FINISH) {
                view_pager.setCurrentItem(FOREIGN_SPEAKER_FRAGMENT, true)
            }
            if (event == EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH) {
                view_pager.setCurrentItem(NATIVE_SPEAKER_FRAGMENT, true)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation.stop()
    }

    private fun setupFragmentPagerAdapter() {
        val adapter = FragmentPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.adapter = adapter
    }

    private fun setupAnimatedBackground() {
        backgroundAnimation = parent_layout.background as AnimationDrawable
        backgroundAnimation.setEnterFadeDuration(5000);
        backgroundAnimation.setExitFadeDuration(2000);
    }
}
