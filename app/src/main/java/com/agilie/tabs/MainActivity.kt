package com.agilie.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		addFragment(R.id.fragment_container, TabsFragment())
	}

	fun addFragment(containerViewId: Int, fragment: Fragment) {
		val fragmentTransaction = this.supportFragmentManager.beginTransaction()
		fragmentTransaction.replace(containerViewId, fragment)
		fragmentTransaction.commit()
	}
}
