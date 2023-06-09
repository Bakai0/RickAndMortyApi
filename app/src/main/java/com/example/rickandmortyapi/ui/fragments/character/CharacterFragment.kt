package com.example.rickandmortyapi.ui.fragments.character

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.base.BaseFragment
import com.example.rickandmortyapi.databinding.FragmentCharacterBinding
import com.example.rickandmortyapi.models.CharacterModel
import com.example.rickandmortyapi.ui.activity.MainActivity
import com.example.rickandmortyapi.ui.adapters.CharacterAdapter
import kotlinx.coroutines.launch

class CharacterFragment :
    BaseFragment<FragmentCharacterBinding, SharedViewModel>(R.layout.fragment_character) {

    override val binding by viewBinding(FragmentCharacterBinding::bind)
    override val viewModel: SharedViewModel by activityViewModels()
    private var characterAdapter = CharacterAdapter(this::onItemClick)


    override fun initialize() {
        binding.characterRecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
    }

    override fun setupObserves() {
        lifecycleScope.launch {
            viewModel.fetchCharacters().collect {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun onItemClick(id: Int) {
        val model = CharacterModel(
            id = id,
            name = "",
            gender = "",
            status = "",
            image = "",
        )
        findNavController().navigate(
            CharacterFragmentDirections.actionCharacterFragmentToSingleFragment(
                model
            )
        )
    }

    override fun bottomNavigationSelected() {
        (requireActivity() as MainActivity).setOnItemReselectedListener() {
            binding.characterRecView.smoothScrollToPosition(0)
        }
    }
}




