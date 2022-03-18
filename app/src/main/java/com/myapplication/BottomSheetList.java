package com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class BottomSheetList extends BottomSheetDialogFragment {
    View bottomSheetInternal;
    TextInputEditText txtedit_newlist;
    public BottomSheetList() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            bottomSheetInternal = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            assert bottomSheetInternal != null;

            txtedit_newlist=bottomSheetInternal.findViewById(R.id.txtedit_newlist);
            txtedit_newlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newlistDialog();
                    keyboardShow();
                }
            });

            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return inflater.inflate(R.layout.bottomlist, container, false);
    }

    private void newlistDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.newlistdialog, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("New Task List");

        dialogBuilder.setPositiveButton("Add", (dialogInterface, i) -> {
        });
        dialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void keyboardShow(){
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }



}
