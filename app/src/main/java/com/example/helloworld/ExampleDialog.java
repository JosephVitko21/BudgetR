package com.example.helloworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;


import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {

    private LayoutInflater inflater;
    private View dialogView;
    private View scanLayout;
    private View manualLayout;
    private ImageButton enterManualButton;
    private ImageButton scanReceiptButton;
    private ImageButton cameraButton;
    private ImageButton photoButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final page2test p2test = new page2test();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final AlertDialog dialogS = builder.create();

        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
        dialogS.setView(dialogView);

        enterManualButton = (ImageButton) dialogView.findViewById(R.id.enter_manual_button);
        enterManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogS.dismiss();

            }
        });
        scanReceiptButton = (ImageButton) dialogView.findViewById(R.id.scan_receipt_button);
        scanReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLayout = inflater.inflate(R.layout.scan_add_layout, null);
                dialogS.setContentView(scanLayout);

                cameraButton = (ImageButton) scanLayout.findViewById(R.id.camera_button);
                photoButton = (ImageButton) scanLayout.findViewById(R.id.photo_button);

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p2test.camera(dialogS);
                    }
                });

                photoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p2test.gallery(dialogS);
                    }
                });



            }
        });

        dialogS.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return dialogS;
    }


}
