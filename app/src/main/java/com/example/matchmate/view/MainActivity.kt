package com.example.matchmate.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchmate.R
import com.example.matchmate.data.db.AppDatabase
import com.example.matchmate.data.db.UserEntity
import com.example.matchmate.data.model.UserData
import com.example.matchmate.databinding.ActivityMainBinding
import com.example.matchmate.repository.HomeRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchAdapter: MatchAdapter
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).userDao()
        val repository = HomeRepository(dao)
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        initAdapter()
        observeViewModel()

        viewModel.loadUsers()
    }
    fun initAdapter() {
        matchAdapter = MatchAdapter(listOf()) { user, action ->
            viewModel.updateUserStatus(user, action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = matchAdapter
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            matchAdapter.updateList(users)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_all -> {
                viewModel.loadUsers("all")
                true
            }
            R.id.filter_accepted -> {
                viewModel.loadUsers("accepted")
                true
            }
            R.id.filter_declined -> {
                viewModel.loadUsers("declined")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}