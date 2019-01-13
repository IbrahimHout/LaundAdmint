package dev.ibrahhout.shinystoreadmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Models.Category;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_activitu);
        ButterKnife.bind(this);


        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildCategoryIntoFirebase();
            }
        });

    }

    private void buildCategoryIntoFirebase() {

        if (categoryName.getEditText().getText().toString().isEmpty() || categoryDescription.getEditText().getText().toString().isEmpty()) {

            Snackbar.make(layout,"Please fill all requirements",Snackbar.LENGTH_LONG).show();

            return;
        }else {
            Category category = new Category();
            category.setName(categoryName.getEditText().getText().toString());
            category.setDescription(categoryDescription.getEditText().getText().toString());
            String linkOfPhoto = "https://is3-ssl.mzstatic.com/image/thumb/Purple49/v4/28/82/50/288250cc-6b27-2a76-6097-b856c1f3f2e4/source/256x256bb.jpg";

            category.setImageLink(linkOfPhoto);
            //todo replace with picked photo link
            FirebaseDatabase.getInstance().getReference().child(Constants.CATEGORIES).push().setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(AddCategoryActivity.this, "Item was added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Snackbar.make(layout,"Problem happened Please try again",Snackbar.LENGTH_LONG).show();

                    }
                }
            });

        }


    }
}
