import com.example.kitesurf.data.datasource.ApiService
import com.example.kitesurf.network.UnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://192.168.1.141:5000"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                UnsafeOkHttpClient.getUnsafeOkHttpClient().build() // SSL Self-signed
            )
            .build()
            .create(ApiService::class.java)
    }
}
