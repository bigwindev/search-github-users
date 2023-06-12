package com.speer.githubusers.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.speer.githubusers.R
import com.speer.githubusers.databinding.ItemSearchHistoryBinding
import com.speer.githubusers.databinding.ItemSearchHistoryHeadBinding

class SearchHistoriesAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var histories: Array<String> = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEAD.value -> HeadViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_search_history_head,
                    parent,
                    false
                )
            )
            else -> ItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_search_history,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(this.histories[position - 1], this.onClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return (if (position == 0) ViewType.HEAD else ViewType.ITEM).value
    }

    override fun getItemCount(): Int {
        return this.histories.size + 1
    }

    fun setHistories(histories: Array<String>) {
        this.histories = histories
        notifyDataSetChanged()
    }

    private enum class ViewType(val value: Int) {
        HEAD(0), ITEM(1),
    }

    private class HeadViewHolder(binding: ItemSearchHistoryHeadBinding) : RecyclerView.ViewHolder(binding.root)

    private class ItemViewHolder(
        private val binding: ItemSearchHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            query: String,
            onClickListener: OnClickListener
        ) {
            this.binding.query = query
            this.binding.onClickListener = onClickListener
            this.binding.imageView.setColorFilter(this.binding.textView.currentTextColor)
            this.binding.executePendingBindings()
        }
    }
}

class OnClickListener(val clickListener: (query: String) -> Unit) {
    fun onClick(query: String) = clickListener(query)
}
