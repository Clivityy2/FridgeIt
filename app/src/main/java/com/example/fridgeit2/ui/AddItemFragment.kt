package com.example.fridgeit2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.R
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.data.ItemDatabase
import com.example.fridgeit2.databinding.FragmentAddItemBinding
import com.example.fridgeit2.repository.ItemRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddItemFragment : Fragment(R.layout.fragment_add_item) {
    private lateinit var binding: FragmentAddItemBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddItemBinding.bind(view)
        val btnAdd = binding.btnAdd
        val btnClose = binding.btnClose
        val dao = ItemDatabase.getInstance(requireContext()).itemDAO
        val repository = ItemRepository(dao)
        val itemRecyclerView = requireActivity().findViewById<RecyclerView>(R.id.rvItems)
        val factory = ItemViewModelFactory(repository, itemRecyclerView)
        itemViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]

        btnAdd.setOnClickListener{
            val itemName = binding.etFragmentItemName.text.toString()
            val itemExpiryString = binding.etItemExpiryDate.text.toString()


            if (itemName.isNotEmpty() && itemExpiryString.isNotEmpty()){
                val dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy")
                val expiryDate = LocalDate.parse(itemExpiryString, dateFormatter)
                val item = Item(null,itemName,expiryDate)
                Toast.makeText(requireContext(),"$itemName Was Added Successfully",
                Toast.LENGTH_SHORT).show()
                itemViewModel.upsertItem(item)
                findNavController().navigate(R.id.action_addItemFragment_to_homeFragment)
                binding.etFragmentItemName.text.clear()
                binding.etItemExpiryDate.text.clear()
            }
            else{
                Toast.makeText(requireContext(),"Could Not Add Item, Please Ensure You Have Filled Out All Fields",
                    Toast.LENGTH_SHORT).show()
            }
        }

        btnClose.setOnClickListener(){
            findNavController().navigate(R.id.action_addItemFragment_to_homeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddItemFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}