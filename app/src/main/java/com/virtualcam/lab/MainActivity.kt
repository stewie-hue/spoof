package com.virtualcam.lab

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var preview: ImageView
    private lateinit var status: TextView
    private var selectedUri: Uri? = null
    private val pickImage = 501

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(12, 16, 14)
        window.navigationBarColor = android.graphics.Color.rgb(12, 16, 14)
        val bg = android.graphics.Color.rgb(12, 16, 14)
        val panel = android.graphics.Color.rgb(24, 30, 26)
        val green = android.graphics.Color.rgb(90, 255, 130)
        val white = android.graphics.Color.WHITE

        val scroll = ScrollView(this)
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 28, 24, 28)
            setBackgroundColor(bg)
        }
        scroll.addView(page)

        fun label(s: String, size: Float, color: Int = white): TextView =
            TextView(this).apply {
                text = s
                textSize = size
                setTextColor(color)
                setPadding(0, 8, 0, 8)
            }

        fun button(s: String, action: () -> Unit): Button =
            Button(this).apply {
                text = s
                setOnClickListener { action() }
            }

        page.addView(label("VIRTUALCAM LAB", 26f, green))
        page.addView(label("Android camera testing • local preview only", 14f))
        page.addView(label("TEST FEED PREVIEW", 16f, green))

        preview = ImageView(this).apply {
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
            minimumHeight = 360
            setBackgroundColor(panel)
            setImageResource(android.R.drawable.ic_menu_camera)
            contentDescription = "Local test feed preview"
        }
        page.addView(preview, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 420
        ))

        page.addView(button("Choose test image", ::chooseImage))
        page.addView(button("Clear test image", ::clearImage))
        page.addView(button("Run local diagnostics", ::runDiagnostics))

        status = label(
            "Status: Ready\nSource: None\nMode: Local preview\n" +
            "This app does not replace the system camera or feed images to other apps.",
            14f
        ).apply {
            setPadding(14, 16, 14, 16)
            setBackgroundColor(panel)
        }
        page.addView(status)
        page.addView(label("SAFE TESTING NOTES", 16f, green))
        page.addView(label(
            "• Image selection uses Android's document picker.\n" +
            "• The selected image is displayed only inside this app.\n" +
            "• Use test images you own or have permission to use.\n" +
            "• This project does not hook third-party KYC cameras.",
            14f
        ))
        setContentView(scroll)
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, pickImage)
    }

    @Deprecated("Compatibility callback")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == RESULT_OK) {
            val uri = data?.data ?: return
            try {
                contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) { }
            selectedUri = uri
            preview.setImageURI(uri)
            status.text = "Status: Test image loaded\nSource: User-selected image\n" +
                "Mode: Local preview only\nNot available to other apps."
            Toast.makeText(this, "Loaded local test image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearImage() {
        selectedUri = null
        preview.setImageResource(android.R.drawable.ic_menu_camera)
        status.text = "Status: Ready\nSource: None\nMode: Local preview"
    }

    private fun runDiagnostics() {
        val state = if (selectedUri != null) "Selected image available" else "No image selected"
        status.text = "Diagnostics complete\nAndroid API: ${android.os.Build.VERSION.SDK_INT}\n" +
            "ABI: ${android.os.Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"}\n" +
            "Storage: Android document picker\n$state\nCamera injection: Not implemented"
    }
}
