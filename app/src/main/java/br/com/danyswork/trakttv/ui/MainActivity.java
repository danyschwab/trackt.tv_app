package br.com.danyswork.trakttv.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.danyswork.trakttv.R;
import br.com.danyswork.trakttv.presenter.TraktTVPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    TextView mText;

    private TraktTVPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new TraktTVPresenter(this);
        presenter.getPopularMovies();
    }

    public void setText(String text) {
        mText.setText(text);
    }
}
