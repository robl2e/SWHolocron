package com.lab.swholocron.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lab.swholocron.R
import com.lab.swholocron.data.network.APIClient
import com.lab.swholocron.data.people.PeopleRepositoryImpl
import com.lab.swholocron.databinding.ActivityDetailBinding
import com.lab.swholocron.ui.base.BaseActivity
import kotlinx.coroutines.flow.collect

class DetailActivity : BaseActivity() {

    companion object {
        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_URI = "extra_uri"

        //TODO: Use navigation component
        fun start(activity: AppCompatActivity, name: String, uri: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_URI, uri)

            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var view: DetailContract.View
    private val viewModel: DetailContract.Container by viewModels<DetailViewModel> {
        //TODO: Dependency injection
        DetailViewModel.Factory(PeopleRepositoryImpl(APIClient.peopleService))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        view = DetailProxyView(binding.containerDetail)
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getStateFlow().collect {
                view.render(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            // Handle initial state
            val name = intent.getStringExtra(EXTRA_NAME)!!
            val uri = intent.getStringExtra(EXTRA_URI)!!
            viewModel.process(DetailContract.ViewEvent.Initialize(name, uri))
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}