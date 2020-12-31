package com.example.sidheshnaiktentwentyassignment.ui.ticketbookscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.sidheshnaiktentwentyassignment.databinding.ActivityTicketBookingBinding;
import com.example.sidheshnaiktentwentyassignment.ui.base.BaseActivity;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import org.jetbrains.annotations.Nullable;

public class TicketBookingActivity extends BaseActivity {
    private ActivityTicketBookingBinding binding;
    private String selectedLocation = "";
    private String selectedCinema = "";
    private String selectedSeats = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpSpinner();
        setButtonClickListener();
    }

    private void setButtonClickListener() {
        binding.bookButton.setOnClickListener(view -> {
            if (selectedLocation.isEmpty() && selectedCinema.isEmpty() && selectedSeats.isEmpty()) {
                Toast.makeText(TicketBookingActivity.this, "You need to select all fields.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TicketBookingActivity.this, "Your tickets has been booked successfully.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setUpSpinner() {
        binding.locationSpinner.setOnSpinnerItemSelectedListener((i, o, position, t1) -> {
            System.out.println("Item = " + t1.toString());
            selectedLocation = t1.toString();
        });

        binding.cinemaSpinner.setOnSpinnerItemSelectedListener((i, o, position, t1) -> {
            System.out.println("Item = " + t1.toString());
            selectedCinema = t1.toString();
        });
        binding.seatSpinner.setOnSpinnerItemSelectedListener((i, o, position, t1) -> {
            System.out.println("Item = " + t1.toString());
            if (selectedSeats.isEmpty()) {
                selectedSeats += t1.toString();
            } else {
                selectedSeats += ", "+t1.toString();
            }
            binding.selectedSeatsTextView.setText(selectedSeats);
        });
    }
}
