package com.ikhsan.submissionusergithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikhsan.submissionusergithub.databinding.ItemUserBinding
import com.ikhsan.submissionusergithub.response.UserResponseItem
import com.ikhsan.submissionusergithub.util.ColorType.setColor

class UserAdapter(): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val listUser = ArrayList<UserResponseItem>()
    private var onItemClickCallBack: OnItemClickCallback?=null

    fun setList(items: List<UserResponseItem>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

     inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun userData(data: UserResponseItem) {
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(data)
            }
            binding.tvName.text = data.login
            binding.tvTypeDetail.setColor(itemView.context, data.type)
            Glide.with(itemView)
                .load(data.avatarUrl)
                .into(binding.imgProfile)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.userData(listUser[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponseItem)
    }
}