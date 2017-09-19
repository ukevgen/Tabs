package com.agilie.tabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.agilie.poster.adapter.TabsFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private val TAB_FOR_SALE = 0
	private val TAB_HISTORY = 1

	enum class TabSelectedStatus {
		SELECTED,
		RESELECTED,
		UNSELECTED
	}

	private var visibleTabIndicator = false
	private var adapter: TabsFragmentAdapter? = null

	private var tabSelectorListener = object : TabLayout.OnTabSelectedListener {
		override fun onTabReselected(tab: TabLayout.Tab) {
			onTabClick(tab, TabSelectedStatus.RESELECTED)
		}

		override fun onTabUnselected(tab: TabLayout.Tab) {
			onTabClick(tab, TabSelectedStatus.UNSELECTED)
		}

		override fun onTabSelected(tab: TabLayout.Tab) {
			onTabClick(tab, TabSelectedStatus.SELECTED)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		adapter = TabsFragmentAdapter(supportFragmentManager)
		initTabs()
		bt_search.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
	}

	private fun initTabs() {
		tab_layout.addOnTabSelectedListener(tabSelectorListener)
		//Add fragments
		adapter?.apply {
			addFragment(ForSaleFragment(), "FOR SALE")
			addFragment(HistoryFragment(), "HISTORY")
		}
		view_pager.adapter = adapter
		view_pager.offscreenPageLimit = 2
		//Init TabLayout
		tab_layout.apply {
			setupWithViewPager(view_pager)
			getTabAt(TAB_FOR_SALE)?.customView = getForSaleTabIndicator(context, "FOR SALE")
			getTabAt(TAB_HISTORY)?.customView = getHistoryTabIndicator(context, "HISTORY")
		}

		val layout = (tab_layout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
		val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
		layoutParams.weight = 0.5f // e.g. 0.5f
		layout.layoutParams = layoutParams

		(tab_layout.getTabAt(TAB_FOR_SALE))?.select()
	}

	private fun getForSaleTabIndicator(context: Context, title: String): View {
		val view = LayoutInflater.from(context).inflate(R.layout.tab_header_sales_for_sale, null) as ConstraintLayout
		(view.findViewById<View>(R.id.sales_header_text) as TextView).text = title
		return view
	}

	private fun getHistoryTabIndicator(context: Context, title: String): View {
		val view = LayoutInflater.from(context).inflate(R.layout.tab_header_sales_history, null) as ConstraintLayout
		(view.findViewById<View>(R.id.history_header_text) as TextView).text = title
		return view
	}

	private fun onTabClick(tab: TabLayout.Tab, selected: TabSelectedStatus) {
		val layout = tab.customView ?: return

		when (selected) {
			TabSelectedStatus.SELECTED -> {
				showTabIndicator(layout)
			}
			TabSelectedStatus.RESELECTED -> {
				when (visibleTabIndicator) {
				/* true -> {
					 hideTabIndicator(layout)
				 }*/
					false -> {
						showTabIndicator(layout)
					}
				}
			}
			TabSelectedStatus.UNSELECTED -> {
				hideTabIndicator(layout)
			}
		}
	}

	private fun showTabIndicator(layout: View?) {
		val set = ConstraintSet()
		layout?.let {
			it as ConstraintLayout
			set.clone(it)
			set.setVisibility(R.id.line_view, View.VISIBLE)
			set.applyTo(it)
			//       TransitionManager.beginDelayedTransition(it)
			set.constrainWidth(R.id.line_view, 0)
			set.applyTo(it)
		}
		visibleTabIndicator = true
	}

	private fun hideTabIndicator(layout: View?) {

		val set = ConstraintSet()
		layout?.let {
			it as ConstraintLayout
			set.clone(it)
			set.setVisibility(R.id.line_view, View.INVISIBLE)
			set.constrainWidth(R.id.line_view, 1)
			set.applyTo(it)
		}
		visibleTabIndicator = false
	}
}
