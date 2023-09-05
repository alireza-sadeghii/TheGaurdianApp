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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment(private val category: String) : Fragment() {
    private lateinit var binding: FragmentLayoutBinding
    private lateinit var layoutManager: LinearLayoutManager
        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLayoutBinding.bind(view)
        val itemAdapter = ItemAdapter()

        val recyclerView = binding.recyclerview
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = itemAdapter

        val apiService = GuardianApi.retrofitService
        val database = AppDatabase.getInstance(requireContext())
        val viewModel = NewsViewModel(database, apiService, category)


        if (savedInstanceState != null) {
            val scrollPosition = savedInstanceState.getInt("scrollPosition", 0)
            layoutManager.scrollToPosition(scrollPosition)
        }


        lifecycleScope.launch {
            viewModel.pagingFlow().collectLatest { new ->
                itemAdapter.submitData(new)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scrollPosition", layoutManager.findFirstVisibleItemPosition())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val scrollPosition = savedInstanceState.getInt("scrollPosition", 0)
            layoutManager.scrollToPosition(scrollPosition)
        }
    }
}