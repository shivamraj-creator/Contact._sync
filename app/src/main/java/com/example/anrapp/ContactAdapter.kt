package com.example.anrapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsync.databinding.ItemContactBinding

class ContactAdapter :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contacts = mutableListOf<ContactEntity>()

    fun submitList(list: List<ContactEntity>) {
        contacts.clear()
        contacts.addAll(list)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(
        val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {

        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {

        val contact = contacts[position]

        holder.binding.tvName.text = contact.name

        holder.binding.tvPhone.text = contact.phoneNumber

        holder.binding.tvAvatar.text =
            contact.name.firstOrNull()?.uppercase() ?: "#"
    }

    override fun getItemCount(): Int =
        contacts.size
}