package com.example.expensetracker_shops.Activities

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker_shops.Adapters.ShopAdapter
import com.example.expensetracker_shops.Models.ShopModel
import com.example.expensetracker_shops.R
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class FetchingActivityTest {

    @Mock
    private lateinit var mockShopRView: RecyclerView

    @Mock
    private lateinit var mockTvLoadingData: TextView

    @Mock
    private lateinit var mockDbRef: DatabaseReference

    private lateinit var fetchingActivity: FetchingActivity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fetchingActivity = FetchingActivity()
        fetchingActivity.shopRView = mockShopRView
        fetchingActivity.tvLoadingData = mockTvLoadingData
        fetchingActivity.dbRef = mockDbRef
    }

    @Test
    fun getShopsData_startsLoadingState() {
        // Arrange
        `when`(mockShopRView.visibility).thenReturn(View.GONE)
        `when`(mockTvLoadingData.visibility).thenReturn(View.VISIBLE)

        // Act
        fetchingActivity.getShopsData()

        // Assert
        verify(mockShopRView).visibility = eq(View.GONE)
        verify(mockTvLoadingData).visibility = eq(View.VISIBLE)
    }

    @Test
    fun onDataChange_withExistingSnapshot_updatesShopListAndAdapter() {
        // Arrange
        val mockSnapshot = mock(DataSnapshot::class.java)
        val mockShopSnap = mock(DataSnapshot::class.java)
        val mockShopData = mock(ShopModel::class.java)
        val shopList = mutableListOf<ShopModel>(mockShopData)
        `when`(mockSnapshot.exists()).thenReturn(true)
        `when`(mockSnapshot.children).thenReturn(listOf(mockShopSnap))
        `when`(mockShopSnap.getValue(ShopModel::class.java)).thenReturn(mockShopData)
        `when`(mockShopSnap.getValue(ShopModel::class.java)).thenReturn(mockShopData)
        fetchingActivity.shopList = shopList as ArrayList<ShopModel>
        val mockAdapter = mock(ShopAdapter::class.java)
        `when`(mockAdapter.setOnItemClickListener(any())).thenAnswer {
            val listener = it.arguments[0] as ShopAdapter.onItemClickListener
            listener.onItemClick(0)
        }
        `when`(mockShopRView.adapter).thenReturn(mockAdapter)

        // Act
        fetchingActivity.getShopsData()

        // Assert
        assertEquals(1, fetchingActivity.shopList.size)
        verify(mockShopRView).adapter = mockAdapter
        verify(mockAdapter).setOnItemClickListener(any())
        verify(mockShopRView).visibility = eq(View.VISIBLE)
        verify(mockTvLoadingData).visibility = eq(View.GONE)
    }

}
