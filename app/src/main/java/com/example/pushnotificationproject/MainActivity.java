package com.example.pushnotificationproject;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.StrictMode;
    import android.util.Log;
    import android.view.View;
    import android.widget.EditText;

    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.io.OutputStreamWriter;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.messaging.FirebaseMessaging;

    public class MainActivity<button> extends AppCompatActivity {
        private static final String TAG = "pushNotification";
        private static final String CHANNEL_ID = "101";
//        private static final String TAG = "date";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            createNotificationChannel();
            getToken();

        }
        public void userSave(View view){
            // startActivity(new Intent(this,Register.class));
            System.out.println("Sending to server...");
            sendToServer();
        }

        public void sendToServer() {

            EditText name = (EditText) findViewById(R.id.inputName);
            EditText token = (EditText) findViewById(R.id.txtToken);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String reg_url = "http://tourbazi.ir/tourbazi_notif";
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "="+URLEncoder.encode(name.getText().toString(),"UTF-8")+"&"+
                        URLEncoder.encode("token", "UTF-8") + "="+URLEncoder.encode(token.getText().toString(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream Is = httpURLConnection.getInputStream();
                Is.close();

                System.out.println("OK");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://localhost/notification/notify.php?name=" + name.getText().toString() + "&token=" + token.getText().toString())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println(response.body().string());
                }

            });*/
        }

        private void getToken() {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    //if task is failed then
                    if(!task.isSuccessful()){
                        Log.d(TAG, "onComplete: failed to get token");
                    }
                    //token
                    String token=task.getResult();
                    EditText txtToken =(EditText)findViewById(R.id.txtToken);
                    txtToken.setText(token);
                    Log.d(TAG, "onComplete: " + token);
                }


            });
        }
        private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "firebaseNotificationChannel";
                String description = "Receive firebase notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }


    }

