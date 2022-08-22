package com.mosenergo.energo.utils

import android.content.Context
import com.mosenergo.energo.R
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

object TecHelper {
	fun getAllCompanies(context: Context): ArrayList<String> {
		var tempArray = ArrayList<String>()
		try {
			// создаем поток байтов
			val inputStream: InputStream = context.assets.open("TEC_info.json")
			// узнаем обьем байт в файле
			val size: Int = inputStream.available()
			// создаем массив и помещаем считанные байты
			val bytesArray = ByteArray(size)
			// считываем и записываем в массив
			inputStream.read(bytesArray)
			// превращаем обьект в Srting
			val jsonFile = String(bytesArray)
			// превращаем в json обьект
			val jsonObject = JSONObject(jsonFile)
			// получем все имена из обьекта (массивов не будет)
			val childCompaniesNames = jsonObject.names()
			if (childCompaniesNames != null) {
				for (n in 0 until childCompaniesNames.length()) {
					// добавляем в массив
					tempArray.add(childCompaniesNames.getString(n))
				}
			}

		} catch (e: IOException) {

		}
		return tempArray
	}

	fun getAllFilial(company: String, context: Context): ArrayList<String> {
		var tempArray = ArrayList<String>()
		try {
			// создаем поток байтов
			val inputStream: InputStream = context.assets.open("TEC_info.json")
			// узнаем обьем байт в файле
			val size: Int = inputStream.available()
			// создаем массив и помещаем считанные байты
			val bytesArray = ByteArray(size)
			// считываем и записываем в массив
			inputStream.read(bytesArray)
			// превращаем обьект в Srting
			val jsonFile = String(bytesArray)
			// превращаем в json обьект
			val jsonObject = JSONObject(jsonFile)
			// получем все имена из обьекта (массивов не будет)
			val filialNames = jsonObject.getJSONArray(company)
			for (n in 0 until filialNames.length()) {
				// добавляем в массив
				tempArray.add(filialNames.getString(n))
			}
		} catch (e: IOException) {
		}
		return tempArray
	}

	fun filterListData(list: ArrayList<String>, searchText: String?): ArrayList<String> {
		val tempList = ArrayList<String>()
		tempList.clear()
		if (searchText == null) {
			tempList.add("нет результата")
			return tempList
		}
		for (selection: String in list) {
			if (selection.toLowerCase(Locale.ROOT)
					.startsWith(searchText.toLowerCase(Locale.ROOT))
			) {
				tempList.add(selection)
			}
		}
		if (tempList.size == 0) tempList.add("нет результата")
		return tempList
	}
}