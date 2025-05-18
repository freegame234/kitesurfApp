import com.example.kitesurf.domaine.model.Calendrier
import com.example.kitesurf.domaine.model.Classement
import com.example.kitesurf.domaine.model.Competition
import com.example.kitesurf.domaine.model.Kitesurfer
import com.example.kitesurf.domaine.model.Meteo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("classement/{competitionId}")
    suspend fun getClassement(@Path("competitionId") competitionId: Int): List<Classement>

    @GET("calendrier/{competitionId}")
    suspend fun getCalendrier(@Path("competitionId") competitionId: Int): List<Calendrier>

    @GET("competition")
    suspend fun getCompetitions(): List<Competition>

    @GET("kitesurfer")
    suspend fun getKitesurfers(): List<Kitesurfer>

    @GET("meteo")
    suspend fun getMeteo(
        @Query("start") start: String? = null,
        @Query("end") end: String? = null
    ): List<Meteo>
}
