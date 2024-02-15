package com.rokneltayb.presentation.more.order.details.track

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentOrderDetailsBinding
import com.rokneltayb.databinding.FragmentTrackOrderBinding

class TrackOrderFragment : Fragment() {

    private val binding by lazy { FragmentTrackOrderBinding.inflate(layoutInflater) }
    private val args :TrackOrderFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.order_status)
        (requireActivity() as BaseActivity).binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        when(args.status){
            "pending" ->{
                binding.layoutPending.visibility = View.VISIBLE
                binding.layoutPerpearing.visibility = View.GONE
                binding.layoutOnWay.visibility = View.GONE
                binding.layoutDelivered.visibility = View.GONE
                pendingStatus()
            }
            "preparing" ->{
                binding.layoutPerpearing.visibility = View.VISIBLE
                binding.layoutPending.visibility = View.VISIBLE
                binding.layoutOnWay.visibility = View.GONE
                binding.layoutDelivered.visibility = View.GONE
                pendingStatus()
                preparingStatus()
            }
            "on_way" ->{
                binding.layoutPerpearing.visibility = View.VISIBLE
                binding.layoutPending.visibility = View.VISIBLE
                binding.layoutOnWay.visibility = View.VISIBLE
                binding.layoutDelivered.visibility = View.GONE
                pendingStatus()
                preparingStatus()
                onWayStatus()
            }
            "delivered" ->{
                binding.layoutPerpearing.visibility = View.VISIBLE
                binding.layoutPending.visibility = View.VISIBLE
                binding.layoutOnWay.visibility = View.VISIBLE
                binding.layoutDelivered.visibility = View.VISIBLE
                pendingStatus()
                preparingStatus()
                onWayStatus()
                deliveredStatus()
            }
        }
        return binding.root
    }

    private fun pendingStatus(){
        binding.statusPendingTextVIew.text = getString(R.string.pending_confirmation)
        binding.circlePendingImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePending1ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePending2ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePending3ImageView.setImageResource(R.drawable.ic_circle_green)
    }

    private fun preparingStatus(){
        binding.statusPerparingTextVIew.text = getString(R.string.waiting_to_be_shipped)
        binding.circlePerparingImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePerparing1ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePerparing2ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circlePerparing3ImageView.setImageResource(R.drawable.ic_circle_green)
    }

    private fun onWayStatus(){
        binding.statusOnWayTextVIew.text = getString(R.string.on_way)
        binding.circleOnWayImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circleOnWay1ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circleOnWay2ImageView.setImageResource(R.drawable.ic_circle_green)
        binding.circleOnWay3ImageView.setImageResource(R.drawable.ic_circle_green)
    }

    private fun deliveredStatus(){
        binding.statusDeliveredTextVIew.text = getString(R.string.delivered)
        binding.circleDeliveredImageView.setImageResource(R.drawable.ic_circle_green)
        binding.successfullyDeliveredTextView.visibility = View.VISIBLE
    }
}