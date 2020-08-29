package com.marcoscg.dialogsheetsample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marcoscg.dialogsheet.DialogSheet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            createAndShowDialog()
        }
    }

    private fun createAndShowDialog() {
        val useNewDialogStyle = newStyleCheckBox.isChecked

        val dialogSheet = DialogSheet(this@MainActivity, useNewDialogStyle)
                .setTitle(R.string.app_name)
                .setMessage(R.string.lorem)
                .setSingleLineTitle(true)
                .setColoredNavigationBar(true) //.setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .setPositiveButton(android.R.string.ok, object: DialogSheet.OnPositiveClickListener {
                    override fun onClick(v: View?) {
                        Toast.makeText(this@MainActivity, "Positive button clicked!", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton(android.R.string.cancel)
                .setNeutralButton("Neutral")
                //.setNeutralButtonColor(Color.BLACK)

        if (customViewCheckBox.isChecked) {
            dialogSheet.setView(R.layout.custom_dialog_view)

            // Access dialog custom inflated view
            val inflatedView = dialogSheet.inflatedView
            val button = inflatedView?.findViewById<Button>(R.id.customButton)
            button?.setOnClickListener {
                Toast.makeText(this@MainActivity, "I'm a custom button", Toast.LENGTH_SHORT).show()
            }
        }

        if (!cornersCheckBox.isChecked)
            dialogSheet.setRoundedCorners(false)

        if (iconCheckBox.isChecked)
            dialogSheet.setIconResource(R.mipmap.ic_launcher)

        dialogSheet.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_github) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/marcoscgdev/DialogSheet")))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}