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


    public void getPopularMovies(final ArrayAdapter<String> adapter){

        serviceInterface = retrofit.create(MoviesInterface.class);//Creación del servicio
        Call<MoviesData> call = serviceInterface.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(Response<MoviesData> response, Retrofit retrofit) {
                List<Result> movies;

                if (response.isSuccess()){
//                    Log.d("Resultsss", String.valueOf(response.body().getResults().size()));
                    movies = response.body().getResults();
                    Log.d("id", String.valueOf(movies.get(0).getId()));
                    Log.d("title", movies.get(0).getTitle());
                    Log.d("overview", movies.get(0).getOverview());

                    adapter.clear();
                    for (Result movie : movies) {
                        adapter.add(movie.getTitle());
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
        });



    }


    /**
     * Interfaz para hacer las llamadas desde Retrofit
     * */
    public interface MoviesInterface{
        @GET("movie/popular")
        Call<MoviesData> getPopularMovies(@Query("api_key") String apiKey);

        @GET("movie/top_rated")
        Call<String> getRatedMovies(@Query("api_key") String apiKey);

    }



}
