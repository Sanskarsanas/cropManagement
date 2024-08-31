package com.example.crop_management

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelFor_Product_History : ViewModel() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("SellProducts")

    private val _items = MutableStateFlow<List<SellProduct>>(emptyList())
    val items: StateFlow<List<SellProduct>> get() = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<SellProduct>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(SellProduct::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                _items.value = itemList
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}