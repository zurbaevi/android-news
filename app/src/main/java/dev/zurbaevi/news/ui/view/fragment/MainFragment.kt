package dev.zurbaevi.news.ui.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.news.R
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.databinding.FragmentMainBinding
import dev.zurbaevi.news.ui.adapter.NewsLoadStateAdapter
import dev.zurbaevi.news.ui.adapter.NewsMainAdapter
import dev.zurbaevi.news.ui.viewmodel.MainViewModel
import dev.zurbaevi.news.util.safeNavigate
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), NewsMainAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter by lazy { NewsMainAdapter(this) }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.adapter = newsAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerView.adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { newsAdapter.retry() },
                footer = NewsLoadStateAdapter { newsAdapter.retry() }
            )

            buttonRetry.setOnClickListener {
                newsAdapter.retry()
            }

            newsAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh == LoadState.Loading
                recyclerView.isVisible = it.refresh != LoadState.Loading
                buttonRetry.isVisible = it.source.refresh is LoadState.Error
                textViewError.isVisible = it.source.refresh is LoadState.Error
            }
        }

        mainViewModel.articles.observe(viewLifecycleOwner, {
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
        val searchItem = menu.findItem(R.id.menuSearch)
        val searchView = searchItem.actionView as SearchView
        var job: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    newText?.let {
                        delay(1000)
                        if (newText.isNotEmpty()) {
                            binding.recyclerView.scrollToPosition(0)
                            mainViewModel.searchArticles(newText)
                        }
                    }
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(articles: Articles) {
        findNavController().safeNavigate(
            MainFragmentDirections.actionMainFragmentToDetailsFragment(
                articles
            )
        )
    }

}