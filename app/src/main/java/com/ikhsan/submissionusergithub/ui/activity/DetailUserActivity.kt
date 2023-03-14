package com.ikhsan.submissionusergithub.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ikhsan.submissionusergithub.R
import com.ikhsan.submissionusergithub.databinding.ActivityDetailUserBinding
import com.ikhsan.submissionusergithub.ui.adapter.SectionPagerAdapter
import com.ikhsan.submissionusergithub.util.ColorType.setColor
import com.ikhsan.submissionusergithub.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            if (savedInstanceState == null) {
                viewModel.setDetailUsers(username.toString())
            }
        }

        setToolbar()
        setDetail()
        setUpPager()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun setToolbar() {
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.pbDetail.visibility = View.VISIBLE
        } else {
            binding.pbDetail.visibility = View.GONE
        }
    }

    private fun setDetail() {
            viewModel.listDetailUser.observe(this) { item ->
                if (item != null) {
                    binding.tvDetailName.text = item.name
                    binding.tvTypeDetail.setColor(this@DetailUserActivity.baseContext, item.type)
                    binding.tvTotalRepos.text = item.publicRepos.toString()
                    binding.tvTotalFollowing.text = item.following.toString()
                    binding.tvTotalFollowers.text = item.followers.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(item.avatarUrl)
                        .into(binding.ivDetailUser)
                }
            }
    }


    private fun setUpPager() {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        sectionsPagerAdapter.username = username ?: ""

        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object{
        const val EXTRA_USERNAME = "username"
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
}