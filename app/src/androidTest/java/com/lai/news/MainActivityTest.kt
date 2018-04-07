package com.lai.news

import android.icu.util.TimeUnit
import android.support.test.espresso.Espresso.onView
import android.support.test.rule.ActivityTestRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import android.support.test.espresso.util.HumanReadables
import android.support.test.espresso.PerformException
import android.support.test.espresso.util.TreeIterables
import android.support.test.espresso.UiController
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

class MainActivityTest {

    @get:Rule
    public var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onCreate() {
        onView(isRoot()).perform(waitId(R.id.tvTitle, java.util.concurrent.TimeUnit.SECONDS.toMillis(1)))
    }

    /** Perform action of waiting for a specific view id.  */
    fun waitId(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                        .withActionDescription(this.description)
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(TimeoutException())
                        .build()
            }
        }
    }
}