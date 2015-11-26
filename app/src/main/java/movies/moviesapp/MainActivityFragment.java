package movies.moviesapp;

import android.os.Bundle;
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
import java.util.Arrays;

import movies.pojos.ApiData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private final String API_KEY = "392610fb7cf880a09d912568ce434ef2";
    private final String BASE_URL = "http://api.themoviedb.org/3/";


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

        String[] data = {
                "Los 400 golpes",
                "El odio",
                "El padrino",
                "El padrino. Parte II",
                "Ocurrió cerca de su casa",
                "Infiltrados",
                "Umberto D."
        };

        items = new ArrayList<>(Arrays.asList(data));

        adapter = new ArrayAdapter<String>(
                getContext(),
                R.layout.movie_row,
                R.id.tvMovie,
                items
        );
        lvMovies.setAdapter(adapter);

        return rootView;

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
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        MoviesInterface serviceInterface = retrofit.create(MoviesInterface.class);//Creación del servicio
//        Log.d("holaaaaaa", "aahahahaha");

//        Call<ApiData> call = serviceInterface.getPopularMovies(API_KEY);
        Call<ApiData> call = serviceInterface.getRatedMovies(API_KEY);
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Response<ApiData> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Log.d("Petición: ","OKKKKKKKKK");
//                    ApiData apiData = response.body();
                    Log.e("respuesta ", response.message());
                    Log.e("Datos ", String.valueOf(response.body().getTotalResults()));


                }else{
                    Log.e("Error", response.errorBody().toString());

                }
//                    Log.d("Petición: ","NOOOOO");

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }


    /**
     * Interfaz para hacer las llamadas desde Retrofit
     * */
    public interface MoviesInterface{
        @GET("movie/popular")
        Call<ApiData> getPopularMovies(@Query("api_key") String apiKey);

        @GET("movie/top_rated")
        Call<ApiData> getRatedMovies(@Query("api_key") String apiKey);


    }


}
