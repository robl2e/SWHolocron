package com.lab.swholocron.ui.main

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lab.swholocron.databinding.ViewMainBinding
import com.lab.swholocron.ui.arch.ArchState
import com.lab.swholocron.ui.arch.BaseProxyView
import com.lab.swholocron.ui.common.ItemClickSupport
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener

class MainProxyView(
    private val binding: ViewMainBinding,
    items: MutableList<SearchResultUIModel>
) :
    MainContract.View, BaseProxyView<MainContract.Listener>(),
    MaterialSearchView.OnQueryTextListener, SearchViewListener,
    ItemClickSupport.OnItemClickListener {

    private val searchView = binding.searchView
    private val viewSwitcher = binding.viewSwitcher
    private val loadingView = binding.loadingView
    private val searchResultListView: RecyclerView = binding.listSearchResult
    private var adapter: SearchResultListAdapter = SearchResultListAdapter()

    init {
        searchView.setOnQueryTextListener(this)
        searchView.setOnSearchViewListener(this)

        adapter.submitList(items)

        ItemClickSupport.addTo(searchResultListView).setOnItemClickListener(this)
        val layoutManager = LinearLayoutManager(binding.root.context)
        searchResultListView.layoutManager = layoutManager
        searchResultListView.adapter = adapter
    }

    override fun render(state: ArchState) {
        when (state) {
            is MainContract.ViewState.DataState -> {
                renderSearchResultsView(state.items)
            }
            is MainContract.ViewState.EmptyState -> {
                renderEmptyView()
            }
            is MainContract.ViewState.LoadingState -> {
                renderLoadingView(state.show)
            }
            is MainContract.ViewState.ErrorState -> {
                renderErrorState(state.error)
            }
        }
    }

    private fun renderErrorState(error: Exception) {
        Snackbar.make(binding.root, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun renderEmptyView() {
        viewSwitcher.displayedChild = 0
    }

    private fun renderLoadingView(show: Boolean) {
        if (show) {
            loadingView.show()
        } else {
            loadingView.hide()
        }
    }

    private fun renderSearchResultsView(items: List<SearchResultUIModel>) {
        viewSwitcher.displayedChild = 1
        adapter.submitList(items)
    }

    private fun notifyListeners(event: MainContract.ViewEvent) {
        for (listener in getListeners()) {
            listener.onEvent(event)
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        notifyListeners(MainContract.ViewEvent.SearchEvent(query))
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onSearchViewClosed() {
    }

    override fun onSearchViewShown() {
    }

    override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
        notifyListeners(MainContract.ViewEvent.ItemClickEvent(adapter.getItemFrom(position)))
    }
}