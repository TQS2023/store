package pt.ua.deti.store.picky;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiDecl {
    @GET("/pickup/points")
    Call<PickyPickupPoints> getPickupPoints();

    @POST("/package/new")
    Call<PickyCreatePackage> createPackage(
            @Body PickyPackage pickyPackage
    );

    @GET("/package/all")
    Call<PickyAllPackages> getAllPackages();
}
