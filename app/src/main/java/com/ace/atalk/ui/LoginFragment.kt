package com.ace.atalk.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ace.atalk.R
import com.ace.atalk.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
//    private val viewModel: LoginFragmentViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        setOnclickListeners()
    }

    private fun setOnclickListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                login(email, password)
            }
            tvGoToRegister.setOnClickListener {
                gotoRegister()
            }
        }
    }

    private fun login(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        var accountId = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("create user", "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        accountId = user.uid
                    }
                    gotoHome()
                    binding.progressBar.visibility = View.GONE
//                        updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("create user", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
//                        updateUI(null)
                }
            }
    }

    private fun gotoHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }


    private fun gotoRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

}