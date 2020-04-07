package com.speech.ui.conversation;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.speech.R;
import com.speech.util.EventObserver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConversationActivityTest {

    @Rule
    public ActivityTestRule<ConversationActivity> activityTestRule = new ActivityTestRule<>(ConversationActivity.class);

    @Test
    public void nativeSpeakerFragment_isDisplayed() {
        onView(withId(R.id.fragment_native_speaker_parent_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void startHint_isDisplayed() {
        onView(withId(R.id.start_speak_hint)).check(matches(isDisplayed()));
    }

    @Test
    public void foreignSpeakerFragment_isDisplayed_onSwipeLeft() {
        onView(withId(R.id.conversation_activity_parent)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.fragment_foreign_speaker_parent_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void fragmentsDisplayCorrect_onEvents() throws InterruptedException {
        EventObserver.Companion.getCommonObserver().postValue(EventObserver.Event.SPEAK_NATIVE_INTENT_FINISH);
        onView(withId(R.id.fragment_foreign_speaker_parent_layout)).check(matches(isDisplayed()));
        Thread.sleep(500);
        EventObserver.Companion.getCommonObserver().postValue(EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH);
        onView(withId(R.id.fragment_native_speaker_parent_layout)).check(matches(isDisplayed()));
    }
}
