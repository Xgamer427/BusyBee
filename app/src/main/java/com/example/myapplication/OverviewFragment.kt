import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.RecyclerAdapter

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview_notifications, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)

        // Your data (titles, details, images)
        val titles = listOf("Title 1", "Title 2", "Title 3")
        val details = listOf("Detail 1", "Detail 2", "Detail 3")
        val images = listOf(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background)

        // Create an instance of your RecyclerAdapter
        val adapter = RecyclerAdapter(titles, details, images)

        // Set the adapter on the RecyclerView
        recyclerView.adapter = adapter

        // Set a LinearLayoutManager on the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}
