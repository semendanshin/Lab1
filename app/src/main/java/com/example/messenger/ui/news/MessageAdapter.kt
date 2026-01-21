package com.example.messenger.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.model.Message
import com.example.messenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val differ = AsyncListDiffer(this, MessageDiffCallback())

    fun submitList(list: List<Message>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    class MessageViewHolder(
        private val binding: ItemMessageBinding,
        private val onLikeClick: (Message) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvName.text = message.title
            binding.tvMessage.text = message.body

            val likeIcon = if (message.isLiked) {
                R.drawable.ic_like_filled
            } else {
                R.drawable.ic_like_outline
            }
            binding.btnLike.setImageResource(likeIcon)
            binding.btnLike.setOnClickListener { onLikeClick(message) }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
