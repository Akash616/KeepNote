package com.akashgupta.keepnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.akashgupta.keepnote.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null //nullable type
    private val binding get() = _binding!! //null safe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onDestroyView() { //for performance
        super.onDestroyView()
        _binding = null
    }

}