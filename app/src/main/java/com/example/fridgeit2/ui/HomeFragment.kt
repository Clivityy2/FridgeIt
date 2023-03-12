package com.example.fridgeit2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fridgeit2.FridgeItApp
import com.example.fridgeit2.NotificationService
import com.example.fridgeit2.R
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.data.ItemDatabase
import com.example.fridgeit2.databinding.FragmentHomeBinding
import com.example.fridgeit2.repository.ItemRepository

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var notificationService: NotificationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = ItemDatabase.getInstance(requireContext()).itemDAO
        //val db = ItemDatabase.getInstance(requireContext())
        val repository = ItemRepository(dao)
        val itemRecyclerView = binding.rvItems

        val fridgeItApp = requireActivity().application as FridgeItApp
        fridgeItApp.createNotificationChannel()

        notificationService = NotificationService(requireContext())
        //repository.deleteDatabase(requireContext())

        val factory = ItemViewModelFactory(requireContext(), repository, itemRecyclerView)
        itemViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = this

        val adapter = ItemRecyclerViewAdapter(onDeleteCallback = {item ->
            itemViewModel.deleteItem(item)
        })

        binding.rvItems.adapter = adapter

        initRecyclerView()
        itemViewModel.checkIfItemIsDueToExpire()

        val btnAddItem = binding.btnAddItem
        val btnDeleteAllItems = binding.btnDeleteAllItems


        btnAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addItemFragment)
        }
        btnDeleteAllItems.setOnClickListener{
            itemViewModel.deleteAllItems()
        }
    }

    private fun displayItemList(itemList: List<Item>) {
        itemViewModel.items.observe(viewLifecycleOwner, Observer {
            (binding.rvItems.adapter as ItemRecyclerViewAdapter).items = it
        })
    }

    private fun initRecyclerView() {
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        val itemTouchHelper = ItemTouchHelper(itemViewModel.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvItems)
        itemViewModel.items.observe(viewLifecycleOwner, Observer {
            displayItemList(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}