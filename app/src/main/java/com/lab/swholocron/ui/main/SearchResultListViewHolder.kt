package com.lab.swholocron.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.lab.swholocron.databinding.ItemSearchResultBinding


class SearchResultListViewHolder(binding: ItemSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.textName
    private val resultImageView = binding.imageResult

    fun bindItem(uiModel: SearchResultUIModel) {
        renderName(uiModel)
        renderImage(uiModel)
    }

    // TODO: Consolidate same logic used in main screen
    private fun renderImage(uiModel: SearchResultUIModel) {
        val generator = ColorGenerator.MATERIAL // or use DEFAULT
        val color = generator.getColor(uiModel.name)

        val builder = TextDrawable.builder()
            .beginConfig()
            .withBorder(4)
            .endConfig()
            .round()

        val drawable = builder.build(uiModel.abbrevName, color)

        resultImageView.setImageDrawable(drawable)
    }

    private fun renderName(uiModel: SearchResultUIModel) {
        nameTextView.text = uiModel.name
    }

}