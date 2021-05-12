package com.lab.swholocron.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lab.swholocron.databinding.ItemSearchResultBinding

class SearchResultListAdapter :
    ListAdapter<SearchResultUIModel, SearchResultListViewHolder>(
        DiffItemCallback()
    ) {


    class DiffItemCallback : DiffUtil.ItemCallback<SearchResultUIModel>() {
        override fun areItemsTheSame(
            oldItem: SearchResultUIModel,
            newItem: SearchResultUIModel
        ): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(
            oldItem: SearchResultUIModel,
            newItem: SearchResultUIModel
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultListViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchResultListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    fun getItemFrom(position: Int): SearchResultUIModel {
        return getItem(position)
    }
}


