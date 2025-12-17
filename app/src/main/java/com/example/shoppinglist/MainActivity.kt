package com.example.shoppinglist

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ShoppingAdapter
    private var nextId: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ShoppingAdapter { pos ->
            confirmDelete(pos)
        }

        val rv = findViewById<RecyclerView>(R.id.rvItems)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val input = EditText(this)
        input.hint = "Pl. tej, kenyér..."

        AlertDialog.Builder(this)
            .setTitle("Új elem")
            .setView(input)
            .setPositiveButton("Hozzáadás") { _, _ ->
                val name = input.text.toString().trim()
                if (name.length == 0) {
                    Toast.makeText(this, "Nem lehet üres!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                adapter.addItem(ShoppingItem(nextId++, name))
            }
            .setNegativeButton("Mégse", null)
            .show()
    }

    private fun confirmDelete(pos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Törlés")
            .setMessage("Biztos el szeretnéd távolítani?")
            .setPositiveButton("Igen") { _, _ ->
                adapter.removeAt(pos)
                Toast.makeText(this, "Törölve", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Mégse", null)
            .show()
    }
}
