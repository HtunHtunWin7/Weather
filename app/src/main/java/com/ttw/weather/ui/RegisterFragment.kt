package com.ttw.weather.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ttw.weather.R
import com.ttw.weather.databinding.FragmentRegisterBinding
import com.ttw.weather.utils.isValidEmail
import com.ttw.weather.utils.showAlertDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegisterBinding

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnRegister.setOnClickListener {
            register()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun register() {
        val name = binding.txtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val confirm_password = binding.edtConfirmPassword.text.toString()
        if (name == "") {
            showAlertDialog(requireContext(), "Please fill correct data", "Name is empty")
        } else if (!isValidEmail(email)) {
            showAlertDialog(
                requireContext(),
                "Please fill correct data",
                "Please type correct mail format"
            )
        } else if (password.toCharArray().count() < 6) {
            showAlertDialog(
                requireContext(),
                "Please fill correct data",
                "Password must have minimum 6 character"
            )
        } else if (password.toCharArray().count() > 12) {
            showAlertDialog(
                requireContext(),
                "Please fill correct data",
                "Password must have maximum 12 character"
            )
        } else if (password == "") {
            showAlertDialog(requireContext(), "Please fill correct data", "Password is empty")
        } else if (password != confirm_password) {
            showAlertDialog(requireContext(), "Please fill correct data", "Password is not match")

        } else {
            loadingState()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Registration Successful",
                            Toast.LENGTH_LONG
                        ).show()
                        finishLoading()
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    } else {
                        finishLoading()
                        task.exception?.message?.let {
                            showAlertDialog(
                                requireContext(), "Something went wrong",
                                it
                            )
                        }
                    }
                }
        }
    }

    private fun loadingState(){
        binding.progressBar.visibility = View.VISIBLE
        binding.registerCardView.visibility = View.GONE
    }

    private fun finishLoading(){
        binding.progressBar.visibility = View.GONE
        binding.registerCardView.visibility = View.VISIBLE
    }


}