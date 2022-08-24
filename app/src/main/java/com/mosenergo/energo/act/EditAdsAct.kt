package com.mosenergo.energo.act

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.mosenergo.energo.R
import com.mosenergo.energo.databinding.ActivityEditAdsBinding
import com.mosenergo.energo.dialogs.DialogSpinnerHelper
import com.mosenergo.energo.utils.ImagePicker
import com.mosenergo.energo.utils.TecHelper


class EditAdsAct: AppCompatActivity() {
	lateinit var rootElement: ActivityEditAdsBinding
	private var dialog = DialogSpinnerHelper()
	private var isImagesPermissionGranted = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
		//создали экран
		setContentView(rootElement.root)
		init()
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		when (requestCode) {
			PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

				// If request is cancelled, the result arrays are empty.
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					isImagesPermissionGranted = true
				} else {
					isImagesPermissionGranted = false
					Toast.makeText(
						this,
						"Approve permissions to open Pix ImagePicker",
						Toast.LENGTH_LONG
					).show()
				}
				return
			}
		}
	}


	fun init(){

	}

		// OnClicks
	fun onClickSelectCompany(view: View){
			val listCompany = TecHelper.getAllCompanies(this)
			dialog.showSpinnerDialog(this, listCompany, rootElement.tvCompany)
			if(rootElement.tvTEC.text.toString() != getString(R.string.select_tec))
			{
				rootElement.tvTEC.text = getString(R.string.select_tec)
			}
	}

	fun onClickGetImages(view: View){
		ImagePicker.getImages(this)
	}

	fun onClickSelectTEC(view: View){
		val selectedCompany = rootElement.tvCompany.text.toString()
		if (selectedCompany != getString(R.string.select_company)) {
			val listTEC = TecHelper.getAllFilial(selectedCompany, this)
			dialog.showSpinnerDialog(this, listTEC, rootElement.tvTEC)
		} else {
			Toast.makeText(this, R.string.select_company_above, Toast.LENGTH_LONG).show()
					}
	}


//		// заздаем адаптер и подключаем к спиннеру
//		val adapter = ArrayAdapter(this,
//			android.R.layout.simple_spinner_item, TecHelper.getAllTEC(this))
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//		// прикрепляем адаптер к спиннеру
//		rootElement.spTEC.adapter = adapter

}