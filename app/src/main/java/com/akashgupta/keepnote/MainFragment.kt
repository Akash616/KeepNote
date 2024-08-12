package com.akashgupta.keepnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.akashgupta.keepnote.databinding.FragmentMainBinding
import com.akashgupta.keepnote.models.note.NoteResponse
import com.akashgupta.keepnote.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()//fragment extension

    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = NoteAdapter(::onNoteClicked) //we are passing this fun(onNoteClicked) ref. into adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        noteViewModel.getNotes()
        binding.rvNoteList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNoteList.adapter = adapter
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    private fun bindObservers() {
        //jasa hi notesLiveData mai kuch bhi change hoga, toh humara ya Observer{} call hoga
        noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            //Now yaha par NetworkResult ayaga
            when(it){
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteResponse: NoteResponse){
        val bundle = Bundle()
        bundle.putString("note", Gson().toJson(noteResponse)) //serialize using Gson
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}