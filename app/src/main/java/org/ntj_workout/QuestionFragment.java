package org.ntj_workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.ntj_workout.data.Question;
import org.ntj_workout.data.Revision;

public class QuestionFragment extends Fragment {
    private Revision revision;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (revision.previous() == null){
                    NavHostFragment.findNavController(QuestionFragment.this).navigate(R.id.nav_question_to_home);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("revision", revision);
                NavHostFragment.findNavController(QuestionFragment.this).navigate(R.id.nav_question_self, bundle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(final @NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.revision = (Revision) getArguments().getSerializable("revision");
        Question question = this.revision.getCurrentQuestion();
        if (question == null) {
            NavHostFragment.findNavController(QuestionFragment.this).navigate(R.id.nav_question_to_continueTraining);
            return;
        }

        String questionId = "#" + question.getId();
        ((TextView) getView().findViewById(R.id.text_question_id)).setText(questionId);
        ((TextView) getView().findViewById(R.id.text_question)).setText(question.getLabel());
        ProgressBar progressBar = ((ProgressBar) getView().findViewById(R.id.progress_bar));
        progressBar.setProgress(revision.currentIndex());
        progressBar.setMax(revision.size());

        String progressText = Math.round((double)revision.currentIndex() / revision.size() * 100)+ "%";
        ((TextView) getView().findViewById(R.id.text_progress)).setText(progressText);

        getView().findViewById(R.id.button_show_answer).setOnClickListener(buttonView -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("revision", revision);
            NavHostFragment.findNavController(QuestionFragment.this).navigate(R.id.nav_question_to_answer, bundle);
        });
    }

}