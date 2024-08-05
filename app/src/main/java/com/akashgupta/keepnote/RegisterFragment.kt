package com.akashgupta.keepnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.akashgupta.keepnote.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    //view binding: jitna bhi hamara layouts honga usko corresponding ak binding class generate kar di jaya gi.
    //Create binding object
    private var _binding: FragmentRegisterBinding? = null //nullable type
    private val binding get() = _binding!! //null safe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onDestroyView() { //for performance
        super.onDestroyView()
        _binding = null
    }

}