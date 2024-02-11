package org.ntj_workout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    public static final String PREF_APP_UNLOCKED = "APP_UNLOCKED";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_training, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showView(view);
    }

    private void showView (@NonNull final View view) {
        SharedPreferences appPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        final Button goTrainingButton = view.findViewById(R.id.button_go_training);
        final Button validateCodeButton = view.findViewById(R.id.button_validate_code);
        final EditText codeEditText = view.findViewById(R.id.edit_text_code_input);
        Database database = new Database();
        if (!appPreferences.contains(PREF_APP_UNLOCKED) && !appPreferences.getBoolean(PREF_APP_UNLOCKED,false)) {
            showLockedMode(validateCodeButton, codeEditText, appPreferences, goTrainingButton, database);
        } else {
            showUnlockedMode(validateCodeButton, codeEditText, database);
        }

        final Spinner levelSpinner = view.findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this.requireContext(),
                R.array.belts, android.R.layout.simple_spinner_dropdown_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        final Spinner workTypeSpinner = view.findViewById(R.id.spinner_work_type);
        ArrayAdapter<CharSequence> workTypeAdapter = ArrayAdapter.createFromResource(this.requireContext(),
                R.array.work_types, android.R.layout.simple_spinner_dropdown_item);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner.setAdapter(workTypeAdapter);

        final SwitchCompat keepScreenOnSwitch = view.findViewById(R.id.switch_keep_screen_on);
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


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
    private void showUnlockedMode(Button validateCodeButton, EditText codeEditText, Database database) {
        validateCodeButton.setVisibility(View.INVISIBLE);
        codeEditText.setVisibility(View.INVISIBLE);
        database.init(this, requireContext());
    }
    private void showLockedMode(Button validateCodeButton, EditText codeEditText, SharedPreferences appPreferences, Button goTrainingButton, Database database) {
        validateCodeButton.setOnClickListener(buttonView -> {
            if (codeEditText.getText().toString().equals(BuildConfig.OPEN_KEY)) {
                appPreferences.edit().putBoolean(PREF_APP_UNLOCKED, true).apply();
                showUnlockedMode(validateCodeButton, codeEditText, database);
            } else {
                Toast.makeText(getContext(), R.string.home_invalid_unlock_code, Toast.LENGTH_SHORT).show();
            }
        });
        validateCodeButton.setVisibility(View.VISIBLE);
        codeEditText.setVisibility(View.VISIBLE);
        goTrainingButton.setVisibility(View.INVISIBLE);
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