package com.example.crop_management


import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewmodelforReminderNotification : ViewModel() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("notification")

    private val _items = MutableStateFlow<List<NotificationClass>>(emptyList())
    val items: StateFlow<List<NotificationClass>> get() = _items

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchItems()
    }

    private fun fetchItems() {

        _isLoading.value = true  // Start loading

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<NotificationClass>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(NotificationClass::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                _items.value = itemList
                _isLoading.value = false  // Stop loading after data is retrieved
            }

            override fun onCancelled(error: DatabaseError) {
                _isLoading.value = false  // Stop loading after data is retrieved
            }
        })
    }
}


//class ViewmodelforReminderNotification : ViewModel() {
//    private val _data = MutableStateFlow<NotificationClass?>(null)
//    val data = _data.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(true)
//    val isLoading = _isLoading.asStateFlow()
//
//    init {
//        fetchDataFromFirebase()
//    }
//
//    private fun fetchDataFromFirebase() {
//        _isLoading.value = true // Start loading
//        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("myPath")
//
//        database.get().addOnSuccessListener { dataSnapshot ->
//            _data.value = dataSnapshot.getValue(NotificationClass::class.java)
//            _isLoading.value = false // Stop loading
//        }.addOnFailureListener { exception ->
//            _isLoading.value = false // Stop loading on error
//        }
//    }
//}
