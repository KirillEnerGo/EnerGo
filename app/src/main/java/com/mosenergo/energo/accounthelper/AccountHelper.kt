package com.mosenergo.energo.accounthelper

import android.util.Log
import android.widget.Toast
import com.example.boardforfinders.dialoghelper.GoogleActConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.mosenergo.energo.MainActivity
import com.mosenergo.energo.R
import com.mosenergo.energo.constants.FirebaseAuthConstants
import com.mosenergo.energo.constants.FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE

class AccountHelper(act: MainActivity) {
	private val act = act
	private lateinit var signInClient: GoogleSignInClient

	fun signUpWithEmail(email: String, password: String){
		if(email.isNotEmpty() && password.isNotEmpty()){
			act.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
				if (task.isSuccessful){
					sendEmailVerification(task.result?.user!!)
					act.uiUpdate(task.result?.user)
				} else {
//					Toast.makeText(act, act.getString(R.string.sign_up_error), Toast.LENGTH_LONG).show()
//					Log.d("MyLog", "Exception : ${exception.errorCode}")
					Log.d("MyLog", "Exception : " + task.exception)
					if (task.exception is FirebaseAuthUserCollisionException){
						val exception = task.exception as FirebaseAuthUserCollisionException
						if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE){
							Toast.makeText(act, FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_LONG).show()
						}
					} else if (task.exception is FirebaseAuthInvalidCredentialsException) {
						val exception = task.exception as FirebaseAuthInvalidCredentialsException
						Log.d("MyLog", "Exception : " + exception.errorCode)
						if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL){
							Toast.makeText(act, FirebaseAuthConstants.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show()
//							Log.d("MyLog", "Exception : ${exception.errorCode}")
						}
					}
					if (task.exception is FirebaseAuthWeakPasswordException) {
//						Log.d("MyLog", "Exception : " + task.exception)
						val exception = task.exception as FirebaseAuthWeakPasswordException
						if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD){
							Toast.makeText(act, FirebaseAuthConstants.ERROR_WEAK_PASSWORD, Toast.LENGTH_SHORT).show()
							Toast.makeText(act, "Для создания пароля используйте больше 6 символов", Toast.LENGTH_LONG).show()
//							Log.d("MyLog", "Exception : ${exception.errorCode}")
						}
					}
				}
			}
		}
	}

	fun signInWithEmail(email: String, password: String){
		if(email.isNotEmpty() && password.isNotEmpty()){
			act.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task->
				if (task.isSuccessful){
					act.uiUpdate(task.result?.user)
				} else {

					if (task.exception is FirebaseAuthInvalidCredentialsException) {
						Log.d("MyLog", "Exception :  ${task.exception}") //отслеживать общие ошибки
						val exception = task.exception as FirebaseAuthInvalidCredentialsException
						Log.d("MyLog", "Exception :  ${exception.errorCode}") // отслеживать конкретную ошибку error code
						if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL){
							Toast.makeText(act, FirebaseAuthConstants.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show()
						} else if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD){
							Toast.makeText(act, FirebaseAuthConstants.ERROR_WRONG_PASSWORD, Toast.LENGTH_LONG).show()
						}
					}
//					Toast.makeText(act, act.getString(R.string.sign_in_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}
// google авторизация
//	private fun getSingInClient(): GoogleSignInClient {
//		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//			.requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
//		return GoogleSignIn.getClient(act, gso)
//	}
//
//	fun signInWithGoogle(){
//		signInClient = getSingInClient()
//		val intent = signInClient.signInIntent
//		act.startActivityForResult(intent, GoogleActConst.GOOGLE_SIGN_IN_REQUEST_CODE)
//	}

//	fun signInFirebaseWithGoogle(token: String){
//		val credential = GoogleAuthProvider.getCredential(token, null)
//		act.mAuth.signInWithCredential(credential).addOnCompleteListener { task->
//			if (task.isSuccessful){
//				Toast.makeText(act, "Sign in done", Toast.LENGTH_LONG).show()
//				act.uiUpdate(task.result?.user)
//			}
//		}
//	}

	private fun sendEmailVerification(user: FirebaseUser){
		user.sendEmailVerification().addOnCompleteListener { task->
			if (task.isSuccessful){
				Toast.makeText(act, act.getString(R.string.send_verification_done), Toast.LENGTH_LONG).show()
			} else {
				Toast.makeText(act, act.getString(R.string.send_verification_error), Toast.LENGTH_LONG).show()
			}
		}
	}
}