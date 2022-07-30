package com.example.videodemo.util
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.videodemo.MainActivity
import com.example.videodemo.R
import com.example.videodemo.util.Constants.Companion.NOTIFICATION_ID
import com.google.android.exoplayer2.util.NotificationUtil

private val Request_Code = 12
private val Channel_Id = "Gopal"

fun com.google.android.material.imageview.ShapeableImageView.loadImage(imageUrl: String){
        Glide.with(this)
            .load(imageUrl)
            .into(this)
}

fun View.setVisibility(isVisible : Boolean){
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun NotificationManager.sendNotifications(
messageBody: String,
context: Context
){

    // after clicking the notification navigates to which activity
    val content = Intent(context,MainActivity::class.java)

    // pending intent will execute the content when user taps on notification
    val contentPendingIntent = PendingIntent.getActivity(
        context,
        Request_Code,
        content,
        PendingIntent.FLAG_IMMUTABLE and PendingIntent.FLAG_UPDATE_CURRENT
    )


 NotificationCompat.Builder(
        context,
        Channel_Id
    )
     .setSmallIcon(R.drawable.ic_launcher_foreground)
     .setContentTitle("Video Playing")
     .setContentText(messageBody)
     .setContentIntent(contentPendingIntent)
     .setPriority(NotificationCompat.PRIORITY_HIGH)
     .run {
         notify(NOTIFICATION_ID,this.build())
     }





}


