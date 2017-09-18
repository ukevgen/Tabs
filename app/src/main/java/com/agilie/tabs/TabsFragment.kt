package com.agilie.tabs

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.agilie.poster.adapter.TabsFragmentAdapter




class TabsFragment : Fragment() {

	lateinit var adapter: TabsFragmentAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val rootView = inflater.inflate(R.layout.fragment_tab, container, false)

		adapter = TabsFragmentAdapter(childFragmentManager)
		val viewPager = rootView.findViewById<View>(R.id.view_pager_main) as ViewPager
		val tabLayout = activity.findViewById<View>(R.id.tab_layout) as TabLayout

		//Add fragments
		adapter.apply {
			addFragment(ForSaleFragment(), "FOR SALE")
			addFragment(HistoryFragment(), "HISTORY")
		}
		//Set adapter
		viewPager.adapter = adapter
		viewPager.offscreenPageLimit = adapter.count
		//Init TabLayout
		tabLayout.apply {
			setupWithViewPager(viewPager)
			/*getTabAt(0)?.customView = getTabIndicator(context, R.drawable.ic_fill)
			getTabAt(1)?.customView = getTabIndicator(context, R.drawable.ic_filter)*/
			getTabAt(0)?.customView = getForSaleTabIndicator(context, "FOR SALE")
			getTabAt(1)?.customView = getHistoryTabIndicator(context, "HISTORY")
		}

		val layout = (tabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
		val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
		layoutParams.weight = 0.5f // e.g. 0.5f
		layout.layoutParams = layoutParams

		return rootView
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

	private fun getTabIndicator(context: Context): View {
		val view = LayoutInflater.from(context).inflate(R.layout.tab_header_search, null) as ConstraintLayout
		//view.findViewById(R.id.header_image).background = ContextCompat.getDrawable(context, icon)
		return view
	}
}