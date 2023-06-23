package pt.ua.deti.store.picky;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class Api {
    @Value("${picky.api.url}")
    private String url;

    private final ApiDecl picky;

    public Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        picky = retrofit.create(ApiDecl.class);
    }

    public PickyCreatePackage createPackage(PickyPackage pickyPackage) {
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
