package com.speech.ui.conversation;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.speech.R;
import com.speech.util.EventObserver;
import com.speech.viewModel.conversation.Conversation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConversationActivityTest {

    @Rule
    public ActivityTestRule<ConversationActivity> intentsTestRule =
            new ActivityTestRule<>(ConversationActivity.class);

    private static Context context;
    private static Conversation conversation;
    private long speechBtnAnimDuration = 350;

    @BeforeClass
    public static void commonSetup() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Before
    public void setup() {
        conversation = Conversation.Companion.getInstance(context);
    }

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
        Thread.sleep(150);
        EventObserver.Companion.getCommonObserver().postValue(EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH);
        onView(withId(R.id.fragment_native_speaker_parent_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void startHint_hidden_after_translatedText_appeared() throws InterruptedException {
        conversation.getTranslatedTextNullOrEmpty().set(false);
        Thread.sleep(150);
        onView(withId(R.id.start_speak_hint)).check(matches(not(isDisplayed())));
    }

    @Test
    public void speechBtn_enabled_after_translatedText_appeared() throws InterruptedException {
        conversation.getNativeTranslate().set("some text");
        Thread.sleep(speechBtnAnimDuration);
        onView(allOf(withId(R.id.speech_btn),
                isDescendantOfA(withId(R.id.fragment_native_speaker_parent_layout))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void translatedText_hidden_onChangeLanguage() throws InterruptedException {
        conversation.getNativeTranslate().set("some text");
        onView(withId(R.id.english_lang_btn)).perform(click());
        Thread.sleep(speechBtnAnimDuration);
        onView(allOf(withId(R.id.translated_text),
                isDescendantOfA(withId(R.id.fragment_native_speaker_parent_layout))))
                .check(matches(not(isDisplayed())));
    }
}
