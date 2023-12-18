package com.rokneltayb.presentation.more

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {


    private val binding by lazy { FragmentMoreBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.myOrdersLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToOrderFragment())

        }
        binding.myWishlistLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToFavoritesFragment())
        }
        binding.myProfileLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToProfileFragment())
        }
        binding.aboutUsLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToAboutUsFragment())
        }
        binding.contactUsLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToContactUsFragment())
        }
        binding.privacyPolicyLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToPrivacyPolicyFragment())
        }

        binding.changeLanguageLinearLayout.setOnClickListener {

        }

        binding.logoutLinearLayout.setOnClickListener {
            logout()
        }
        return binding.root
    }

    private fun logout() {

    }


}