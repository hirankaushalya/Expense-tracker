import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expensetracker_shops.Adapters.ShopAdapter
import com.example.expensetracker_shops.Models.ShopModel
import com.example.expensetracker_shops.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShopAdapterTest {

    private lateinit var shopList: ArrayList<ShopModel>
    private lateinit var adapter: ShopAdapter

    @Before
    fun setup() {
        shopList = arrayListOf(
            ShopModel("Shop 1"),
            ShopModel("Shop 2"),
            ShopModel("Shop 3")
        )
        adapter = ShopAdapter(shopList)
    }

    @Test
    fun getItemCount_shouldReturnCorrectCount() {
        val itemCount = adapter.itemCount
        assertEquals(shopList.size, itemCount)
    }

    @Test
    fun onCreateViewHolder_shouldInflateCorrectLayout() {
        val parent = createParentView()
        val viewType = 0 // or any appropriate view type
        val viewHolder = adapter.onCreateViewHolder(parent, viewType)
        val inflatedView = viewHolder.itemView

        val expectedLayoutId = R.layout.shop_list_item
        val actualLayoutId = inflatedView.id
        assertEquals(expectedLayoutId, actualLayoutId)
    }

    @Test
    fun onBindViewHolder_shouldSetCorrectShopName() {
        val position = 1 // or any valid position in the list
        val parent = createParentView()
        val viewType = 0 // or any appropriate view type
        val viewHolder = adapter.onCreateViewHolder(parent, viewType)

        adapter.onBindViewHolder(viewHolder, position)

        val expectedShopName = shopList[position].shopName
        val actualShopName = viewHolder.tvShopName.text.toString()
        assertEquals(expectedShopName, actualShopName)
    }

    // Helper method to create a mock parent view
    private fun createParentView(): ViewGroup {
        val context: Context = ApplicationProvider.getApplicationContext()
        return RecyclerView(context)
    }

}
