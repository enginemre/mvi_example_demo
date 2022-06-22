package com.express.mviexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.express.mviexample.adapters.AnimalRVAdapter
import com.express.mviexample.api.AnimalService
import com.express.mviexample.databinding.ActivityMainBinding
import com.express.mviexample.model.Animal
import com.express.mviexample.view.MainIntent
import com.express.mviexample.view.MainState
import com.express.mviexample.viewmodel.AnimalViewModel
import com.express.mviexample.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var animalViewModel: AnimalViewModel
    private var adapter = AnimalRVAdapter(arrayListOf<Animal>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
        observeUI()
    }



    private fun initUI(){
        animalViewModel = ViewModelProviders.of(this,ViewModelFactory(AnimalService.api))[AnimalViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context,(binding.recyclerView.layoutManager as LinearLayoutManager).orientation ))
        binding.buttonFetchAnimals.setOnClickListener {
           lifecycleScope.launch {
               animalViewModel.userIntent.send(MainIntent.FetchAnimals)
           }
        }
    }

    private fun observeUI(){
        lifecycleScope.launch {
            animalViewModel.state.collect {
                when(it){
                    is MainState.Idle->{}
                    is MainState.Loading -> {
                        binding.buttonFetchAnimals.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Animals-> {
                        binding.buttonFetchAnimals.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        it.animals.let { animals->
                            adapter.updateAnimals(animals)
                        }

                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchAnimals.visibility = View.GONE
                        Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}