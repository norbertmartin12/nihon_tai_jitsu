package org.ntj_workout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import org.ntj_workout.data.Question;
import org.ntj_workout.data.Revision;

public class QuestionFragment extends Fragment {
    private Revision revision;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(final @NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.revision = (Revision) getArguments().getSerializable("revision");
        if (this.revision.getCurrentQuestion() == null) {
            this.revision.next();
        }
        showQuestion(view);
        view.findViewById(R.id.button_next_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                revision.next();
                showQuestion(view);
            }
        });
    }

    private void showQuestion(View view) {
        Question question = this.revision.getCurrentQuestion();
        if (question == null) {
            NavHostFragment.findNavController(QuestionFragment.this)
                    .navigate(R.id.nav_to_continueTraining);
            return;
        }
        ((TextView) getView().findViewById(R.id.text_question)).setText(question.getLabel());
        ((TextView) getView().findViewById(R.id.text_answer)).setText("");

        final View buttonShowAnswer = view.findViewById(R.id.button_view_answer);
        buttonShowAnswer.setVisibility(View.VISIBLE);
        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getAnswer().startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(question.getAnswer()));
                    getActivity().startActivity(intent);
                } else {
                    buttonShowAnswer.setVisibility(View.GONE);
                    if (question.getAnswer() == null || question.getAnswer().isEmpty()) {
                        ((TextView) getView().findViewById(R.id.text_answer)).setText(R.string.question_no_answer_available);
                    } else {
                        ((TextView) getView().findViewById(R.id.text_answer)).setText(question.getAnswer());
                    }
                }
            }
        });
    }
}