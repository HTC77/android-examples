package com.example.androidexamples

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast

class IntentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intents)
    }

    fun onClickWebBrowser(v: View) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com"))
        startActivity(i)
    }

    fun onClickMakeCalls(v: View) {
        val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+651234567"))
        startActivity(i)
    }

    fun onClickShowMap(v: View) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.827500,-122.481670"))
        startActivity(i)
    }

    fun onContactPick(v: View) {
        val i = Intent(Intent.ACTION_PICK)
        i.type = ContactsContract.Contacts.CONTENT_TYPE
        startActivity(i)
    }

    fun onClickLaunchMyBrowser(v: View) {
        val i = Intent(this, MyBrowserActivity::class.java)
        i.data = Uri.parse("http://www.amazon.com")
        startActivity(i)
    }

    fun onDirectCall(v: View) {
        val i = Intent(Intent.ACTION_CALL)
        i.data = Uri.parse("tel:+651234567")
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        startActivity(i)
        Toast.makeText(baseContext, "Hello Direct Call", Toast.LENGTH_SHORT).show()
    }

    fun onLaunchBrowser(v: View) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("http://www.amazon.com")
        startActivity(Intent.createChooser(i, "Open URL Using..."))
    }
}
