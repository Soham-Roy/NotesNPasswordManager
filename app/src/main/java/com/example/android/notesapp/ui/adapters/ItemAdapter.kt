package com.example.android.notesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.notesapp.database.Item
import com.example.android.notesapp.databinding.SingleListItemBinding
import java.text.SimpleDateFormat

class ItemAdapter(private val listener: ItemClickListener)
    : ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return ( oldItem.id == newItem.id )
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return ( oldItem == newItem )
        }
    }

    fun getItemByPosition(position: Int) : Item = getItem(position)

    inner class ItemViewHolder(private val binding: SingleListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                with(getItem(position)){
                    itemTitle.text = this.title
                    itemDateUpdated.text = SimpleDateFormat("YYYY, MMM d, h:mm:ss a")
                        .format(this.date)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder( SingleListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false ) )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener { listener.onItemClicked(position) }
    }

}