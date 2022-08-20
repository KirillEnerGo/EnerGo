package com.mosenergo.energo.act

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.mosenergo.energo.databinding.ActivityEditAdsBinding
import com.mosenergo.energo.utils.CityHelper

class EditAdsAct: AppCompatActivity() {
	private lateinit var rootElement: ActivityEditAdsBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
		setContentView(rootElement.root)

		val adapter = ArrayAdapter(this,
			R.layout.simple_spinner_item, CityHelper.getAllCountries(this))
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		rootElement.spCountry.adapter = adapter
	}
}