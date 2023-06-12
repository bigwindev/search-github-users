package com.speer.githubusers.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.speer.githubusers.R
import com.speer.githubusers.databinding.FragmentUsersBinding
import com.speer.githubusers.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<UsersViewModel, FragmentUsersBinding>(
    R.layout.fragment_users
) {

    override val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { arguments ->
            val username = UsersFragmentArgs.fromBundle(arguments).username
            when (UsersFragmentArgs.fromBundle(arguments).apiType as UsersViewModel.ApiType) {
                UsersViewModel.ApiType.FOLLOWERS -> this.viewModel.getFollowers(username)
                UsersViewModel.ApiType.FOLLOWING -> this.viewModel.getFollowing(username)
            }
        }

        with(this.viewModel) {
            navigateToProfile.observe(this@UsersFragment.viewLifecycleOwner) { user ->
                user?.let {
                    findNavController().navigate(
                        UsersFragmentDirections.actionToProfileFragment(user)
                    )
                    setNavigatedToProfile()
                }
            }

            navigateToFollowers.observe(this@UsersFragment.viewLifecycleOwner) { user ->
                user?.let {
                    findNavController().navigate(
                        UsersFragmentDirections.actionToUsersFragment(
                            "Followers",
                            user.login,
                            UsersViewModel.ApiType.FOLLOWERS
                        )
                    )
                    setNavigatedToFollowers()
                }
            }

            navigateToFollowing.observe(this@UsersFragment.viewLifecycleOwner) { user ->
                user?.let {
                    findNavController().navigate(
                        UsersFragmentDirections.actionToUsersFragment(
                            "Following",
                            user.login,
                            UsersViewModel.ApiType.FOLLOWING
                        )
                    )
                    setNavigatedToFollowing()
                }
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewDataBinding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0 &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == this@UsersFragment.viewModel.countOfUsers - 1) {
                    this@UsersFragment.viewModel.loadMore()
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }
}
