package com.express.mviexample.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.express.mviexample.api.AnimalAPI
import com.express.mviexample.api.AnimalService
import com.express.mviexample.databinding.ItemAnimalBinding
import com.express.mviexample.model.Animal

class AnimalRVAdapter(private val animals:ArrayList<Animal>):
    RecyclerView.Adapter<AnimalRVAdapter.AnimalViewHolder>() {
    private lateinit var binding:ItemAnimalBinding

    class AnimalViewHolder(val binding:ItemAnimalBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal){
            binding.data = animal
            val url = AnimalService.BASE_URL +  animal.image
            Glide.with(binding.animalImage.context).load(url).into(binding.animalImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(animals[position])
    }

    override fun getItemCount() = animals.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAnimals(list:List<Animal>){
        animals.clear()
        animals.addAll(list)
        notifyDataSetChanged()
    }
}