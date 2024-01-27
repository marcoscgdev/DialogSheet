package com.marcoscg.dialogsheetsample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marcoscg.dialogsheet.DialogSheet
import com.marcoscg.dialogsheetsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            createAndShowDialog()
        }
    }

    private fun createAndShowDialog() {
        val useNewDialogStyle = binding.newStyleCheckBox.isChecked

        val dialogSheet = DialogSheet(this@MainActivity, useNewDialogStyle) // you can also use DialogSheet2 if you want the new style
                //.setNewDialogStyle() // You can also set new style by this method, but put it on the first line
                .setTitle(R.string.app_name)
                .setMessage(R.string.lorem)
                .setSingleLineTitle(true)
                .setColoredNavigationBar(true)
                //.setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .setPositiveButton(android.R.string.ok) {
                    Toast.makeText(this@MainActivity, "Positive button clicked!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(android.R.string.cancel)
                .setNeutralButton("Neutral")
                //.setNeutralButtonColor(Color.BLACK)

        if (binding.customViewCheckBox.isChecked) {
            dialogSheet.setView(R.layout.custom_dialog_view)

            // Access dialog custom inflated view
            val inflatedView = dialogSheet.inflatedView
            val button = inflatedView?.findViewById<Button>(R.id.customButton)
            button?.setOnClickListener {
                Toast.makeText(this@MainActivity, "I'm a custom button", Toast.LENGTH_SHORT).show()
            }
        }

        if (!binding.cornersCheckBox.isChecked)
            dialogSheet.setRoundedCorners(false)

        if (binding.iconCheckBox.isChecked)
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