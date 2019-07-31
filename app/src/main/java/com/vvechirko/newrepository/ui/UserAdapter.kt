package com.vvechirko.newrepository.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vvechirko.newrepository.R
import com.vvechirko.newrepository.data.UserEntity
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.Holder>() {

    var data: List<UserEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int = data.size


    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(it: UserEntity) {
            itemView.tvFullname.setText(it.fullName)
            itemView.tvEmail.setText(it.email)

            Picasso.get().load(it.avatar)
                .into(itemView.ivAvatar)
        }
    }
}