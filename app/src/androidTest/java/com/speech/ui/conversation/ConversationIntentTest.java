package com.speech.ui.conversation;

import android.speech.RecognizerIntent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.speech.R;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ConversationIntentTest {

    @Rule
    public IntentsTestRule<ConversationActivity> intentsTestRule =
            new IntentsTestRule<>(ConversationActivity.class);

    private static ViewInteraction startSpeakBtnInteraction;

    @BeforeClass
    public static void commonSetup() {
        startSpeakBtnInteraction = onView(allOf(withId(R.id.start_speak_btn),
                isDescendantOfA(allOf(withId(R.id.parent_layout),
                        isDescendantOfA(withId(R.id.fragment_native_speaker_parent_layout))))));
    }

    @Test
    public void startSpeakBtn_launch_speechIntent() {
        startSpeakBtnInteraction.perform(click());
        intended(hasAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
    }
}
