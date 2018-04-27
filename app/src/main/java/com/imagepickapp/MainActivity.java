package com.imagepickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.imagepickapp.Adapter.ImageAdapter;
import com.imagepickapp.Database.DatabaseAccess;
import com.imagepickapp.Helper.AppConstant;
import com.imagepickapp.Helper.DividerItemDecoration;
import com.imagepickapp.Helper.Utility;
import com.imagepickapp.MediaPermission.PermissionsChecker;
import com.imagepickapp.MediaPermission.PickMediaActivity;
import com.imagepickapp.Model.ImageModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
    DatabaseAccess databaseAccess;
    FloatingActionButton fabButton;

    PickMediaActivity pickMediaActivity = PickMediaActivity.getInstance();
    PermissionsChecker checker = PermissionsChecker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);

        databaseAccess = DatabaseAccess.getInstance(MainActivity.this);

        imageModelArrayList = databaseAccess.GetImageListFromImageDB();

        SetAdapter(imageModelArrayList);

        GetImagesLocally();


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                     pickMediaActivity.checkPermission(MainActivity.this, AppConstant.PERMISSIONS_CAMERA, "Camera", getString(R.string.cameraNeverAskAgain));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    private void SetAdapter(ArrayList<ImageModel> imageModelArrayList) {
        try {
            imageAdapter = new ImageAdapter(MainActivity.this, imageModelArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST, 0, 1));
            recyclerView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetImagesLocally() {
        try {
            imageModelArrayList = databaseAccess.GetImageListFromImageDB();
            imageAdapter.UpdateList(imageModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            //Every time app is opening we are checking camera permission is given or not. (It might happen somebody has clear the ache data manually from app setting )
            if (!checker.lacksPermissions(this, AppConstant.PERMISSIONS_CAMERA)) {
                pickMediaActivity.SetToSharePreference(MainActivity.this, getString(R.string.cameraNeverAskAgain), false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addUpdateDB(ImageModel imageModel) {
        try {
            imageAdapter.addItem(imageModel);
            //recyclerView.scrollToPosition(imageModelArrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /// this is for image picker as well as place picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            pickMediaActivity.activityResult(MainActivity.this, requestCode, resultCode, data);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
