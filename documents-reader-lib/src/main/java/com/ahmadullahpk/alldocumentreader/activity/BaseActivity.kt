package com.ahmadullahpk.alldocumentreader.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmadullahpk.alldocumentreader.R
import com.ahmadullahpk.alldocumentreader.manageui.CustomFrameLayout
import com.ahmadullahpk.alldocumentreader.util.Utility
import com.ahmadullahpk.alldocumentreader.util.ViewUtils

open class BaseActivity : AppCompatActivity() {
    public var PERMISSIONS_LIST = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE ,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val REQUEST_CODE = 112
    var starterActivity: Intent? = null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE) {
            return
        }
        if (grantResults.isNotEmpty()) {
            val n2 = grantResults[0]
            val bl = n2 == 0
            if (bl) {
                if (starterActivity != null) {
                   // this.startActivity(Intent(this, Main_Home_Activity::class.java))
                    finish()
                    return
                }
                Utility.Toast(this, resources.getString(R.string.permissionGranted))
                return
            }
            Utility.Toast(this, resources.getString(R.string.permission_denied_message2))
        }
    }

    protected fun hasReadStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 30) {
            return Environment.isExternalStorageManager()
        }
        if (Build.VERSION.SDK_INT >= 29) {
            val n = ActivityCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return n == 0
        }
        if (Build.VERSION.SDK_INT >= 23) {
            val n = ActivityCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            return n == 0
        }
        return true
    }

    protected fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = 3846
        decorView.setOnSystemUiVisibilityChangeListener { i: Int ->
            showAndHide(
                i and 4 == 0
            )
        }
    }

    private fun showAndHide(z: Boolean) {
        val linearLayout = findViewById<CustomFrameLayout>(R.id.appToolbar) ?: return
        if (z) {
            linearLayout.visibility = View.VISIBLE
        } else {
            linearLayout.visibility = View.GONE
        }
    }

    fun checkAndLunchActivity(intent: Intent?) {
        starterActivity = intent
        when {
            hasReadStoragePermission() -> {
                startActivity(intent)
                finish()
            }
            Build.VERSION.SDK_INT >= 30 -> {
                try {
                    val intent2 = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent2.addCategory(Intent.CATEGORY_DEFAULT)
                    intent2.data =
                        Uri.parse(String.format("package:%s", applicationContext.packageName))
                    someActivityResultLauncher.launch(intent2)
                } catch (unused: Exception) {
                    val intent3 = Intent()
                    intent3.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    someActivityResultLauncher.launch(intent3)
                }
            }
            else -> {
                ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, 112)
            }
        }
    }

    var someActivityResultLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult: ActivityResult ->
            if (activityResult.resultCode == -1) {
                startActivity(starterActivity)
                finish()
            }
        }

    open fun setStatusBar() {
        isTransparentEnabled(true)
    }

    open fun isTransparentEnabled(z: Boolean) {
        setTransparentForWindow(z, false)
    }

    open fun setTransparentForWindow(z: Boolean, z2: Boolean) {
        val window = window
        if (Build.VERSION.SDK_INT >= 23) {
            window.decorView.systemUiVisibility = ViewUtils.setWidth(z, z2)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        } else if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
    }

    open fun adaptFitsSystemWindows(view: View?) {
        if (view != null) {
            view.fitsSystemWindows = false
            if (view is ViewGroup) {
                val childCount = view.childCount
                for (i in 0 until childCount) {
                    view.getChildAt(i).fitsSystemWindows = false
                }
            }
        }
    }
}