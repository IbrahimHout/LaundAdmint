package dev.ibrahhout.shinystoreadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Models.Product;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;


public class AddProductActivity extends AppCompatActivity {

    @BindView(R.id.categoryName)
    TextInputLayout categoryName;
    @BindView(R.id.productDescription)
    TextInputLayout productDescription;
    @BindView(R.id.productPrice)
    TextInputLayout productPrice;
    @BindView(R.id.photoLink)
    TextView photoLink;
    @BindView(R.id.uploadProductPhotoButton)
    Button uploadProductPhotoButton;
    @BindView(R.id.addProudctButton)
    Button addProudctButton;
    @BindView(R.id.layout)
    LinearLayout layout;

    String catKey;


    StorageReference storageRef;
    private String linkOfPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

        linkOfPhoto = "https://is3-ssl.mzstatic.com/image/thumb/Purple49/v4/28/82/50/288250cc-6b27-2a76-6097-b856c1f3f2e4/source/256x256bb.jpg";
        storageRef = FirebaseStorage.getInstance().getReference();

        catKey = getIntent().getStringExtra(Constants.EXTRA_CATEGORY_ID);
        if (catKey != null) {


            addProudctButton.setOnClickListener(view -> buildProductIntoFirebase());
            uploadProductPhotoButton.setOnClickListener(v -> uploadPhoto());
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


                final ProgressDialog progressDialog = new ProgressDialog(AddProductActivity.this);
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Just a second, We're uplodaing your photo");
                progressDialog.show();


                Uri resultUri = result.getUri();


                StorageReference riversRef = storageRef.child("products/" + random() + resultUri.getLastPathSegment());


                riversRef.putFile(resultUri).continueWithTask(task -> {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return riversRef.getDownloadUrl();

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(Task<Uri> task) {

                        if (task.isSuccessful()) {

                            linkOfPhoto = task.getResult().toString();
                            photoLink.setText(linkOfPhoto);
                            Toast.makeText(AddProductActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } else {
                            Snackbar.make(layout, "File Wasn't uploaded! Please Try Again", Snackbar.LENGTH_LONG).show();
                            progressDialog.dismiss();


                        }
                    }
                });



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("errorimageCropper", "onActivityResult: " + error.toString());
            }
        }
    }

    private void buildProductIntoFirebase() {
        if (categoryName.getEditText().getText().toString().isEmpty() || productDescription.getEditText().getText().toString().isEmpty() || productPrice.getEditText().getText().toString().isEmpty()) {

            Snackbar.make(layout, "Please fill all requirements", Snackbar.LENGTH_LONG).show();

            return;
        } else {
            Product product = new Product();
            product.setName(categoryName.getEditText().getText().toString());
            product.setDescription(productDescription.getEditText().getText().toString());
            product.setPrice(productPrice.getEditText().getText().toString());


            product.setImageURL(linkOfPhoto);
            //todo replace with picked photo link
            FirebaseDatabase.getInstance().getReference().child(Constants.PRODUCTS).child(catKey).push().setValue(product).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(AddProductActivity.this, "Item was added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar.make(layout, "Problem happened Please try again", Snackbar.LENGTH_LONG).show();

                }
            });

        }

    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(15);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
