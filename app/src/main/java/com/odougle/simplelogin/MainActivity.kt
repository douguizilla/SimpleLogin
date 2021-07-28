package com.odougle.simplelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odougle.simplelogin.ui.profile.ProfileFragment
import com.odougle.simplelogin.ui.start.StartFragment

class MainActivity : AppCompatActivity(), StartFragment.OnButtonClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.myContainer, StartFragment.newInstance())
            .commit()
    }

    override fun buttonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.myContainer, ProfileFragment.newInstance())
            .commit()
    }
}