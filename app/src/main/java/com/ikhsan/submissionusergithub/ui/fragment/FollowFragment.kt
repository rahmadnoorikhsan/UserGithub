package com.ikhsan.submissionusergithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ikhsan.submissionusergithub.R
import com.ikhsan.submissionusergithub.databinding.FragmentFollowBinding
import com.ikhsan.submissionusergithub.ui.adapter.UserAdapter
import com.ikhsan.submissionusergithub.viewmodel.FollowViewModel

class FollowFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var userAdapter: UserAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var username: String
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followViewModel.snackBarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    requireActivity().window.decorView.rootView,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        if (position == 1) {
            if (savedInstanceState == null) {
                followViewModel.setFollowing(username)
            }
            followViewModel.listFollowing.observe(viewLifecycleOwner) { following ->
                if (!following.isNullOrEmpty()) {
                    userAdapter.setList(following)
                } else {
                    binding?.igNull?.visibility = View.VISIBLE
                }
            }
        } else {
            if (savedInstanceState == null) {
                followViewModel.setFollowers(username)
            }
            followViewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                if (!followers.isNullOrEmpty()) {
                    userAdapter.setList(followers)
                } else {
                    binding?.igNull?.visibility = View.VISIBLE
                }
            }
        }

        getRecycle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRecycle() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = layoutManager
            rvFollowers.adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarFollow?.isVisible = isLoading
    }

    companion object{
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}