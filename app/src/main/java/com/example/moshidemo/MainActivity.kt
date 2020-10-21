package com.example.moshidemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.io.IOException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val tag: String = MainActivity::class.java.simpleName

    private lateinit var adapter: PartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_parts.layoutManager = LinearLayoutManager(this)
        rv_parts.setHasFixedSize(true)
        adapter = PartAdapter(listOf(), { partItem: PartData -> partItemClicked(partItem) })
        rv_parts.adapter = adapter
        loadPartsAndUpdateList()
    }

    private fun loadPartsAndUpdateList() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){

            }
            try {
                val webResponse = WebAccess.partsApi.getPartsAsync().await()

                if (webResponse.isSuccessful) {
                    val partList: List<PartData>? = webResponse.body()
                    Log.d(tag, partList.toString())
                    adapter.partItemList = partList ?: listOf()
                    adapter.notifyDataSetChanged()
                } else {
                    // Print error information to the console
                    Log.e(tag, "Error ${webResponse.code()}")
                    Toast.makeText(
                        this@MainActivity,
                        "Error ${webResponse.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: IOException) {
                // Error with network request
                Log.e(tag, "Exception " + e.printStackTrace())
                Toast.makeText(this@MainActivity, "Exception ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun addPart(partItem: PartData) {
        GlobalScope.launch(Dispatchers.Main) {
            val webResponse = WebAccess.partsApi.addPartAsync(partItem).await()
            Log.d(tag, "Add success: ${webResponse.isSuccessful}")
            loadPartsAndUpdateList()
        }
    }


    private fun partItemClicked(partItem: PartData) {
        Toast.makeText(this, "Clicked: ${partItem.itemName}", Toast.LENGTH_LONG).show()

        val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        showDetailActivityIntent.putExtra("ItemId", partItem.id)
        showDetailActivityIntent.putExtra("ItemName", partItem.itemName)
        startActivity(showDetailActivityIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.cancel()
    }

}