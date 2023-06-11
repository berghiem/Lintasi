package com.catostudioaruna.alphabet;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import com.catostudioaruna.alphabet.view.HomeScreenActivity;
import com.catostudioaruna.alphabet.view.HomeScreenFragment;
import com.catostudioaruna.alphabet.view.MainActivity;

import java.util.Random;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    private String[] quotes;
    public NotificationHelper(Context base) {
        super(base);
        quotes= new String[]{
                "Tujuan pendidikan itu untuk mempertajam kecerdasan, memperkukuh kemauan serta memperhalus perasaan - Tan Malaka",
                "Sekolah-sekolah saja tidak dapat memajukan masyarakat, tetapi juga keluarga di rumah harus turut bekerja. Lebih-lebih dari rumahlah kekuatan mendidik itu harus berasal - R.A Kartini",
                "Gantungkan cita-cita mu setinggi langit! Bermimpilah setinggi langit. Jika engkau jatuh, engkau akan jatuh di antara bintang-bintang - Soekarno",
                "Jadikan setiap tempat sebagai sekolah, jadikan setiap orang sebagai guru - Ki Hajar Dewantara",
                "Kecerdasan ditambah dengan karakter merupakan tujuan dari pendidikan sejati. - Martin Luther King",
                "Mimpi terbesarku adalah untuk melihat ke belakang dan bilang aku berhasil",
                "Kalau impianmu tak bisa membuatmu takut, mungkin karena impianmu tak cukup besar.-Muhammad Ali",
                "Ketika kamu merasa ingin berhenti, pikirkan tentang mengapa kamu memulainya",
                "Belajarlah seperti air, karena air tidak akan pernah habis, seperti ilmu, tidak akan pernah habis untuk kita mempelajarinya",
                "Kamu tidak harus hebat untuk memulai, tapi kamu harus memulai untuk menjadi hebat",
                "Sebagian orang bermimpi untuk sukses, sedangkan sebagian lainnya bangun dari mimpi untuk mewujudkannya",
                "Pengetahuan adalah senjata yang paling hebat untuk mengubah dunia.-Nelson Mandela",
                "Aku tidak punya aturan. Aku hanya berusaha melakukan yang terbaik, setiap saat dan setiap hari. - Abraham Lincoln",
                "Waktu tidak berpihak pada siapapun. Tapi waktu dapat menjadi sahabat bagi mereka yang memegang dan memperlakukannya Dengan Baik. -Winston Churchill)",
                "Saya tidak pernah gagal. Saya hanya menemukan 10.000 cara yang tidak tepat.- Thomas A. Edison",
                "Semakin tinggi mimpi yang kamu punya, semakin besar pencapaian yang akan kamu dapat.-Michael Phelps",
                "Cara terbaik untuk mempelajari sesuatu adalah dengan melakukannya.-Richard Branson"
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String text = quotes[new Random().nextInt(quotes.length)];
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Motivasi hari ini")
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setSmallIcon(R.drawable.notificon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

}

