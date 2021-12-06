package com.jereschneider.test_talana.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.jereschneider.test_talana.databinding.ActivityLoginBinding
import com.jereschneider.test_talana.models.Login
import com.jereschneider.test_talana.ui.feed.FeedActivity
import com.jereschneider.test_talana.utils.isEmailValid
import com.jereschneider.test_talana.utils.observe

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var isAnimLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenerEmail()
        listenerPassword()
        listenerButton()

        observe(viewModel.success){
            if (it){
                viewModel.saveKeepLogin(binding.checkKeepLogin.isChecked)
                updateAnimation(viewModel.SUCCESS)
                startActivity(Intent(this, FeedActivity::class.java))
            }
        }

        observe(viewModel.error){
            isAnimLogin = false
            updateAnimation(viewModel.FAILURE)
            viewModel.showToast(it.message)
        }
    }

    private fun updateAnimation(anim: Int) {
        binding.lottieAnimationView.setAnimation(anim)
        binding.lottieAnimationView.playAnimation()
    }

    private fun listenerButton(){
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.editText?.text.toString()
            val pass = binding.inputPassword.editText?.text.toString()

            if (isEmailValid(email) && pass.length > 4){
                viewModel.login(Login(email, pass))
            }
            else if(!isEmailValid(email)) binding.inputEmail.error = "Email inválido"
            else if(pass.length <= 4) binding.inputPassword.error = "Password inválido"
        }
    }

    private fun listenerEmail(){
        binding.inputEmail.editText?.setOnClickListener {
            if (!isAnimLogin){
                updateAnimation(viewModel.LOGIN)
                isAnimLogin = true
            }
            binding.inputEmail.error = null
        }

        binding.inputEmail.editText?.addTextChangedListener {
            if(!isEmailValid(it.toString())) binding.inputEmail.error = "Email inválido"
            else binding.inputEmail.error = null
        }
    }

    private fun listenerPassword(){
        binding.inputPassword.editText?.setOnClickListener {
            if (!isAnimLogin){
                updateAnimation(viewModel.LOGIN)
                isAnimLogin = true
            }
            binding.inputPassword.error = null
        }

        binding.inputPassword.editText?.addTextChangedListener {
            if(it.toString().length <= 4) binding.inputPassword.error = "Password inválido"
            else binding.inputPassword.error = null
        }
    }

}