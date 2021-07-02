package com.theagilemonkeys.musictam.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : ViewDataBinding, E>(var layoutId: Int) :
    RecyclerView.Adapter<ItemViewHolder<T>>() {

    var listOfItems: List<E> = arrayListOf()
        set(value) {
            if (field is ArrayList<E>) {
                (field as? ArrayList<E>)?.clear()
                (field as? ArrayList<E>)?.addAll(value)
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {

        val binding =
            DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)
        return ItemViewHolder(binding!!);
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        val item = listOfItems[position]
        bindItem(holder.binding, item, position)
    }

    abstract fun bindItem(binding: T, item: E, position: Int)
}

class ItemViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root) {}