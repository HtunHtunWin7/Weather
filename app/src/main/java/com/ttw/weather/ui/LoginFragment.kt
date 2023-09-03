package com.ttw.weather.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ttw.weather.R
import com.ttw.weather.databinding.FragmentLoginBinding
import com.ttw.weather.utils.isValidEmail
import com.ttw.weather.utils.snkBar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        binding.btnLogin.setOnClickListener {
            signIn()
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
        return binding.root
    }

    private fun signIn() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "wrong email format", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(requireContext(), "password must be greater than 6", Toast.LENGTH_SHORT)
                .show()
        } else {
            loadingState()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Success Login", Toast.LENGTH_SHORT).show()
                        finishLoading()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        finishLoading()
                        task.exception?.message?.let { snkBar(binding.root, it) }
                    }
                }
        }

    }

    private fun loadingState(){
        binding.progressBar.visibility = View.VISIBLE
        binding.loginCardView.visibility = View.GONE
    }

    private fun finishLoading(){
        binding.progressBar.visibility = View.GONE
        binding.loginCardView.visibility = View.VISIBLE
    }
}