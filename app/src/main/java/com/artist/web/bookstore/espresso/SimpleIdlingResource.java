package com.artist.web.bookstore.espresso;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by User on 30-Apr-18.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private static volatile ResourceCallback mCallback;
    private static SimpleIdlingResource mIdlingResource;

    private final long startTime;
    private final long waitingTime;

    private SimpleIdlingResource(long waitingTime){
        this.startTime = System.currentTimeMillis();
        this.waitingTime = waitingTime;
    }

    // Idleness is controlled with this boolean.
    private static AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     *
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public static void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }

    public static IdlingResource getIdlingResource(long waitingTime) {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource(waitingTime);
        }
        return mIdlingResource;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - startTime;
        boolean idle =(elapsed >=waitingTime);
        if(idle){
            mCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }
}
