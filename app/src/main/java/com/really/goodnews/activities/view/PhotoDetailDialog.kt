package com.really.goodnews.activities.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.really.goodnews.databinding.DialogImageSliderBinding
import com.really.goodnews.databinding.FragmentPhotoBinding
import com.really.goodnews.model.Photo
import java.text.FieldPosition

class PhotoDetailDialog(private val list: ArrayList<Photo>, private val position: Int): DialogFragment() {

    private lateinit var pagerAdapter: PhotoSliderPagerAdapter
    private var _binding : DialogImageSliderBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogImageSliderBinding.inflate(inflater, container, false)
        pagerAdapter = PhotoSliderPagerAdapter(this, list)
        binding.pager.adapter = pagerAdapter
        pagerAdapter.notifyDataSetChanged()
        binding.pager.setCurrentItem(position, false)
        return binding.root
    }

    private inner class PhotoSliderPagerAdapter(fa: Fragment, private val list: ArrayList<Photo>) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment {
            val item = list[position]
            return PhotoDetailFragment(item)
        }
    }
}