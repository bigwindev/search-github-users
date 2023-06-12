package com.speer.githubusers.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.speer.githubusers.R
import com.speer.githubusers.databinding.FragmentProfileBinding
import com.speer.githubusers.presentation.base.BaseFragment
import com.speer.githubusers.presentation.users.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>(
    R.layout.fragment_profile
) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { arguments ->
            val user = ProfileFragmentArgs.fromBundle(arguments).user
            viewModel.setUser(user)
        }

        with(this.viewModel) {
            navigateToFollowers.observe(this@ProfileFragment.viewLifecycleOwner) { user ->
                user?.let {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionToUsersFragment(
                            "Followers",
                            user.login,
                            UsersViewModel.ApiType.FOLLOWERS
                        )
                    )
                    setNavigatedToFollowers()
                }
            }

            navigateToFollowing.observe(this@ProfileFragment.viewLifecycleOwner) { user ->
                user?.let {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionToUsersFragment(
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
}
