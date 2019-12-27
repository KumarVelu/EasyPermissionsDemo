package com.velu.easypermissiondemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val RC_CAMERA_PERM = 123
    private val RC_LOCATION_PERM = 124
    private val RC_SMS_PERM = 125
    private val RC_CONTACTS_PERM = 126

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCamera = findViewById<Button>(R.id.btn_camera)
        val btnLocation = findViewById<Button>(R.id.btn_location)
        val btnSms = findViewById<Button>(R.id.btn_sms)
        val btnContact = findViewById<Button>(R.id.btn_contact)

        btnCamera.setOnClickListener {
            cameraTask()
        }

        btnLocation.setOnClickListener {
                        locationTask()
        }

        btnSms.setOnClickListener {
                        smsTask()
        }

        btnContact.setOnClickListener {
                        contactTask()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    private fun hasLocationPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun hasSmsPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_SMS)
    }

    private fun hasContactPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)
    }

    private fun cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_camera),
                RC_CAMERA_PERM,
                Manifest.permission.CAMERA
            )
        }
    }

    fun locationTask() {
        if (hasLocationPermissions()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Location things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    fun smsTask() {
        if (hasSmsPermission()) {
            Toast.makeText(this, "TODO: SMS things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_sms),
                RC_SMS_PERM,
                Manifest.permission.READ_SMS
            )
        }
    }

    fun contactTask() {
        if (hasContactPermission()) {
            Toast.makeText(this, "TODO: Contact things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_contact),
                RC_CONTACTS_PERM,
                Manifest.permission.READ_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                this,
                getString(R.string.returned_from_app_settings_to_activity,
                    if (hasCameraPermission()) yes else no,
                    if (hasLocationPermissions()) yes else no,
                    if (hasSmsPermission()) yes else no,
                    if (hasContactPermission()) yes else no),
                Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:$requestCode")
    }

}
