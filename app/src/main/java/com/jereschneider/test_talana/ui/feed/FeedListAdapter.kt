package com.jereschneider.test_talana.ui.feed

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

import com.jereschneider.test_talana.R
import com.jereschneider.test_talana.databinding.ItemFeedBinding
import com.jereschneider.test_talana.models.Favourite
import com.jereschneider.test_talana.models.Post
import com.jereschneider.test_talana.models.User
import com.jereschneider.test_talana.utils.getDifferenceDate
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FeedListAdapter constructor(
    private val context : Context,
    private var listPost: List<Post>,
    private var listUser: List<User>,
    private var listFav: List<Favourite>,
    private val onClick: (item: Post) -> Unit,
    private val onClickFav: (idPost : Long) -> Unit
) : RecyclerView.Adapter<FeedListAdapter.AttractionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val v = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(v)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(listPost[position])
    }

    override fun getItemCount(): Int { return listPost.size }

    fun updatePost(list: List<Post>) {
        this.listPost = list
        notifyDataSetChanged()
    }
    fun updateUser(list: List<User>) {
        this.listUser = list
        notifyDataSetChanged()
    }
    fun updateFavourite(list: List<Favourite>) {
        this.listFav = list
        notifyDataSetChanged()
    }

    inner class AttractionViewHolder(private val v: ItemFeedBinding) :
        RecyclerView.ViewHolder(v.root) {
        fun bind(item: Post) {
            val user = getUser(item.authorID.toLong())
            setAttributesUser(user, v)
            v.title.text = item.title
            v.description.text = item.description
            Glide.with(v.root)
                .load(item.image)
                .transform(CenterCrop())
                .placeholder(R.color.grey_40)
                .error(R.drawable.ic_not_image)
                .into(v.img)

            val date = getDate(item.published)
            if (date != (-1).toLong())
                v.date.text = getDifferenceDate(date)
            else
                v.date.text = item.published

            if (isFavourite(item.id))
                v.favourite.setImageResource(R.drawable.ic_star)
            else
                v.favourite.setImageResource(R.drawable.ic_star_outline)

            v.favourite.setOnClickListener { onClickFav(item.id) }

            v.root.setOnClickListener { onClick(item) }
        }
    }

    private fun getDate(published : String) : Long{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val datePublished = LocalDateTime.parse(
                published,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            )
            return  ChronoUnit.HOURS.between(datePublished, LocalDateTime.now())
        }
        return -1
    }

    private fun getUser(id : Long) : User{
        val listUser = listUser.filter { user -> user.id == id }
        return if (!listUser.isNullOrEmpty()) listUser[0]
                else User(-1,"E", "E", "J")
    }

    private fun setAttributesUser(user : User, binding : ItemFeedBinding){
        try {
            val strAvatar = "${user.firstName[0]}${user.lastName[0]}"
            val strName = "${user.firstName} ${user.lastName}"
            binding.strAvatar.text = strAvatar
            binding.name.text = strName
            binding.avatar.setCardBackgroundColor(
                if (user.gender == "F")  context.getColor(R.color.fuchsia)
                else context.getColor(R.color.orange)
            )
        }catch (e : Exception){
            binding.strAvatar.text = R.string.ee.toString()
            binding.name.text = R.string.error.toString()
            binding.avatar.setCardBackgroundColor(context.getColor(R.color.red_dark))
        }
    }

    private fun isFavourite(idPost : Long) = listFav.find { fav -> fav.id_post == idPost } != null

}