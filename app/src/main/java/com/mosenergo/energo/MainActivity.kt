package com.mosenergo.energo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.boardforfinders.dialoghelper.GoogleActConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mosenergo.energo.databinding.ActivityMainBinding
import com.mosenergo.energo.dialoghelper.DialogConst
import com.mosenergo.energo.dialoghelper.DialogHelper

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	private lateinit var tvAccount: TextView
	private lateinit var rootElement: ActivityMainBinding
	private val dialogHelper = DialogHelper(this)
	val mAuth = FirebaseAuth.getInstance()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		rootElement = ActivityMainBinding.inflate(layoutInflater)
		val view = rootElement.root
		setContentView(view)
		init()
	}

//	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//		if (requestCode == GoogleActConst.GOOGLE_SIGN_IN_REQUEST_CODE){
////			Log.d("My log", "Sign in result")
//			val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//			try {
//				val account = task.getResult(ApiException::class.java)
//				if (account != null){
//					Log.d("MyLog","Api 0")
//					dialogHelper.accHelper.signInfirebaseWithGoogle(account.idToken!!)
//				}
//			} catch (e: ApiException){
//				Log.d("MyLog","Api error: ${e.message}")
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data)
//	}

	override fun onStart() {
		super.onStart()
		uiUpdate(mAuth.currentUser)
	}

	private fun init(){
		val toggle = ActionBarDrawerToggle(
			this,
			rootElement.drawerLayout,
			rootElement.mainContent.toolbar,
			R.string.open,
			R.string.close
		)
		rootElement.drawerLayout.addDrawerListener(toggle)
		toggle.syncState()
		rootElement.navView.setNavigationItemSelectedListener(this)
		tvAccount = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		when(item.itemId){
			R.id.id_my_branch -> {
				Toast.makeText(this, "Pressed id_my_branch", Toast.LENGTH_SHORT).show()
			}
			R.id.id_pro -> {
				Toast.makeText(this, "Pressed id_pro", Toast.LENGTH_SHORT).show()
			}
			R.id.id_innovation -> {
				Toast.makeText(this, "Pressed id_innovation", Toast.LENGTH_SHORT).show()
			}
			R.id.id_sport -> {
				Toast.makeText(this, "Pressed id_sport", Toast.LENGTH_SHORT).show()
			}
			R.id.id_creation -> {
				Toast.makeText(this, "Pressed id_creation", Toast.LENGTH_SHORT).show()
			}
			R.id.id_s_dir -> {
				Toast.makeText(this, "Pressed id_s_dir", Toast.LENGTH_SHORT).show()
			}
			R.id.id_s_act -> {
				Toast.makeText(this, "Pressed id_s_act", Toast.LENGTH_SHORT).show()
			}
			R.id.id_sign_up -> {
				dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
			}
			R.id.id_sign_in -> {
				dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
			}
			R.id.id_out -> {
//				Toast.makeText(this, "Pressed id_out", Toast.LENGTH_SHORT).show()
				uiUpdate(null)
				mAuth.signOut()
			}
		}
		rootElement.drawerLayout.closeDrawer(GravityCompat.START)
		return true
	}

	fun uiUpdate(user: FirebaseUser?){
		tvAccount.text =  if(user == null){
			resources.getString((R.string.not_reg))
		} else {
			user.email
		}
	}
}