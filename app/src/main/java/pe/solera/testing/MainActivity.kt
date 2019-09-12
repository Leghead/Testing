package pe.solera.testing

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        const val REQUEST_CAMERA_PERMISSION = 200
        const val PHOTO_DIRECTORY_NAME = "/CSMPhotos"
        const val PACKAGE_NAME = "pe.solera.testing"
        const val JPG = ".jpg"

    }
    private lateinit var temporaryPath : String
    private var fileName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPhoto.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            btnPhoto -> {
                if (requestPermissions()){
                    takePhoto()
                }

            }
        }
    }

    private fun requestPermissions() : Boolean {
        return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
            false
        } else {
            true
        }
    }

    private fun takePhoto() {

        var file : File? = null

        try {
            file = createFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (file != null) {
            val pictureUri = FileProvider.getUriForFile(this, PACKAGE_NAME, file)
            startCameraActivity(pictureUri)
        }
    }

    private fun createFile() : File {

        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        fileName = StringBuilder(Calendar.getInstance().timeInMillis.toString()).append(JPG).toString()

        val photo = File("$storageDir$PHOTO_DIRECTORY_NAME", fileName)

        val pathToSave = File("$storageDir$PHOTO_DIRECTORY_NAME")

        if (!pathToSave.exists()) {
            pathToSave.mkdirs()
        }

        temporaryPath = photo.absolutePath

        return photo
    }

    private fun startCameraActivity(fileUri: Uri) {

        val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

        this.startActivityForResult(photoIntent, REQUEST_CAMERA_PERMISSION)
    }

    private fun setImageFromFolder() {

        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val savedPath = File("$storageDir$PHOTO_DIRECTORY_NAME")

        val photosFiles = savedPath.listFiles()

        if (photosFiles != null) {
            for (photo in photosFiles) {
                val photoFile = File("$storageDir$PHOTO_DIRECTORY_NAME", photo.name)
                if (photoFile.absolutePath == temporaryPath) {
                    val bitmap = BitmapFactory.decodeFile(photo.absolutePath)
                    if (bitmap != null) {
                        imageView.setImageBitmap(rotateIfNeeded(bitmap, Uri.fromFile(photoFile)))
                    }
                }
            }
        }
    }

    private fun rotateIfNeeded(bitmap: Bitmap, uri: Uri) : Bitmap {

        val input = this.contentResolver.openInputStream(uri)

        val exitIntf : ExifInterface

        exitIntf = if (Build.VERSION.SDK_INT > 23) ExifInterface(input) else ExifInterface(uri.path)

        val orientation = exitIntf.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when(orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(bitmap, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(bitmap, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(bitmap, 270)
            }
            else -> {
                bitmap
            }
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_PERMISSION && resultCode == Activity.RESULT_OK) {
            if (File(temporaryPath).exists()){
                setImageFromFolder()
            }
        }
    }
}
