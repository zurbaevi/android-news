package dev.zurbaevi.news.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.news.databinding.FragmentDetailsBinding
import dev.zurbaevi.news.ui.viewmodel.DetailsViewModel

@AndroidEntryPoint
class DetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()
    private val detailsViewModel by viewModels<DetailsViewModel>()

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
                detailsViewModel.insert(args.articles)
                DynamicToast.makeSuccess(requireContext(), "Added").show()
                dialog?.dismiss()
            }
        }
    }
}