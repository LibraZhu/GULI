package com.libra.guli.module.calendar


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.libra.core.view.BaseFragment
import com.libra.guli.R
import com.libra.guli.module.calendar.utils.DateUtils
import com.libra.guli.module.calendar.viewmodel.CalendarViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import android.util.SparseArray
import android.view.animation.Animation
import android.view.animation.AnimationUtils


/**
 * A simple [Fragment] subclass.
 *
 */
class CalendarFragment : BaseFragment<CalendarViewModel>() {
    companion object {
        fun newInstance(): CalendarFragment {
            val fragment = CalendarFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.anim_enter_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.anim_exit_bottom)
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_calendar
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel?.preClick = View.OnClickListener { viewPager.currentItem = viewPager.currentItem - 1 }
        viewModel?.nextClick = View.OnClickListener { viewPager.currentItem = viewPager.currentItem + 1 }
        viewModel?.menuClick = View.OnClickListener { }
    }

    override fun initCustomView() {
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = CalendarViewPagerAdapter(childFragmentManager)
        viewPager.currentItem = CalendarViewPagerAdapter.NUM_ITEMS_CURRENT
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                val month = (viewPager.adapter as CalendarViewPagerAdapter?)?.getMonthByPosition(position)
                if (month != null) {
                    if (month > 10) {
                        viewModel?.yearMonth?.value = (viewPager.adapter as CalendarViewPagerAdapter?)?.getYearByPosition(position).toString() + " / " + month.toString()
                    } else {
                        viewModel?.yearMonth?.value = (viewPager.adapter as CalendarViewPagerAdapter?)?.getYearByPosition(position).toString() + " / 0" + month.toString()
                    }
                }

                val height = (viewPager.adapter as CalendarViewPagerAdapter?)?.getFragment(position)?.viewPagerHeight
                if (height != null) {
                    viewPager.layoutParams.height = height
                }
            }
        })
        Handler().post {
            val month = (viewPager.adapter as CalendarViewPagerAdapter?)?.getMonthByPosition(CalendarViewPagerAdapter.NUM_ITEMS_CURRENT)
            if (month != null) {
                if (month > 10) {
                    viewModel?.yearMonth?.value = (viewPager.adapter as CalendarViewPagerAdapter?)?.getYearByPosition(CalendarViewPagerAdapter.NUM_ITEMS_CURRENT).toString() + " / " + month.toString()
                } else {
                    viewModel?.yearMonth?.value = (viewPager.adapter as CalendarViewPagerAdapter?)?.getYearByPosition(CalendarViewPagerAdapter.NUM_ITEMS_CURRENT).toString() + " / 0" + month.toString()
                }
            }

            val height = (viewPager.adapter as CalendarViewPagerAdapter?)?.getFragment(CalendarViewPagerAdapter.NUM_ITEMS_CURRENT)?.viewPagerHeight
            if (height != null) {
                viewPager.layoutParams.height = height
                viewPager.requestLayout()
            }
        }
    }

    class CalendarViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        companion object {
            val NUM_ITEMS = 200
            val NUM_ITEMS_CURRENT = NUM_ITEMS / 2
        }

        private val mThisMonthPosition = DateUtils.year * 12 + DateUtils.month - 1//---100 -position
        private val number = mThisMonthPosition - NUM_ITEMS_CURRENT
        private val fragments = SparseArray<CalendarMonthFragment>()

        override fun getItem(position: Int): Fragment {
            val year = getYearByPosition(position)
            val month = getMonthByPosition(position)
            val fragment = CalendarMonthFragment.newInstance(year, month)
            fragments.put(position, fragment)
            return fragment
        }

        fun getFragment(position: Int): CalendarMonthFragment? {
            return fragments.get(position)
        }

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        fun getYearByPosition(position: Int): Int {
            return (position + number) / 12
        }

        fun getMonthByPosition(position: Int): Int {
            return (position + number) % 12 + 1
        }
    }
}
