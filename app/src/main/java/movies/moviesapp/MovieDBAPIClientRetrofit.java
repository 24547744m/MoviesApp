package movies.moviesapp;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

import movies.pojos.MoviesData;
import movies.pojos.Result;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by damuser on 2/12/15.
 */
public class MovieDBAPIClientRetrofit {

    private final String API_KEY = "392610fb7cf880a09d912568ce434ef2";
    private final String BASE_URL = "http://api.themoviedb.org/3/";
    private Retrofit retrofit;
    private MoviesInterface serviceInterface;

    public MovieDBAPIClientRetrofit(){
        retrofit =  new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
    }

    public void getPopularMovies(final ArrayAdapter<Result> adapter){

        serviceInterface = retrofit.create(MoviesInterface.class);//Creación del servicio
        Call<MoviesData> call = serviceInterface.getPopularMovies(API_KEY);
        processCall(adapter, call);

    }

    public void getRatedMovies(final ArrayAdapter<Result> adapter){

        serviceInterface = retrofit.create(MoviesInterface.class);//Creación del servicio
        Call<MoviesData> call = serviceInterface.getRatedMovies(API_KEY);
        processCall(adapter, call);

    }


    private void processCall(final ArrayAdapter<Result> adapter, Call<MoviesData> call){

        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(Response<MoviesData> response, Retrofit retrofit) {
                List<Result> movies;

                if (response.isSuccess()){
//                    Log.d("Resultsss", String.valueOf(response.body().getResults().size()));
                    movies = response.body().getResults();
//                    Log.d("id", String.valueOf(movies.get(0).getId()));
//                    Log.d("title", movies.get(0).getTitle());
//                    Log.d("overview", movies.get(0).getOverview());
//                    Log.d("imagen", movies.get(0).getPosterPath());
//                    Log.d("idioma", movies.get(0).getOriginalLanguage());

                    adapter.clear();
                    for (Result movie : movies) {
                        adapter.add(movie);

                        Log.d("id", String.valueOf(movie.getId()));
                        Log.d("title", movie.getTitle());
                        Log.d("overview", movie.getOverview());
                        Log.d("imagen", (movie.getPosterPath() != null) ? movie.getPosterPath() : "no hay");
                        Log.d("idioma", (movie.getOriginalLanguage() != null) ? movie.getOriginalLanguage() : "no hay");
                    }


                }else {
                    Log.e("No hay resultados...", response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Errorrrr....", "errorrrr..");
                t.printStackTrace();
            }
        });//END CALLBACK


    }


    /**
     * Interfaz para hacer las llamadas desde Retrofit
     * */
    public interface MoviesInterface{
        @GET("movie/popular")
        Call<MoviesData> getPopularMovies(@Query("api_key") String apiKey);

        @GET("movie/top_rated")
        Call<MoviesData> getRatedMovies(@Query("api_key") String apiKey);

    }


}
