package com.example.finalproject_lntandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject_lntandroid.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class home extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private FirebaseUser currentUser;
    private DocumentReference docUser;
    private Map<String, Object> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView((View) binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.navBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fT = fragmentManager.beginTransaction();

                switch(item.getItemId()){
                    case R.id.page_1:
                        fT.replace(R.id.fgView, CounterFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("counter") // name can be null
                            .commit();
                        break;
                    case R.id.page_2:
                        fT.replace(R.id.fgView, AreaFragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack("area") // name can be null
                                .commit();
                        break;
                    case R.id.page_3:
                        fT.replace(R.id.fgView, VolumeFragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack("volume") // name can be null
                                .commit();
                        break;
                    case R.id.page_4:
                        fT.replace(R.id.fgView, LicenseFragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack("license") // name can be null
                                .commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            finish();
        }else{
            binding.txtName.setText(currentUser.getDisplayName().toString());

            docUser = db.collection("users").document(currentUser.getUid());

            docUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    user = task.getResult().getData();
                    binding.txtID.setText(user.get("member_id").toString());
                }
            });
        }
    }

    public void logout(View view){
        mAuth.signOut();
        finish();
    }

    public void delete_account(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Delete account?");
        alertBuilder.setMessage("Your account would be deleted and cannot be undone.")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.collection("users")
                                .document(user.get("member_id").toString())
                                .set(new HashMap<>())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        docUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                currentUser.delete();
                                                mAuth.signOut();
                                                finish();
                                            }
                                        });
                                    }
                                });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

}