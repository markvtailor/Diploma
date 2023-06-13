package com.markvtls.diploma_aug.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.markvtls.diploma_aug.databinding.ItemImageBinding
import com.markvtls.diploma_aug.domain.model.ImageModel

class ImagesAdapter(private val context: Context,
    private val toOrderForm: (String, String, String, String) -> Unit,
    private val isOrdering: Boolean) : ListAdapter<ImageModel, ImagesAdapter.ImageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false), context, toOrderForm, isOrdering)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val context: Context,
        private val toOrderForm: (String, String, String, String) -> Unit,
        private val isOrdering: Boolean
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(imageModel: ImageModel) {
            with(binding) {
                Glide.with(context)
                    .load(imageModel.imageUri.toUri())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.image)

                imageAuthor.text = imageModel.imageAuthor
                imageName.text = imageModel.imageName
                price.text = "${imageModel.imagePrice} P"

                proceedButton.setOnClickListener {
                    toOrderForm(
                        imageModel.imageAuthor,
                        imageModel.imageName,
                        imageModel.imagePrice.toString(),
                        imageModel.imageUri
                    )
                }

                if (!isOrdering) {
                    proceedButton.visibility = View.GONE
                }

            }
        }

    }


    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem.imageUri == newItem.imageUri
            }

        }
    }
}