package com.ikhsan.submissionusergithub.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ikhsan.submissionusergithub.R
import com.ikhsan.submissionusergithub.databinding.ActivityMainBinding
import com.ikhsan.submissionusergithub.response.UserResponseItem
import com.ikhsan.submissionusergithub.ui.adapter.UserAdapter
import com.ikhsan.submissionusergithub.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()


        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        supportActionBar?.title = "weGitHub"

        if (savedInstanceState == null) {
            userViewModel.findUser("google")
        }

        userViewModel.listUser.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                userAdapter.setList(list)
                showLoading(false)
            }
        }

        userViewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    window.decorView.rootView,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        getRecycle()
        onClickCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.itemSearch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun searchQuery(query: String) {
        showLoading(true)
        if (query.isEmpty()) return
        userViewModel.findUser(query)
    }

    private fun getRecycle() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.apply {
            rvUser.layoutManager = layoutManager
            rvUser.setHasFixedSize(true)
            rvUser.adapter = userAdapter
        }
    }

    private fun onClickCallback() {
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponseItem) {
                val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                moveIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                startActivity(moveIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}