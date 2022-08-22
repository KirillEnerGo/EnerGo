package com.mosenergo.energo.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosenergo.energo.R
import com.mosenergo.energo.act.EditAdsAct
import com.mosenergo.energo.dialogs.RcViewDialogSpinnerAdapter.*

class RcViewDialogSpinnerAdapter(var context: Context, var dialog: AlertDialog): RecyclerView.Adapter<SpViewHolder>() {
	private val mainList = ArrayList<String>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
		return SpViewHolder(view, context, dialog)
	}

	override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
		holder.setData(mainList[position])
	}

	override fun getItemCount(): Int {
		return mainList.size
	}
	class SpViewHolder(itemView: View, var context: Context, var dialog: AlertDialog) : RecyclerView.ViewHolder(itemView), OnClickListener {
		private var itemText = ""
		fun setData(text: String){
			val tvSpItem = itemView.findViewById<TextView>(R.id.tvSpItem)
			tvSpItem.text = text
			itemText = text
			itemView.setOnClickListener(this)
		}
		// выбор компании из списка и присваивание
		override fun onClick(v: View?) {
			(context as EditAdsAct).rootElement.tvCompany.text = itemText
			dialog.dismiss()
		}
	}
	fun updateAdapter(list: ArrayList<String>){
		mainList.clear()
		mainList.addAll(list)
		// сообщить адапетру что данные изменились
		notifyDataSetChanged()
	}
}