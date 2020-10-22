package com.design.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.design.myapplication.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NewsSplashActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_news_activity)
        Observable.interval(3, TimeUnit.MILLISECONDS)
            .take(1)
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { along -> navigateToHome() }
    }

    private fun navigateToHome() {
        Log.i(TAG,"Navigate From Splash To HomeActivity!!!!!!")
        val newsSplashActivity = this
        val homeIntent = Intent(newsSplashActivity, NewsHomeActivity::class.java)
        startActivity(homeIntent)
    }


    companion object {
        private val TAG: String = NewsSplashActivity::class.java.simpleName
    }
}
