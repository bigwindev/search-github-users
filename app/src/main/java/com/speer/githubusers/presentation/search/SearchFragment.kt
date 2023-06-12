package com.speer.githubusers.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.speer.githubusers.R
import com.speer.githubusers.databinding.FragmentSearchBinding
import com.speer.githubusers.presentation.base.BaseFragment
import com.speer.githubusers.presentation.users.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    R.layout.fragment_search
) {

    override val viewModel: SearchViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels({
        this.childFragmentManager.findFragmentById(R.id.fragment_users)!!
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(this.viewModel) {
            query.observe(this@SearchFragment.viewLifecycleOwner) { query ->
                if (query.isEmpty()) {
                    hideClearAction()
                } else {
                    this@SearchFragment.viewDataBinding.searchView.hide()
                    showClearAction()
                }
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel.setUsersViewModel(this@SearchFragment.usersViewModel)

        this.viewDataBinding.searchBar.setNavigationOnClickListener {
            if (this.viewModel.query.value?.isNotEmpty() == true) {
                this.viewModel.setQuery("")
            } else {
                this.viewDataBinding.searchView.show()
            }
        }

        this.viewDataBinding.searchBar.inflateMenu(R.menu.menu_search_bar)

        this.viewDataBinding.searchView
            .editText
            .setOnEditorActionListener { v, _, _ ->
                this.viewDataBinding.searchView.hide()

                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    this.viewModel.setQuery(query)
                }

                true
            }
    }

    private fun showClearAction() {
        this.viewDataBinding.searchBar.menu.clear()
        val clearActionItem = this.viewDataBinding.searchBar.menu.add(R.string.clear_action_title)
            .setIcon(com.google.android.material.R.drawable.ic_clear_black_24)
        clearActionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        this.viewDataBinding.searchBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem == clearActionItem) {
                this.viewDataBinding.searchView.show()
                this.viewDataBinding.searchView.setText("")

                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun hideClearAction() {
        this.viewDataBinding.searchBar.menu.clear()
    }
}
