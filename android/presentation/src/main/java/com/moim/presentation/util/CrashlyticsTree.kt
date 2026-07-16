package com.moim.presentation.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR && t != null) {
            FirebaseCrashlytics.getInstance().recordException(t)
        } else if (priority == Log.ERROR) {
            FirebaseCrashlytics.getInstance().log(message)
        }
    }
}