import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment(val category: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v("checking", "fragment")
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLayoutBinding.bind(view) // Use the view parameter for binding

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val call = GuardianApi.retrofitService.callData(category)

        var news: List<News> = emptyList()

        call.enqueue(object : Callback<FetchedData> {
            override fun onResponse(call: Call<FetchedData>, response: Response<FetchedData>) {
                if (response.isSuccessful) {
                    news = response.body()?.data?.news ?: emptyList() // Update the news list
                    Log.v("checking", response?.body()?.data?.resultNum.toString())
                    val itemAdapter = ItemAdapter(context, news)
                    recyclerView.adapter = itemAdapter // Set the adapter with updated data
                }
            }

            override fun onFailure(call: Call<FetchedData>, t: Throwable) {
                Toast.makeText(context, "No Internet Response", Toast.LENGTH_LONG).show()
            }
        })
    }
}