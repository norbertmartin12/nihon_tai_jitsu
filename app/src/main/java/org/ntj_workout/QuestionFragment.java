package org.ntj_workout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        final WebView mediaWebView = getView().findViewById(R.id.web_view_media);
        mediaWebView.setWebViewClient(new WebViewClient());
        mediaWebView.getSettings().setJavaScriptEnabled(true);
        mediaWebView.getSettings().setLoadsImagesAutomatically(true);
        mediaWebView.getSettings().setLoadWithOverviewMode(true);

        getView().findViewById(R.id.button_show_next_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                mediaWebView.stopLoading();
                mediaWebView.removeAllViews();
                mediaWebView.clearCache(true);
                mediaWebView.clearHistory();
                revision.next();
                showQuestion();
            }
        });

        getView().findViewById(R.id.button_show_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
            }
        });



        showQuestion();
    }

    private void showQuestion() {
        Question question = this.revision.getCurrentQuestion();
        if (question == null) {
            NavHostFragment.findNavController(QuestionFragment.this)
                    .navigate(R.id.nav_to_continueTraining);
            return;
        }
        getView().findViewById(R.id.content_answer).setVisibility(View.GONE);

        ((TextView) getView().findViewById(R.id.text_question)).setText(question.getLabel());
        getView().findViewById(R.id.button_show_answer).setVisibility(View.VISIBLE);
    }

    private void showAnswer() {
        getView().findViewById(R.id.button_show_answer).setVisibility(View.GONE);
        getView().findViewById(R.id.content_answer).setVisibility(View.VISIBLE);
        Question question = this.revision.getCurrentQuestion();

        final TextView textAnswer = getView().findViewById(R.id.text_answer);
        textAnswer.setVisibility(View.VISIBLE);
        if (question.getTextAnswer() != null) {
            textAnswer.setText(question.getTextAnswer());
        } else if (!question.hasAnswer()) {
            textAnswer.setText(R.string.question_no_answer_available);
        } else {
            getView().findViewById(R.id.text_answer).setVisibility(View.GONE);
        }

        WebView mediaWebView = getView().findViewById(R.id.web_view_media);
        View mediaOutLink = getView().findViewById(R.id.media_out_link);
        if (question.hasMediaAnswer()) {
            mediaWebView.setVisibility(View.VISIBLE);
            mediaOutLink.setVisibility(View.VISIBLE);
            if (question.getImageAnswer() != null) {
                mediaWebView.loadUrl(question.getImageAnswer());
                mediaOutLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(question.getImageAnswer()));
                        getActivity().startActivity(intent);
                    }
                });
            } else if (question.getVideoAnswer() != null) {
                mediaWebView.loadUrl(question.getVideoAnswer());
                mediaOutLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(question.getVideoAnswer()));
                        getActivity().startActivity(intent);
                    }
                });
            }
        } else {
            mediaWebView.setVisibility(View.GONE);
            mediaOutLink.setVisibility(View.GONE);
        }
    }
}