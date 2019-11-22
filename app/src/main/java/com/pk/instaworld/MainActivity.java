package com.pk.instaworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pk.instaworld.fragmants.ComposeFragment;
import com.pk.instaworld.fragmants.HomeFragment;
import com.pk.instaworld.fragmants.ProfileFragment;

import org.parceler.Parcels;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public static final int ADD_POST_REQUEST_CODE = 20;
    BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "I am in MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
//                        fragmentManager.beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_compose:
                         fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragmentManager.beginTransaction().replace(R.id.flContainer, new ProfileFragment()).commit();
                        fragment = new ProfileFragment();
                        break;
                    default:
//                        fragmentManager.beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
                        fragment = new HomeFragment();
                        break;

                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }

//    private void launchCamera() {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
////                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                // Load a bitmap from the drawable folder
//
//                Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(photoFileName));
//                // by this point we have the camera photo on disk
//                Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
//
//                // Resize the bitmap to 150x100 (width x height)
//                Bitmap bMapScaled = Bitmap.createScaledBitmap(rawTakenImage, 50, 50, true);
//
//                fragmentManager.beginTransaction().replace(R.id.flContainer, new ComposeFragment()).commit();
//
//
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
////                Intent i = new Intent(MainActivity.this, AddPostActivity.class);
////                i.putExtra("picture", bMapScaled);
////                i.putExtra("pictureFile", photoFile);
////                startActivityForResult(i, ADD_POST_REQUEST_CODE);
//
//                // Pass it to AddPostActivity with Parcelable
////                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
////                ivPreview.setImageBitmap(takenImage);
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        } else if(requestCode == ADD_POST_REQUEST_CODE && resultCode == RESULT_OK) {
//            // Use Recycler view to display all the posts
//            String imageId = data.getStringExtra("imageId");
//            Log.i(TAG, "onActivityResult" + " these are extras: " + data.getExtras());
//            Log.i(TAG, "Print the id" + imageId);
//            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//            Bitmap imageToBeDisplayed = data.getParcelableExtra("image");
//            ivPost.setImageBitmap(imageToBeDisplayed);
//
//            query.getInBackground(imageId, new GetCallback<Post>() {
//                @Override
//                public void done(Post object, ParseException e) {
//                    if (e == null) {
//                        Log.i(TAG, "fetched the things successfully" + object.getImage().getUrl());
////                        Glide.with(MainActivity.this).load(object.getImage().getUrl()).into(ivPost);
//
//                        tvDescription.setText(object.getDescription());
//                        tvUserName.setText("@" + ParseUser.getCurrentUser().getUsername());
//                    } else {
//                        Log.i(TAG, "Error fetching what needed for displaying image", e);
//                    }
//                }
//            });
//            Log.i(TAG, "Added a post successfully!");
//        }

//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOut:
                Log.i(TAG, "Logging out from " + ParseUser.getCurrentUser());

                ParseUser.logOut();

                Log.i(TAG, "Now the user should be null: " + ParseUser.getCurrentUser());

                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
}
