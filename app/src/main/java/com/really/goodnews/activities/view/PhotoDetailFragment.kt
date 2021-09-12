package com.really.goodnews.activities.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.really.goodnews.databinding.FragmentPhotoBinding
import com.really.goodnews.model.Photo

class PhotoDetailFragment(private val photo: Photo): Fragment() {

    private var _binding :FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val url = GlideUrl(
            photo.url, LazyHeaders.Builder()
                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(this.context))
                .build()
        )
        Glide.with(this).load(url).into(binding.imageView)
        return binding.root
    }
}