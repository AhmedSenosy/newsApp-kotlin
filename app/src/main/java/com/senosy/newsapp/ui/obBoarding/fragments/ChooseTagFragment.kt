package com.senosy.newsapp.ui.obBoarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.senosy.newsapp.R
import com.senosy.newsapp.databinding.FragmentChooseTagBinding
import com.senosy.newsapp.ui.MainActivity
import com.senosy.newsapp.utils.FAVOURITE_TAGS
import com.senosy.newsapp.utils.PREF_FIRST_TIME
import com.senosy.newsapp.utils.PreferenceHelper
import com.senosy.newsapp.utils.PreferenceHelper.set


class ChooseTagFragment : Fragment() {
    private lateinit var binding :FragmentChooseTagBinding
    private var savedTags:MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTagBinding.inflate(inflater)
        attachListener()
        return binding.root
    }

    private fun attachListener(){
        for( chip in binding.chipGrpTags.children)
        {
            (chip as Chip).setOnCheckedChangeListener(changeListener)
        }
        binding.btnSave.setOnClickListener {
            saveTags()
        }
    }
    private val changeListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                if(savedTags.size ==3)
                {
                    buttonView.isChecked = false
                    Toast.makeText(requireContext(),getString(R.string.tag_limit),Toast.LENGTH_LONG)
                }
                else {
                    addToSavedList(buttonView.text.toString())
                }
            }
            else
            {
                removeFromSavedList(buttonView.text.toString())
            }
        }
    private fun addToSavedList(tag:String){
        savedTags.add(tag)
    }

    private fun removeFromSavedList(tag:String)
    {
        savedTags.remove(tag)
    }

    private fun saveTags(){
        val tags = Gson().toJson(savedTags)
        PreferenceHelper.getPrefs(requireContext())[FAVOURITE_TAGS] = tags
        PreferenceHelper.getPrefs(requireContext())[PREF_FIRST_TIME] = true
        requireActivity().finish()
        startActivity(Intent(requireActivity(),MainActivity::class.java))
    }
}