package org.ntj_workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.ntj_workout.data.Database;
import org.ntj_workout.data.Level;
import org.ntj_workout.data.Revision;
import org.ntj_workout.data.Type;

import java.util.Calendar;

public class ChooseTrainingFragment extends Fragment implements Database.Initiator {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_training, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showView(view);
    }

    private void showView (@NonNull final View view) {
        final Button goTrainingButton = view.findViewById(R.id.button_go_training);
        final View unlockView = view.findViewById(R.id.unlock_view);
        final Button validateCodeButton = view.findViewById(R.id.button_validate_code);
        final EditText codeEditText = view.findViewById(R.id.edit_text_code_input);
        Database database = new Database();
        if (SafetyAccess.getInstance().hasAccessToContent(this.requireActivity())) {
            showUnlockedMode(unlockView, database);
        } else {
            showLockedMode(unlockView, validateCodeButton, codeEditText, goTrainingButton, database);
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
    private void showUnlockedMode(View unlockView, Database database) {
        unlockView.setVisibility(View.INVISIBLE);
        database.init(this, requireContext());
    }
    private void showLockedMode(View unlockView, Button validateCodeButton, EditText codeEditText, Button goTrainingButton, Database database) {
        validateCodeButton.setOnClickListener(buttonView -> {
            String inputCode = codeEditText.getText().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, SafetyAccess.DELAY_ALLOWED_BY_CODE);
            if (SafetyAccess.getInstance().accept(this.requireActivity(), inputCode, calendar.getTime())) {
                Toast.makeText(this.requireContext(), R.string.content_unlocked, Toast.LENGTH_LONG).show();
                showUnlockedMode(unlockView, database);
            } else {
                Toast.makeText(getContext(), R.string.home_invalid_unlock_code, Toast.LENGTH_SHORT).show();
            }
        });
        TextView practiceAdviceTextView = requireActivity().findViewById(R.id.text_training_practice_warning);
        practiceAdviceTextView.setOnLongClickListener(null);
        unlockView.setVisibility(View.VISIBLE);
        goTrainingButton.setVisibility(View.INVISIBLE);
    }
    @Override
    public void loaded(Database database) {
        Button goTrainingButton = requireActivity().findViewById(R.id.button_go_training);
        TextView practiceAdviceTextView = requireActivity().findViewById(R.id.text_training_practice_warning);
        practiceAdviceTextView.setOnLongClickListener( textView -> {
                    Toast.makeText(this.getContext(), this.getString(R.string.content_availability, SafetyAccess.getInstance().getEndValidityDate(this.requireActivity())), Toast.LENGTH_LONG).show();
                    return true;
                }
        );
        requireActivity().runOnUiThread(() ->  goTrainingButton.setVisibility(View.VISIBLE));
        goTrainingButton.setOnLongClickListener(view -> {
            Toast.makeText(getContext(), database.getInitDescription(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

}