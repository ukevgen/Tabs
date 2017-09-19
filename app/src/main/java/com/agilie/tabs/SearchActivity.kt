package com.agilie.tabs

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.agilie.poster.adapter.TabsFragmentAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {


	private val TAB_FOR_NEW = 0
	private val TAB_POPULAR = 1
	private val TAB_A_Z = 2
	private val TAB_RATING = 3
	private val TAB_PRICE = 4

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
		setContentView(R.layout.activity_search)
		adapter = TabsFragmentAdapter(supportFragmentManager)
		initTabs()
	}

	private fun initTabs() {
		tab_layout_search.addOnTabSelectedListener(tabSelectorListener)
		//Add fragments
		adapter?.apply {
			addFragment(HistoryFragment(), "NEW")
			addFragment(HistoryFragment(), "POPULAR")
			addFragment(HistoryFragment(), "A-Z")
			addFragment(HistoryFragment(), "RATING")
			addFragment(HistoryFragment(), "PRICE")
		}
		view_pager_search.adapter = adapter
		view_pager_search.offscreenPageLimit = 5
		//Init TabLayout
		tab_layout_search.apply {
			setupWithViewPager(view_pager_search)
			getTabAt(TAB_FOR_NEW)?.customView = getSearchTabIndicator(context, "FOR SALE")
			getTabAt(TAB_POPULAR)?.customView = getSearchTabIndicator(context, "POPULAR")
			getTabAt(TAB_A_Z)?.customView = getSearchTabIndicator(context, "A-Z")
			getTabAt(TAB_RATING)?.customView = getSearchTabIndicator(context, "RATING")
			getTabAt(TAB_PRICE)?.customView = getSearchTabIndicator(context, "PRICE")
		}

		(tab_layout_search.getTabAt(TAB_FOR_NEW))?.select()
	}

	private fun getSearchTabIndicator(context: Context, title: String): View? {
		val view = LayoutInflater.from(context).inflate(R.layout.tab_header_search, null) as ConstraintLayout
		(view.findViewById<View>(R.id.search_header_text) as TextView).text = title
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
