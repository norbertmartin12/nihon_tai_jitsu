package org.ntj_workout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.ntj_workout.data.Database;
import org.ntj_workout.data.Level;
import org.ntj_workout.data.Revision;
import org.ntj_workout.data.Type;

public class ChooseTrainingFragment extends Fragment implements Database.Initiator {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_training, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showView(view, savedInstanceState);
    }

    private void showView (@NonNull final View view, Bundle savedInstanceState) {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Database database = new Database().init(this, connectivityManager);

        final Spinner levelSpinner = view.findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.belts, android.R.layout.simple_spinner_dropdown_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        final Spinner workTypeSpinner = view.findViewById(R.id.spinner_work_type);
        ArrayAdapter<CharSequence> workTypeAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.work_types, android.R.layout.simple_spinner_dropdown_item);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner.setAdapter(workTypeAdapter);

        final SwitchCompat keepScreenOnSwitch = view.findViewById(R.id.switch_keep_screen_on);
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final Button goTrainingButton = view.findViewById(R.id.button_go_training);
        goTrainingButton.setVisibility(View.INVISIBLE);
        goTrainingButton.setOnLongClickListener(null);
        goTrainingButton.setOnClickListener(buttonView -> {
            int level = levelSpinner.getSelectedItemPosition() - 1;
            int type = workTypeSpinner.getSelectedItemPosition() - 1;

            if (level == -1) {
                Toast.makeText(getContext(), R.string.home_invalid_level, Toast.LENGTH_SHORT).show();
                return;
            }
            if (type == -1) {
                Toast.makeText(getContext(), R.string.home_invalid_work_type, Toast.LENGTH_SHORT).show();
                return;
            }
            if (keepScreenOnSwitch.isChecked()) {
                requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }

            Revision revision = database.start(Level.values()[level], Type.values()[type]);
            if (revision == null) {
                Toast.makeText(getContext(), R.string.home_no_revision_available, Toast.LENGTH_SHORT).show();
                return;
            }
            revision.next();
            Bundle bundle = new Bundle();
            bundle.putSerializable("revision", revision);
            NavHostFragment.findNavController(ChooseTrainingFragment.this).navigate(R.id.nav_home_to_question, bundle);
        });
    }

    @Override
    public void loaded(Database database) {
        Button goTrainingButton = requireActivity().findViewById(R.id.button_go_training);
        requireActivity().runOnUiThread(() ->  goTrainingButton.setVisibility(View.VISIBLE));
        goTrainingButton.setOnLongClickListener(view -> {
            Toast.makeText(getContext(), database.getInitDescription(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

}