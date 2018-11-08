package com.artist.web.bookstore.booklist;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import com.artist.web.bookstore.R;
import com.artist.web.bookstore.detail.BookPagerActivity;
import com.artist.web.bookstore.espresso.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

/**
 * https://stackoverflow.com/questions/30155227/espresso-how-to-wait-for-some-time1-hour
 */
@RunWith(AndroidJUnit4.class)
public class BookListActivityTest {

    private static final String AUTHOR = "Mike Riley";
    private final static int RECIPE_LIST_SCROLL_POSITION = 1;
    private long waitingTime = DateUtils.MINUTE_IN_MILLIS;
    @Rule
    public final IntentsTestRule<BookListActivity> mBookListActivityIntentsTestRule=
            new IntentsTestRule<>(BookListActivity.class);


    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Test
    public void onClickBook_displayPagerActivityInPortrait() {

        //Make sure espresso dont timed out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime*2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime*2,TimeUnit.MILLISECONDS);

        //Now we wait
        IdlingRegistry.getInstance().register(SimpleIdlingResource.getIdlingResource(waitingTime));
        //Start
        onView(withId(R.id.rv_books)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        Context context = InstrumentationRegistry.getTargetContext();
        Boolean isTwoPane = context.getResources().getBoolean(R.bool.two_pane_layout);

        if(isTwoPane){
            onView(withId(R.id.detail_fragment_container)).check(matches(isDisplayed()));
        }else{
            intended(hasComponent(BookPagerActivity.class.getName()));
        }
    }

    @After
    public void unregisterIdlingResource() {

        IdlingRegistry.getInstance().unregister(SimpleIdlingResource.getIdlingResource(waitingTime));
    }
}