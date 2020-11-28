package com.umang.stumate.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.umang.stumate.R
import com.umang.stumate.general.HomeActivity
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000 //3 seconds
    private var mDelayHandler: Handler? = null
    private var progressBarStatus = 0
    var dummy:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppPreferences.init(this)

       /* progressBar: Prog = findViewById(R.id.progressBar)
        val logoImage: ImageView = findViewById(R.id.logo)
*/
        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    private fun launchMainActivity() {
        val intent = Intent(this, GettingStartedActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
        mDelayHandler!!.removeCallbacks(mRunnable)

    }

    private val mRunnable: Runnable = Runnable {

        Thread(Runnable {
            while (progressBarStatus < 100) {
                // performing some dummy operation
                try {
                    dummy = dummy+25
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                // tracking progress
                progressBarStatus = dummy

                // Updating the progress bar
                splash_screen_progress_bar.progress = progressBarStatus
            }

            //splash_screen_progress_bar.setProgress(10)


            if(AppPreferences.isLogin) {
                // user is logged in
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            } else {
                launchMainActivity()

            }


        }).start()
    }

    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}

