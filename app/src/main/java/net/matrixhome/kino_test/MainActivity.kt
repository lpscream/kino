package net.matrixhome.kino_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.matrixhome.kino_test.databinding.ActivityMainBinding
import net.matrixhome.kino_test.ui.KinoAdapter
import net.matrixhome.kino_test.ui.KinoRepositoriesViewModel
import net.matrixhome.kino_test.ui.UiAction
import net.matrixhome.kino_test.ui.UiState

class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity_log"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(KinoRepositoriesViewModel::class.java)
        binding.bindState(
            uiState = viewModel.state,
            uiAction = viewModel.accept
        )
    }


    private fun ActivityMainBinding.bindState(
        uiState: StateFlow<UiState>,
        uiAction: (UiAction) -> Unit
    ){
        val kinoAdapter = KinoAdapter()
        movieList.adapter = kinoAdapter
        movieList.layoutManager = GridLayoutManager(applicationContext, 3)


        bindSearch(
            uiState = uiState,
            onQueryChanged = uiAction
        )

        bindList(
            kinoAdapter = kinoAdapter,
            uiState = uiState,
            onScrollChanged = uiAction
        )



    }

    private fun ActivityMainBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ){
        searchMovie.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                updateMovieListFromInput(onQueryChanged)
                true
            }
            else
                false
        }

        searchMovie.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                updateMovieListFromInput(onQueryChanged)
                return@setOnKeyListener true
            }else
                return@setOnKeyListener false
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(searchMovie::setText)
        }
    }



    private fun ActivityMainBinding.updateMovieListFromInput(onQueryChanger: (UiAction.Search) -> Unit){
        searchMovie.text.trim().let {
            if (it.isNotEmpty()){
                Log.d(TAG, "updateMovieListFromInput: ${searchMovie.text}")
                movieList.scrollToPosition(0)
                onQueryChanger(UiAction.Search(query = it.toString()))
            }
        }
    }


    private fun ActivityMainBinding.bindList(
        kinoAdapter: KinoAdapter,
        uiState: StateFlow<UiState>,
        onScrollChanged: (UiAction.Scroll) -> Unit){
        movieList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = kinoAdapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .map { it.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch
        ,Boolean::and
        )
            .distinctUntilChanged()

        val pagingData = uiState
            .map { it.pagingData }
            .distinctUntilChanged()

        lifecycleScope.launch {
            combine(shouldScrollToTop, pagingData, ::Pair)
                .distinctUntilChangedBy { it.second }
                .collectLatest { (shouldScrollToTop, pagingData) ->
                    kinoAdapter.submitData(pagingData)

                    if (shouldScrollToTop) movieList.scrollToPosition(0)
                }
        }
    }
}