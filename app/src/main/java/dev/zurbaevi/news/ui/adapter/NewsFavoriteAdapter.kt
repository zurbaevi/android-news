package dev.zurbaevi.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.databinding.ItemArticleBinding

class NewsFavoriteAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<Articles, NewsFavoriteAdapter.NewsViewHolder>(NewsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.bind(current)
        }
    }

    inner class NewsViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        clickListener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(articles: Articles) {
            binding.apply {
                Glide.with(itemView)
                    .load(articles.urlToImage)
                    .into(imageView)
                textViewTitle.text = articles.title
                textViewDescription.text = articles.description
            }
        }

    }

    object NewsDiffCallback : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles) = oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClick(articles: Articles)
    }

}