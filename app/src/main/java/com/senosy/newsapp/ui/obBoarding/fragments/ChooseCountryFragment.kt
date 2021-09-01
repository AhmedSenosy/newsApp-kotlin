package com.senosy.newsapp.ui.obBoarding.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import com.senosy.newsapp.R
import com.senosy.newsapp.databinding.FragmentChooseCountryBinding
import com.senosy.newsapp.utils.FAVOURITE_COUNTRY
import com.senosy.newsapp.utils.PreferenceHelper
import com.senosy.newsapp.utils.PreferenceHelper.set


class ChooseCountryFragment : Fragment() {
    lateinit var binding : FragmentChooseCountryBinding
    lateinit var countryPicker:CountryPickerView
    var country:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCountryBinding.inflate(inflater,container,false)
        countryPicker = binding.countryPicker
        setupCountryPickerView()
        toggleEnableNextButton()
        attachListeners()
        return binding.root
    }

    private fun attachListeners() {
        countryPicker.cpViewHelper.selectedCountry.observe(requireActivity(), Observer {
            country = it?.alpha2 ?: ""
            PreferenceHelper.getPrefs(requireContext())[FAVOURITE_COUNTRY] = country
            toggleEnableNextButton()
        })

        binding.btnNext.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)
            viewPager?.currentItem = 1
        }
    }

    private fun setupCountryPickerView() {
        countryPicker.cpViewHelper.cpViewConfig.viewTextGenerator = { cpCountry: CPCountry ->
            "${cpCountry.name} (${cpCountry.alpha2})"
        }
        // make sure to refresh view once view configuration is changed
        countryPicker.cpViewHelper.refreshView()
    }

    private fun toggleEnableNextButton(){
        when(country.isEmpty()){
            true -> binding.btnNext.isEnabled = false
            else -> binding.btnNext.isEnabled = true
        }
    }
}