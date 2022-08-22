package com.mosenergo.energo.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.mosenergo.energo.MainActivity
import com.mosenergo.energo.R
import com.mosenergo.energo.accounthelper.AccountHelper
import com.mosenergo.energo.databinding.SignDialogBinding
import com.mosenergo.energo.dialoghelper.DialogConst


class DialogHelper(act: MainActivity) {
	private val act = act
	val accHelper = AccountHelper(act)

	fun createSignDialog(index: Int) {
		val builder = AlertDialog.Builder(act)
		val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
		val view = rootDialogElement.root
		builder.setView(view)
		setDialogState(index, rootDialogElement)
		val dialog = builder.create() // созадаем диалог
		rootDialogElement.btSignUpIn.setOnClickListener {
			setOnClickSignUpIn(index, rootDialogElement, dialog)
		}
		rootDialogElement.btForgetP.setOnClickListener {
			setOnClickResetPassword(rootDialogElement, dialog)
		}
//		rootDialogElement.btGoogleSignIn.setOnClickListener {
//			accHelper.signInWithGoogle()
//			dialog.dismiss()
//		}
		dialog.show()
	}

	private fun setOnClickResetPassword(rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
		if(rootDialogElement.edSignEmail.text.isNotEmpty()){
			act.mAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString()).addOnCompleteListener{ task->
				if(task.isSuccessful){
					Toast.makeText(act, R.string.email_reset_password_was_sent, Toast.LENGTH_LONG).show()
				}
			}
			dialog?.dismiss()
		} else {
			rootDialogElement.tvDialogMessagetextView.visibility = View.VISIBLE
		}
	}

	private fun setOnClickSignUpIn(index: Int, rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
		dialog?.dismiss() //закрываем диалог
		if (index == DialogConst.SIGN_UP_STATE) {
			accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString(),
				rootDialogElement.edSignPassword.text.toString())
		} else if (index == DialogConst.SIGN_IN_STATE){
			accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString(),
				rootDialogElement.edSignPassword.text.toString())
			//просто else не работает
		}
	}

	private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
		if (index == DialogConst.SIGN_UP_STATE) {
			rootDialogElement.tvSignTitile.text = act.resources.getString(R.string.ac_sign_up)
			rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
		} else {

			rootDialogElement.tvSignTitile.text = act.resources.getString(R.string.ac_sign_in)
			rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
			rootDialogElement.btForgetP.visibility = View.VISIBLE
		}
	}
}