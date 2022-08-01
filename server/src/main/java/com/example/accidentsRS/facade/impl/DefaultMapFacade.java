package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.DirectionalStreetConverter;
import com.example.accidentsRS.converter.IntersectionConverter;
import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.facade.MapFacade;
import com.example.accidentsRS.factory.GeoPointConverterFactory;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMapFacade implements MapFacade {

    private static final Integer MAX_NEAR_POINTS = 2;

    @Autowired
    IntersectionConverter defaultIntersectionConverter;

    @Autowired
    DirectionalStreetConverter defaultDirectionalStreetConverter;

    @Autowired
    GeoPointConverterFactory defaultGeoPointConverterFactory;

    @Autowired
    MapService defaultMapService;

    @Override
    public void addStreet(final DirectionalStreetData directionalStreetData) {
        defaultMapService.addStreet(
                defaultDirectionalStreetConverter.convert(directionalStreetData)
        );
    }

    @Override
    public void addIntersection(final IntersectionData intersectionData) {
        defaultMapService.addIntersection(
                defaultIntersectionConverter.convert(intersectionData)
        );
    }

    @Override
    public List<GeoPointData> findNearestPoints(final Location location) {
        final List<GeoPointData> geoPointDataList = new ArrayList<>();
        final List<GeoLocation> nearestPoints = defaultMapService
                .findNearestPoints(location.toPoint(), MAX_NEAR_POINTS);

        nearestPoints.forEach(geoLocation ->
                geoPointDataList.add(defaultGeoPointConverterFactory
                        .getConverterForGeoLocation(geoLocation)
                        .convert(geoLocation)
                )
        );

        return geoPointDataList;
    }
}