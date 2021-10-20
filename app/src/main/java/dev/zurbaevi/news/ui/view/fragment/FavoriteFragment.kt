package dev.zurbaevi.news.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.news.databinding.FragmentFavoriteBinding
import dev.zurbaevi.news.ui.adapter.NewsAdapter
import dev.zurbaevi.news.ui.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    private val newsAdapter by lazy { NewsAdapter(null) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            favoriteViewModel.articles.observe(viewLifecycleOwner, {
                recyclerView.adapter = newsAdapter
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            })
        }

        favoriteViewModel.articles.observe(viewLifecycleOwner, {
            newsAdapter.submitList(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}