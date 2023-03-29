package com.ace.atalk.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.atalk.R
import com.ace.atalk.data.model.User
import com.ace.atalk.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("User")
        setOnclickListeners()
    }

    private fun setOnclickListeners() {
        with(binding) {
            btnRegister.setOnClickListener{
                register()
            }
            tvGoToLogin.setOnClickListener{
                gotoLogin()
            }
        }
    }

    private fun register() {
        with(binding){
            var accountId = ""
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("create user", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        if (user != null) {
                            accountId = user.uid
                        }
                        databaseReference = firebaseDatabase.getReference(accountId)
                        gotoLogin()
                        insertToDatabase(username,email, accountId)
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("create user", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }
//            database.child("users").child(accountId).setValue(user)
        }
    }

    private fun insertToDatabase(username: String, email: String, accountId: String) {
        val user = User(accountId, username, email, null, null)
        databaseReference.setValue(user)
    }

    private fun gotoLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}