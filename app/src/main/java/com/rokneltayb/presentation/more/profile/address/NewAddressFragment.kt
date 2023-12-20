package com.rokneltayb.presentation.more.profile.address

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentNewAddressBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.presentation.more.profile.ProfileViewModel

class NewAddressFragment : Fragment() {

    companion object {
        fun newInstance() = NewAddressFragment()
    }
    private val binding by lazy { FragmentNewAddressBinding.inflate(layoutInflater) }
    private val viewModel: NewAddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }



}