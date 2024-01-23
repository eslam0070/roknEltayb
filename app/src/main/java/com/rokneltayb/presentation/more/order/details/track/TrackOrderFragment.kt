package com.rokneltayb.presentation.more.order.details.track

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentOrderDetailsBinding
import com.rokneltayb.databinding.FragmentTrackOrderBinding

class TrackOrderFragment : Fragment() {

    private val binding by lazy { FragmentTrackOrderBinding.inflate(layoutInflater) }
    private val args :TrackOrderFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }


}