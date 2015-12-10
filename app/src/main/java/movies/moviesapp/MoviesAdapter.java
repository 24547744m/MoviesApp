package movies.moviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.pojos.Result;

/**
 * Created by damuser on 10/12/15.
 */
public class MoviesAdapter extends ArrayAdapter<Result> {


    public MoviesAdapter(Context context, int resource, List<Result> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Result movie = getItem(position);
        Log.w("XXXXXXXX", movie.toString());

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_row, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
        TextView tvLanguage = (TextView) convertView.findViewById(R.id.tvMovieLanguage);
        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);

        tvTitle.setText(movie.getTitle());
        tvLanguage.setText(movie.getOriginalLanguage());

        Picasso.with(getContext()).
                load(movie.getPosterImage()).
                resize(160, 0).
                into(ivPoster);

        return convertView;
    }
}
