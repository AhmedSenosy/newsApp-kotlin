package com.senosy.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senosy.newsapp.data.models.ArticleModel
import com.senosy.newsapp.databinding.ItemRecyclerArticleBinding
import com.senosy.newsapp.ui.interfaces.OnRecyclerItemClickListener

class ArticleAdapter  constructor() : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private var list: List<ArticleModel>? = null
    private var listener: OnRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val holder = holder
        with(holder.binding)
        {
            model = list!![holder.layoutPosition]
            layoutItemRecyclerArticle.setOnClickListener {
                listener?.let {
                    listener?.onRecyclerItemClickListener(list!![holder.layoutPosition])
                }
            }
            showDivider = position != list?.size!! - 1
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<ArticleModel>) {
        this.list = list
        notifyDataSetChanged()

    }

    fun addListener(listener: OnRecyclerItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(var binding: ItemRecyclerArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

}