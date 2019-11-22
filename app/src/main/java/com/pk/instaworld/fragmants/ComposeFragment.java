package com.pk.instaworld.fragmants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.pk.instaworld.MainActivity;
import com.pk.instaworld.Post;
import com.pk.instaworld.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends Fragment {
    public static final String TAG = "ComposeFragment";
    ImageView ivPostToBe;
    Button btnTakePictureAgain;
    Button btnSharePost;
    Button btnCancelPost;
    Button btnEditPhoto;
    TextView etDescription;
    Bitmap image;


    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Inside onCreateView");
        launchCamera();
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Inside onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        ivPostToBe = view.findViewById(R.id.ivPostToBe);
        btnTakePictureAgain = view.findViewById(R.id.btnTakePictureAgain);
        btnSharePost = view.findViewById(R.id.btnSharePost);
        btnCancelPost = view.findViewById(R.id.btnCancelPost);
        btnEditPhoto = view.findViewById(R.id.btnEditPhoto);
        etDescription = view.findViewById(R.id.etDescription);

        btnSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save post and description
                String description = etDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                savePost(description, user, photoFile);

//                setResult(RESULT_OK, i);
//                finish();
                // go to MainActivity
            }
        });

        ivPostToBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDescription.setText("");
                ivPostToBe.setImageResource(R.drawable.post);
            }
        });
//        image = getContext().getIntent().getParcelableExtra("picture");
//        ivPostToBe.setImageBitmap(image);


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
                etDescription.setText("");
                ivPostToBe.setImageResource(R.drawable.post);
                Log.i(TAG, "Saved the post successfully");


            }


        });
//        while (post.getImageId() == null) {
//            Log.i(TAG, "TESTING: " + post.getImageId());
//        }

    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this.getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // Load a bitmap from the drawable folder

                Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(photoFileName));
                // by this point we have the camera photo on disk
                Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());

                // Resize the bitmap to 150x100 (width x height)
                Bitmap bMapScaled = Bitmap.createScaledBitmap(rawTakenImage, 50, 50, true);

                image = bMapScaled;
                ivPostToBe.setImageBitmap(image);


                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
//                Intent i = new Intent(MainActivity.this, AddPostActivity.class);
//                i.putExtra("picture", bMapScaled);
//                i.putExtra("pictureFile", photoFile);
//                startActivityForResult(i, ADD_POST_REQUEST_CODE);

                // Pass it to AddPostActivity with Parcelable
//                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
//                ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this.getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
