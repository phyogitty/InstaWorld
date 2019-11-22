package com.pk.instaworld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class AddPostActivity extends AppCompatActivity {


    ImageView ivPostToBe;
    Button btnTakePictureAgain;

    Button btnEditPhoto;
    TextView etDescription;
    Bitmap image;
    Button btnSharePost;
    Button btnCancelPost;

    public static final String TAG = "AddPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Log.i(TAG, "Inside AddPostActivity");
        ivPostToBe = findViewById(R.id.ivPostToBe);
        btnTakePictureAgain = findViewById(R.id.btnTakePictureAgain);
        btnSharePost = findViewById(R.id.btnSharePost);
        btnCancelPost = findViewById(R.id.btnCancelPost);
        btnEditPhoto = findViewById(R.id.btnEditPhoto);
        etDescription = findViewById(R.id.etDescription);



        image = getIntent().getParcelableExtra("picture");
        ivPostToBe.setImageBitmap(image);

        btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AddPostActivity.this, MainActivity.class);
                setResult(RESULT_OK, i);

            }
        });

        btnSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save post and description
                String description = etDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                savePost(description, user, (File) getIntent().getSerializableExtra("pictureFile"));

//                setResult(RESULT_OK, i);
//                finish();
                // go to MainActivity
            }
        });

        btnTakePictureAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to camera again
                Toast.makeText(AddPostActivity.this, "Add More Not Yet Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddPostActivity.this, "Edit Button Not Yet Implemented", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void savePost(String description, ParseUser parseUser, File pictureFile) {
        final Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
        post.setImage(new ParseFile(pictureFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Error while saving post" + e);
                    e.printStackTrace();
                    return;
                }
                Intent i = new Intent();
                i.putExtra("imageId", post.getObjectId());
                i.putExtra("image", image);

                Log.d(TAG, "Successfully Saved Post! " + post.getObjectId());
                setResult(Activity.RESULT_OK, i);
                finish();

            }


        });
//        while (post.getImageId() == null) {
//            Log.i(TAG, "TESTING: " + post.getImageId());
//        }

    }
}
