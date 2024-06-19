package org.kgromov.web;

import com.fasterxml.jackson.databind.node.TextNode;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kgromov.domain.City;
import org.kgromov.projection.CityByCountryProjection;
import org.kgromov.projection.LanguageUsage;
import org.kgromov.projection.CityProjection;
import org.kgromov.repository.CityRepository;

import java.util.List;

@ApplicationScoped
@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {
    @Inject
    CityRepository cityRepository;

    @GET
    public List<City> getCities() {
        List<City> cities = City.listAll();
        return cityRepository.listAll(Sort.by("name"));
    }

    @GET
    @Path("{id}")
    public City getCity(@PathParam("id") Long cityId) {
        return City.findById(cityId);
    }

    @GET
    @Path("/projection")
    public List<CityProjection> getCitiesProjection() {
        return cityRepository.findAllCityCountries();
    }

    @GET
    @Path("{id}/projection")
    public CityProjection getCityProjection(@PathParam("id") Long cityId) {
        return cityRepository.findCityCountryById(cityId);
    }

    @GET
    @Path("/byCountry")
    public List<CityByCountryProjection> getCitiesByCountry() {
       return cityRepository.findAllCitiesGroupedByCountry();
    }

    @GET
    @Path("/{city}/languages")
    public List<LanguageUsage> getCityLanguages(@PathParam("city") String city) {
        return cityRepository.findCityLanguagesByName(city);
//        return cityRepository.findCityLanguagesNativeQuery(city);
    }

    @GET
    @Path("/{city}/language")
    public TextNode getCityMainLanguage(@PathParam("city") String city) {
//        return RestResponse.ok(cityRepository.getCityMainLanguage(city));
        return TextNode.valueOf(cityRepository.getCityMainLanguage(city));
    }
}

