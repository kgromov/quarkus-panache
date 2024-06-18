package org.kgromov.projection;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;

public record CityProjection (long id, String name, @ProjectedFieldName("country.name") String country) {}
