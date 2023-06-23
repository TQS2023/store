package pt.ua.deti.store.picky;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiDecl {
    @GET("/api/pickup/points")
    Call<PickyPickupPoints> getPickupPoints();

    @POST("/api/package/new")
    Call<PickyCreatePackage> createPackage(
            @Body PickyPackageRequest pickyPackage
    );

    @GET("/api/package/all")
    Call<PickyAllPackages> getAllPackages();
}
