package com.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class BottomSheetNewTask extends BottomSheetDialogFragment {

    MaterialDatePicker materialDatePicker;
    TextInputEditText txt_editdate,txtedit_time;
    View bottomSheetInternal;
    MaterialButton btn_apply,btn_back;

    public BottomSheetNewTask() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    interface FragmentListener {
        void getView(View view);
    }

    static FragmentListener mFragmentListener;

    public static BottomSheetNewTask newInstance(FragmentListener listener) {
        mFragmentListener = listener;
        return new BottomSheetNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(getDialog()).setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            bottomSheetInternal = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            assert bottomSheetInternal != null;

            txt_editdate=bottomSheetInternal.findViewById(R.id.txtedit_date);
            Long today=MaterialDatePicker.todayInUtcMilliseconds();
            MaterialDatePicker.Builder builder=MaterialDatePicker.Builder.datePicker();

            CalendarConstraints.Builder consBuilder=new CalendarConstraints.Builder();
            consBuilder.setValidator(DateValidatorPointForward.now());

            txt_editdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assert getFragmentManager() != null;
                    materialDatePicker.show(getFragmentManager(),"DATE_PICKER");
                }
            });

            builder.setTitleText("Select task date");
            builder.setSelection(today);
            builder.setCalendarConstraints(consBuilder.build());

            materialDatePicker=builder.build();
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    txt_editdate.setText(materialDatePicker.getHeaderText());

                }
            });
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            txtedit_time=bottomSheetInternal.findViewById(R.id.txtedit_time);
            txtedit_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            txtedit_time.setText((String.format("%02d:%02d", i,i1)));
                        }
                    },hour,minute,true);

                    timePickerDialog.show();
                }
            });

            btn_back=bottomSheetInternal.findViewById(R.id.btn_back);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                    keyboardDismiss();
                }
            });


            btn_apply=bottomSheetInternal.findViewById(R.id.btn_apply);
            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mFragmentListener.getView(getView());
                    d.dismiss();
                    keyboardDismiss();

                }
            });


            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return inflater.inflate(R.layout.addtasks, container, false);
    }
    public void keyboardDismiss(){
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }

}



















