package com.mosenergo.energo.act

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.mosenergo.energo.R
import com.mosenergo.energo.databinding.ActivityEditAdsBinding
import com.mosenergo.energo.dialogs.DialogSpinnerHelper
import com.mosenergo.energo.utils.TecHelper

class EditAdsAct: AppCompatActivity() {
	lateinit var rootElement: ActivityEditAdsBinding
	private var dialog = DialogSpinnerHelper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
		//создали экран
		setContentView(rootElement.root)
		init()
	}

	fun init(){

	}

		// OnClicks
	fun onClickSelectCompany(view: View){
			val listTEC = TecHelper.getAllTEC(this)
			dialog.showSpinnerDialog(this, listTEC)
	}


//		// заздаем адаптер и подключаем к спиннеру
//		val adapter = ArrayAdapter(this,
//			android.R.layout.simple_spinner_item, TecHelper.getAllTEC(this))
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//		// прикрепляем адаптер к спиннеру
//		rootElement.spTEC.adapter = adapter

}