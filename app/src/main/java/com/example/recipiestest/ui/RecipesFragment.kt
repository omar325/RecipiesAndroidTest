package com.example.recipiestest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.recipiestest.R
import com.example.recipiestest.databinding.FragmentRecipesBinding
import com.example.recipiestest.infrastructure.models.Meal
import kotlinx.android.synthetic.main.fragment_recipes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesFragment: Fragment() {
    companion object {
        private val TAG = "RecipesFragment"
    }

    private val viewModel: RecipesViewModel by viewModel()
    private val adapter by lazy {
        RecipesAdapter { selectedRecipe ->
            goToRecipeDetail(selectedRecipe)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false) as FragmentRecipesBinding
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSearchView()
        recycler_view.adapter = adapter

        observeViewModelData()
    }

    private fun configureSearchView() {
        val searchView = top_app_bar.menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e(TAG, "Submit: ${query?:"empty"})")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e(TAG, "Change: ${newText?:"empty"})")
                newText?.let {
                    if(it.isNotBlank()) {
                        viewModel.queryRecipes(it)
                    }
                }
                return false
            }

        })
    }

    private fun observeViewModelData() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RecipesVmState.Initial -> {
                    progress_bar.visibility = GONE
                    no_results_layout.visibility = VISIBLE
                }
                is RecipesVmState.Searching -> {
                    progress_bar.visibility = VISIBLE
                }
                is RecipesVmState.SearchSuccess -> {
                    adapter.submitList(it.recipes)
                    no_results_layout.visibility = GONE
                    progress_bar.visibility = GONE
                }
            }
        })
    }

    private fun goToRecipeDetail(selectedRecipe: Meal) {
        val action = RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailFragment(selectedRecipe.idMeal)
        findNavController().navigate(action)
    }
}