package ai.bale.theguardian.fragment

import ai.bale.theguardian.R
import ai.bale.theguardian.adapter.ItemAdapter
import ai.bale.theguardian.databinding.FragmentLayoutBinding
import ai.bale.theguardian.network.GuardianApi
import ai.bale.theguardian.repository.DataRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

class MainFragment(val category: String) : Fragment() {

    private lateinit var newsRepository: DataRepository
    private lateinit var itemAdapter: ItemAdapter

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

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val apiService = GuardianApi.retrofitService
        val newsRepository = DataRepository(apiService)

        val itemAdapter = ItemAdapter(context, emptyList())

        recyclerView.adapter = itemAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            newsRepository.getNews(category).collect { news ->
                itemAdapter.updateData(news)
            }
        }
    }
}
