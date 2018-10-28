package cubex.mahesh.telephony_oct9am

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }
}
