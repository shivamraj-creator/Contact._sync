package com.example.anrapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anrapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ContactAdapter

    private lateinit var repository: ContactRepository

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                loadContacts()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        setupRecyclerView()

        val dao =
            AppDatabase
                .getDatabase(this)
                .contactDao()

        repository = ContactRepository(dao)

        observeContacts()

        checkPermission()
    }

    private fun setupRecyclerView() {

        adapter = ContactAdapter()

        binding.rvContacts.layoutManager =
            LinearLayoutManager(this)

        binding.rvContacts.adapter =
            adapter

        binding.rvContacts.setHasFixedSize(true)
    }

    private fun observeContacts() {

        lifecycleScope.launch {

            repository.getContacts()
                .collect { contacts ->

                    adapter.submitList(contacts)
                }
        }
    }

    private fun checkPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            loadContacts()

        } else {

            requestPermissionLauncher.launch(
                Manifest.permission.READ_CONTACTS
            )
        }
    }

    private fun loadContacts() {

        lifecycleScope.launch {

            val contacts =
                ContactReader(contentResolver)
                    .getContacts()

            Log.d(
                "MainActivity",
                "Total Contacts = ${contacts.size}"
            )

            repository.saveContacts(
                contacts.map {
                    it.toEntity()
                }
            )
        }
    }

    private fun Contact.toEntity(): ContactEntity {
        return ContactEntity(
            id = id,
            name = name,
            phoneNumber = phoneNumber
        )

    }
}