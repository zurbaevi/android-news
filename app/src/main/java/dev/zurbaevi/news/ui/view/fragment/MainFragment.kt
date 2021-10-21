package dev.zurbaevi.news.ui.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.news.R
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.databinding.FragmentMainBinding
import dev.zurbaevi.news.ui.adapter.NewsAdapter
import dev.zurbaevi.news.ui.viewmodel.MainViewModel
import dev.zurbaevi.news.util.Resource
import dev.zurbaevi.news.util.safeNavigate

@AndroidEntryPoint
class MainFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter by lazy { NewsAdapter(this) }
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
        }

        mainViewModel.articles.observe(viewLifecycleOwner, {
            binding.apply {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            progressBar.isVisible = false
                            recyclerView.isVisible = true
                            newsAdapter.submitList(it.data?.articles)
                        }
                        Resource.Status.ERROR -> {
                            progressBar.isVisible = false
                            recyclerView.isVisible = false
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Resource.Status.LOADING -> {
                            progressBar.isVisible = true
                            recyclerView.isVisible = false
                        }
                    }
                }
            }
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
        val searchItem = menu.findItem(R.id.menuSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    mainViewModel.searchArticles(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
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