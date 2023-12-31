package pt.ua.deti.store.picky;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class Api {
    private final ApiDecl picky;

    public Api(@Value("${picky.api.url}") String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        picky = retrofit.create(ApiDecl.class);
    }

    public PickyCreatePackage createPackage(PickyPackageRequest pickyPackage) {
        try {
            return picky.createPackage(pickyPackage).execute().body();
        } catch (Exception e) {
            return null;
        }
    }

    public PickyAllPackages getAllPackages() {
        try {
            return picky.getAllPackages().execute().body();
        } catch (Exception e) {
            return null;
        }
    }

    public PickyPickupPoints getPickupPoints() {
        try {
            return picky.getPickupPoints().execute().body();
        } catch (Exception e) {
            return null;
        }
    }
}
