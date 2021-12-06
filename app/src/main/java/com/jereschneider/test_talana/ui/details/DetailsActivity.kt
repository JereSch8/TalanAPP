package com.jereschneider.test_talana.ui.details

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.jereschneider.test_talana.R
import com.jereschneider.test_talana.databinding.ActivityDetailsBinding
import com.jereschneider.test_talana.utils.observe
import java.lang.Exception
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import com.jereschneider.test_talana.models.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailsActivity : AppCompatActivity() {
    companion object { const val ID_POST = "id_post" }

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        val idPost = intent.getLongExtra(ID_POST,-1)

        if(idPost != (-1).toLong()){
            observe(viewModel.getFavouriteByID(idPost)){
                try {
                    if (it.id_post == idPost)
                        binding.favourite.setImageResource(R.drawable.ic_star)
                }catch (e : Exception){
                    binding.favourite.setImageResource(R.drawable.ic_star_outline)
                }
            }

            observe(viewModel.getPostByID(idPost)){ post ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        val date = LocalDate.parse(post.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        binding.date.text = date.toString()
                    }
                    catch (e : Exception){  binding.date.text = post.date }
                }
                else
                    binding.date.text = post.date

                setImg(post.image)
                binding.title.text = post.title
                binding.description.movementMethod = ScrollingMovementMethod()
                binding.description.text = post.description

                observe(viewModel.getUserByID(post.authorID.toLong())){ user ->
                    setAttributesUser(user)
                }
            }

            observe(viewModel.error){
                Toast.makeText(this, "No se pudo guardar: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun setupActionBar(){
        if (supportActionBar != null) {
            supportActionBar?.title = "Detalles"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAttributesUser(user : User){
        try {
            val strAvatar = "${user.firstName[0]}${user.lastName[0]}"
            val strName = "${user.firstName} ${user.lastName}"
            binding.strAvatar.text = strAvatar
            binding.name.text = strName
            binding.avatar.setCardBackgroundColor(
                if (user.gender == "F")  resources.getColor(R.color.fuchsia)
                else resources.getColor(R.color.orange)
            )
        }catch (e : Exception){
            binding.strAvatar.text = R.string.ee.toString()
            binding.name.text = R.string.error.toString()
            binding.avatar.setCardBackgroundColor(resources.getColor(R.color.red_dark))
        }
    }

    private fun setImg(url : String){
        Glide.with(this)
            .load(url)
            .transform(CenterCrop())
            .placeholder(R.color.grey_40)
            .error(R.drawable.ic_not_image)
            .into(binding.img)
    }

}