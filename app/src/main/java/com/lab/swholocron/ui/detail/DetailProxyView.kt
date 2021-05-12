package com.lab.swholocron.ui.detail

import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.google.android.material.snackbar.Snackbar
import com.lab.swholocron.databinding.ViewDetailBinding
import com.lab.swholocron.ui.arch.ArchState
import com.lab.swholocron.ui.arch.BaseProxyView

class DetailProxyView(
    private val binding: ViewDetailBinding
) : DetailContract.View, BaseProxyView<DetailContract.Listener>() {

    private val imagePersonView = binding.imagePerson
    private val textNameView = binding.textName
    private val textBirthYearView = binding.textPropertyValueBirthYear
    private val textGenderView = binding.textPropertyValueGender
    private val textHeightView = binding.textPropertyValueHeight
    private val textMassView = binding.textPropertyValueMass


    override fun render(viewState: ArchState) {
        when (viewState) {
            is DetailContract.ViewState.InitialState -> {
                renderImageView(viewState.name, viewState.abbrevName)
                renderInitialView(viewState.name)
            }
            is DetailContract.ViewState.DataState -> {
                renderImageView(viewState.uiModel.name, viewState.uiModel.abbrevName)
                renderDataView(viewState.uiModel)
            }
            is DetailContract.ViewState.ErrorState -> {
                renderErrorState(viewState.error)
            }
        }
    }

    // TODO: Centralize logic to one place
    private fun renderImageView(name: String, abbrevName: String) {
        val generator = ColorGenerator.MATERIAL
        val color = generator.getColor(name)

        val builder = TextDrawable.builder()
            .beginConfig()
            .withBorder(4)
            .endConfig()
            .round()

        val drawable = builder.build(abbrevName, color)

        imagePersonView.setImageDrawable(drawable)
    }

    private fun renderDataView(uiModel: DetailUIModel) {
        textNameView.text = uiModel.name
        textBirthYearView.text = uiModel.birthYear
        textGenderView.text = uiModel.gender
        textHeightView.text = uiModel.height
        textMassView.text = uiModel.mass
    }

    private fun renderInitialView(name: String) {
        textNameView.text = name
    }

    private fun renderErrorState(error: Exception) {
        Snackbar.make(binding.root, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

}
