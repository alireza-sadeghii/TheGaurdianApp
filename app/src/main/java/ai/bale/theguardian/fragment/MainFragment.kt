package ai.bale.theguardian.fragment

import ai.bale.theguardian.R
import ai.bale.theguardian.adapter.ItemAdapter
import ai.bale.theguardian.data.NewsViewModel
import ai.bale.theguardian.databinding.FragmentLayoutBinding
import ai.bale.theguardian.db.AppDatabase
import ai.bale.theguardian.network.GuardianApi
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment(private val category: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLayoutBinding.bind(view)
        val itemAdapter = ItemAdapter()

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = itemAdapter

        val apiService = GuardianApi.retrofitService
        val database = AppDatabase.getInstance(requireContext())
        val viewModel = NewsViewModel(database, apiService, category)


        lifecycleScope.launch {
            viewModel.pagingFlow().collectLatest { new ->
                itemAdapter.submitData(new)
            }
        }
    }
}