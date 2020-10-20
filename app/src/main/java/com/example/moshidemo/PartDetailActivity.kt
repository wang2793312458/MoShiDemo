package com.example.moshidemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_part_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class PartDetailActivity : AppCompatActivity() {
    private val tag: String = PartDetailActivity::class.java.simpleName
    private var originalItemId: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_detail)
        originalItemId = intent.getLongExtra("ItemId", 0)
        tv_item_id.text = originalItemId.toString()
        et_item_name.setText(intent.getStringExtra("ItemName"))

        bt_delete.setOnClickListener {
            deletePart(originalItemId)
        }

        bt_update.setOnClickListener {
            updatePart(
                originalItemId,
                PartData(originalItemId, et_item_name.text.toString())
            )
        }
    }


    private fun deletePart(itemId: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = WebAccess.partsApi.deletePartAsync(itemId).await()
                Log.e(tag, "Delete success: ${webResponse.isSuccessful}")
                Toast.makeText(
                    applicationContext,
                    "Deleted: $itemId: ${webResponse.isSuccessful}",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: IOException) {
                // Error with network request
                Log.e(tag, "Exception " + e.printStackTrace())
                Toast.makeText(this@PartDetailActivity, "Exception ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun updatePart(originalItemId: Long, newItem: PartData) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse =
                    WebAccess.partsApi.updatePartAsync(originalItemId, newItem).await()
                Log.e(tag, "Update success: ${webResponse.isSuccessful}")
                Toast.makeText(
                    applicationContext,
                    "Updated: $originalItemId: ${webResponse.isSuccessful}",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: IOException) {
                // Error with network request
                Log.e(tag, "Exception " + e.printStackTrace())
                Toast.makeText(this@PartDetailActivity, "Exception ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}