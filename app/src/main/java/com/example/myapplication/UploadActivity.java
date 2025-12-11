package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button btnSelectVideo, btnUploadVideo;
    private ProgressBar progressBar;
    private Uri videoUri;
    private StorageReference storageRef;
    private FirebaseAuth mAuth;

    private final ActivityResultLauncher<Intent> videoPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    videoUri = result.getData().getData();
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                    btnUploadVideo.setEnabled(true);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mAuth = FirebaseAuth.getInstance();
        
        // THAY ĐỔI QUAN TRỌNG: Thử dùng đuôi .appspot.com thay vì .firebasestorage.app
        // Nếu vẫn lỗi, bạn cần vào Console copy chính xác dòng gs://...
        try {
            storageRef = FirebaseStorage.getInstance("gs://phongtran74737.appspot.com").getReference("videos");
        } catch (Exception e) {
            // Fallback nếu url sai format, nhưng thường thì appspot.com là chuẩn
            storageRef = FirebaseStorage.getInstance().getReference("videos");
        }

        videoView = findViewById(R.id.videoView);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnUploadVideo = findViewById(R.id.btnUploadVideo);
        progressBar = findViewById(R.id.progressBar);

        btnUploadVideo.setEnabled(false);

        btnSelectVideo.setOnClickListener(v -> selectVideo());
        btnUploadVideo.setOnClickListener(v -> uploadVideo());
    }

    private void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        videoPickerLauncher.launch(Intent.createChooser(intent, "Select Video"));
    }

    private void uploadVideo() {
        if (videoUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            btnUploadVideo.setEnabled(false);
            btnSelectVideo.setEnabled(false);

            String uid = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "anonymous";
            StorageReference fileReference = storageRef.child(uid + "/" + System.currentTimeMillis() + ".mp4");

            fileReference.putFile(videoUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        btnUploadVideo.setEnabled(true);
                        btnSelectVideo.setEnabled(true);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        // Hiển thị lỗi chi tiết hơn để dễ debug
                        Toast.makeText(UploadActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        btnUploadVideo.setEnabled(true);
                        btnSelectVideo.setEnabled(true);
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        // Optional: update progress
                    });
        } else {
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show();
        }
    }
}