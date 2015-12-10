package movies.moviesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import movies.pojos.Result;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Result> items;
    private MoviesAdapter adapter;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//seteando el menu
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //obteniendo el view principal del fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //obteniendo el listView del xml y seteandolo al view principal
        ListView lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);


        items = new ArrayList<>();

        adapter = new MoviesAdapter(
                getContext(),
                R.layout.movie_row,
                items
        );
        lvMovies.setAdapter(adapter);

        return rootView;

    }
    @Override
    public void onStart() {
        super.onStart();
//        refresh();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main_fragment, menu);//agregando items al menu
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh){
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo para refrescar los valores del listView
     * */
    private void refresh() {
        Log.d("refresshh","...");

        MovieDBAPIClientRetrofit apiClient = new MovieDBAPIClientRetrofit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String filterQuery = preferences.getString("filter_movie", "popular");
        if (filterQuery.equals("popular")){
            apiClient.getPopularMovies(adapter);
        }else{
            apiClient.getRatedMovies(adapter);
        }


    }





}
