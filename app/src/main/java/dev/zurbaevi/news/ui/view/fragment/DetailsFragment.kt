package dev.zurbaevi.news.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.news.databinding.FragmentDetailsBinding
import dev.zurbaevi.news.ui.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class DetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewTitle.text = args.articles.title
            textViewDescription.text = args.articles.description

            buttonFavorite.setOnClickListener {
                favoriteViewModel.insert(args.articles)
                dialog?.dismiss()
                Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}