package com.example.expensetracker_shops.Activities

import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class InsertionActivityTest {

    @Mock
    private lateinit var mockShopName: EditText

    @Mock
    private lateinit var mockShopAddress: EditText

    @Mock
    private lateinit var mockShopMobNo: EditText

    @Mock
    private lateinit var mockDbRef: DatabaseReference

    private lateinit var insertionActivity: InsertionActivity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        insertionActivity = InsertionActivity()
        insertionActivity.shopName = mockShopName
        insertionActivity.shopAddress = mockShopAddress
        insertionActivity.shopMobNo = mockShopMobNo
        insertionActivity.dbRef = mockDbRef
    }

    @Test
    fun saveShopsData_withValidData_insertsDataSuccessfully() {
        // Arrange
        val sName = "TestName"
        val sAddress = "Test Address"
        val sMob = "0711234567"

        val shopNameEditableMock = mock(Editable::class.java)
        `when`(shopNameEditableMock.toString()).thenReturn(sName)
        `when`(mockShopName.text).thenReturn(shopNameEditableMock)

        val shopAddressEditableMock = mock(Editable::class.java)
        `when`(shopAddressEditableMock.toString()).thenReturn(sAddress)
        `when`(mockShopAddress.text).thenReturn(shopAddressEditableMock)

        val shopMobNoEditableMock = mock(Editable::class.java)
        doReturn(sMob).`when`(shopMobNoEditableMock).toString()
        `when`(mockShopMobNo.text).thenReturn(shopMobNoEditableMock)

        `when`(mockDbRef.push()).thenReturn(mockDbRef)
        `when`(mockDbRef.key).thenReturn("shopId")

        // Act
        insertionActivity.saveShopsData()

        // Assert
        // Verify that no error is set for EditText fields
        verify(mockShopName, never()).error = eq(null)
        verify(mockShopAddress, never()).error = eq(null)
        verify(mockShopMobNo, never()).error = eq(null)

        // Verify that the correct child and value are set on DatabaseReference
        verify(mockDbRef).child("shopId")
        verify(mockDbRef).setValue(any())

        // Verify that EditText fields are cleared
        verify(mockShopName).text?.clear()
        verify(mockShopAddress).text?.clear()
        verify(mockShopMobNo).text?.clear()
    }


    @Test
    fun saveShopsData_withEmptyName_showsErrorMessage() {
        // Arrange
        val sName = "" // Empty name

        `when`(mockShopName.text.toString()).thenReturn(sName)


        // Act
        insertionActivity.saveShopsData()


        // Assert
        // Verify that an error message is set for the shop name EditText field
        verify(mockShopName).error = anyString()

        // Verify that child and value operations are not performed on the DatabaseReference
        verify(mockDbRef, never()).child(anyString())
        verify(mockDbRef, never()).setValue(any())

        // Verify that no error messages are set for the shop address and shop mobile number EditText fields
        verify(mockShopAddress, never()).error = anyString()
        verify(mockShopMobNo, never()).error = anyString()
    }

}
