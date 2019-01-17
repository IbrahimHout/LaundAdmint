package dev.ibrahhout.shinystoreadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Models.Category;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

import static dev.ibrahhout.shinystoreadmin.AddProductActivity.random;

public class AddCategoryActivity extends AppCompatActivity {

    @BindView(R.id.categoryName)
    TextInputLayout categoryName;
    @BindView(R.id.categoryDescription)
    TextInputLayout categoryDescription;
    @BindView(R.id.photoLink)
    TextView photoLink;
    @BindView(R.id.uploadCatPhotoButton)
    Button uploadCatPhotoButton;
    @BindView(R.id.addCategoryButton)
    Button addCategoryButton;
    @BindView(R.id.layout)
    LinearLayout layout;

    String linkOfPhoto;

    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_activitu);
        ButterKnife.bind(this);

        storageRef = FirebaseStorage.getInstance().getReference();
        linkOfPhoto = "https://is3-ssl.mzstatic.com/image/thumb/Purple49/v4/28/82/50/288250cc-6b27-2a76-6097-b856c1f3f2e4/source/256x256bb.jpg";

        addCategoryButton.setOnClickListener(view -> buildCategoryIntoFirebase());

        uploadCatPhotoButton.setOnClickListener(v -> uploadPhoto());

    }

    private void buildCategoryIntoFirebase() {

        if (categoryName.getEditText().getText().toString().isEmpty() || categoryDescription.getEditText().getText().toString().isEmpty()) {

            Snackbar.make(layout, "Please fill all requirements", Snackbar.LENGTH_LONG).show();

            return;
        } else {
            Category category = new Category();
            category.setName(categoryName.getEditText().getText().toString());
            category.setDescription(categoryDescription.getEditText().getText().toString());


            category.setImageLink(linkOfPhoto);
            //todo replace with picked photo link
            FirebaseDatabase.getInstance().getReference().child(Constants.CATEGORIES).push().setValue(category).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(AddCategoryActivity.this, "Item was added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar.make(layout, "Problem happened Please try again", Snackbar.LENGTH_LONG).show();

                }
            });

        }


    }

    private void uploadPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                final ProgressDialog progressDialog = new ProgressDialog(AddCategoryActivity.this);
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Just a second, We're uplodaing your photo");
                progressDialog.show();


                Uri resultUri = result.getUri();


                StorageReference riversRef = storageRef.child("categories/" + random() + resultUri.getLastPathSegment());


                riversRef.putFile(resultUri).continueWithTask(task -> {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return riversRef.getDownloadUrl();

                }).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        linkOfPhoto = task.getResult().toString();
                        photoLink.setText(linkOfPhoto);
                        Toast.makeText(AddCategoryActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                        Snackbar.make(layout, "File Wasn't uploaded! Please Try Again", Snackbar.LENGTH_LONG).show();
                        progressDialog.dismiss();


                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("errorimageCropper", "onActivityResult: " + error.toString());
            }
        }
    }

}
