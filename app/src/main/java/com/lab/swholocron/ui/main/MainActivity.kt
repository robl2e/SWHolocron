package com.lab.swholocron.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lab.swholocron.R
import com.lab.swholocron.data.network.APIClient
import com.lab.swholocron.data.people.PeopleRepositoryImpl
import com.lab.swholocron.databinding.ActivityMainBinding
import com.lab.swholocron.ui.base.BaseActivity
import com.lab.swholocron.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collect


class MainActivity : BaseActivity(), MainContract.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var view: MainContract.View
    private val viewModel: MainContract.Container by viewModels<MainViewModel> {
        //TODO: Dependency injection
        MainViewModel.Factory(PeopleRepositoryImpl(APIClient.peopleService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        view = MainProxyView(
            binding.containerMain,
            mutableListOf()
        )
        setSupportActionBar(binding.containerMain.toolbar)
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getStateFlow().collect {
                view.render(it)
            }
        }
    }

    override fun onEvent(event: MainContract.ViewEvent) {
        when (event) {
            //TODO: Use side effects
            is MainContract.ViewEvent.ItemClickEvent -> {
                DetailActivity.start(this, event.uiModel.name, event.uiModel.uri)
            }
            else -> {
                viewModel.process(event)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        view.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.unregisterListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        binding.containerMain.searchView.setMenuItem(searchActionMenuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}