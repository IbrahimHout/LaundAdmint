package dev.ibrahhout.shinystoreadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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


//    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);


        catKey = getIntent().getStringExtra(Constants.EXTRA_CATEGORY_ID);
        if (catKey!=null) {


            addProudctButton.setOnClickListener(view -> buildProductIntoFirebase());
            uploadProductPhotoButton.setOnClickListener(v -> uploadPhoto());
        }
    }

    private void uploadPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();





            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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

            String linkOfPhoto = "https://is3-ssl.mzstatic.com/image/thumb/Purple49/v4/28/82/50/288250cc-6b27-2a76-6097-b856c1f3f2e4/source/256x256bb.jpg";

            product.setImageURL(linkOfPhoto);
            //todo replace with picked photo link
            FirebaseDatabase.getInstance().getReference().child(Constants.PRODUCTS).child(catKey ).push().setValue(product).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(AddProductActivity.this, "Item was added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar.make(layout, "Problem happened Please try again", Snackbar.LENGTH_LONG).show();

                }
            });

        }

    }
}
