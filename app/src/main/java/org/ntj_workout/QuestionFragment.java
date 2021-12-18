package org.ntj_workout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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



        final View answerView = view.findViewById(R.id.content_answer);
        answerView.setVisibility(View.GONE);

        TextView textAnswer = getView().findViewById(R.id.text_answer);
        getView().findViewById(R.id.text_bloc).setVisibility(View.VISIBLE);
        if (question.getTextAnswer() != null) {
            textAnswer.setText(question.getTextAnswer());
        } else if (!question.hasAnswer()){
            textAnswer.setText(R.string.question_no_answer_available);
        } else {
            getView().findViewById(R.id.text_bloc).setVisibility(View.GONE);
        }

        View imageAnswer = getView().findViewById(R.id.image_answer);
        if (question.getImageAnswer() == null) {
            imageAnswer.setVisibility(View.GONE);
        } else {
            imageAnswer.setVisibility(View.VISIBLE);
            imageAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(question.getImageAnswer()));
                    getActivity().startActivity(intent);
                }
            });
        }

        View videoAnswer = getView().findViewById(R.id.video_answer);
        if (question.getVideoAnswer() == null) {
            videoAnswer.setVisibility(View.GONE);
        } else {
            videoAnswer.setVisibility(View.VISIBLE);
            videoAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(question.getVideoAnswer()));
                    getActivity().startActivity(intent);
                }
            });
        }


        final View buttonShowAnswer = view.findViewById(R.id.button_view_answer);
        buttonShowAnswer.setVisibility(View.VISIBLE);
        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonShowAnswer.setVisibility(View.GONE);
                answerView.setVisibility(View.VISIBLE);
            }
        });
    }
}