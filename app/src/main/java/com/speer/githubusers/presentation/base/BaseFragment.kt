package com.speer.githubusers.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.speer.githubusers.BR

abstract class BaseFragment<V : ViewModel, T : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected abstract val viewModel: V
    protected lateinit var viewDataBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.viewDataBinding = DataBindingUtil.inflate(inflater, this.layoutId, container, false)
        this.viewDataBinding.setVariable(BR.viewModel, this.viewModel)
        this.viewDataBinding.lifecycleOwner = this
        this.viewDataBinding.executePendingBindings()

        return this.viewDataBinding.root
    }
}
