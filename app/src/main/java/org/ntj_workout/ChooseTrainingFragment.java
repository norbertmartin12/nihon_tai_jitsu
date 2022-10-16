package org.ntj_workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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

public class ChooseTrainingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_training, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        view.findViewById(R.id.button_go_training).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }

                Revision revision = new Database().init().start(Level.values()[level], Type.values()[type]);
                if (revision == null) {
                    Toast.makeText(getContext(), R.string.home_no_revision_available, Toast.LENGTH_SHORT).show();
                    return;
                }
                revision.next();
                Bundle bundle = new Bundle();
                bundle.putSerializable("revision", revision);
                NavHostFragment.findNavController(ChooseTrainingFragment.this).navigate(R.id.nav_home_to_question, bundle);
            }
        });
    }

}