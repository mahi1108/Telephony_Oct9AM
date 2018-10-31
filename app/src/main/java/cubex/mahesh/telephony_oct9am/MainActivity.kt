package cubex.mahesh.telephony_oct9am

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    var uri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendSMS.setOnClickListener {

            var sIntent = Intent(this@MainActivity,
                                                        SendActivity::class.java)
            var spInetnt = PendingIntent.getActivity(
                    this@MainActivity,0,
                    sIntent,0)
            var dIntent = Intent(this@MainActivity,
                    DeliverActivity::class.java)
            var dpInetnt = PendingIntent.getActivity(
                    this@MainActivity,0,
                    dIntent,0)
            var sManager = SmsManager.getDefault()
            var list = et1.text.toString().split(",")
            for(num in list) {
                sManager.sendTextMessage(num,
                        null, et2.text.toString(),
                        spInetnt, dpInetnt)
            }

        }
        call.setOnClickListener {
            var  i = Intent( )
            i.setAction(Intent.ACTION_CALL)
            i.setData(Uri.parse("tel:"+et1.text.toString()))
            startActivity(i) 
        }


        attach.setOnClickListener {

            var aDialog = AlertDialog.Builder(this@MainActivity)
            aDialog.setTitle("Message")
            aDialog.setMessage("select option to add an attachment")
            aDialog.setPositiveButton("Camera",
                    object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            var i = Intent("android.media.action.IMAGE_CAPTURE")
                            startActivityForResult(i,123)
                        }
                    })
            aDialog.setNegativeButton("File Explorer",
                    object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            var i = Intent( )
                            i.setAction(Intent.ACTION_GET_CONTENT)
                            i.setType("*/*")
                            startActivityForResult(i,124)
             }
                    })
            aDialog.setNeutralButton("Cancel",
                    object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0!!.dismiss()
                        }
                    })
            aDialog.show()
        } // attach onclick listener


        sendMail.setOnClickListener {
             var i = Intent( )
             i.setAction(Intent.ACTION_SEND)
             i.putExtra(Intent.EXTRA_EMAIL, arrayOf(et3.text.toString()))
             i.putExtra(Intent.EXTRA_SUBJECT, et4.text.toString())
             i.putExtra(Intent.EXTRA_TEXT,et5.text.toString())
             i.putExtra(Intent.EXTRA_STREAM,uri)
             i.setType("message/rfc822")
             startActivity(i)
        }

        javaMail.setOnClickListener {
                var lop = LongOperation(et3.text.toString(),
                                        et4.text.toString(),
                                        et5.text.toString())
                lop.execute()
        }

    } // closing onCreate( )

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==123 && resultCode== Activity.RESULT_OK)
        {
                var  bmp:Bitmap = data!!.extras.get("data") as Bitmap
               uri = getImageUri(this@MainActivity,bmp)
        }
        if(requestCode==124 && resultCode== Activity.RESULT_OK)
        {
                    uri = data!!.data
        }
    } // onActivityResult

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }




} // MainActivity
