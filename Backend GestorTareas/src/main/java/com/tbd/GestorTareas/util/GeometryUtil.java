package com.tbd.GestorTareas.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryUtil {
    public static final int SRID = 4326; // WGS84

    public static Point createPoint(double lat, double lon) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        return geometryFactory.createPoint(new Coordinate(lon, lat));
    }

    public static Point wktToPoint(String wktPoint) {
        WKTReader reader = new WKTReader();
        try {
            return (Point) reader.read(wktPoint);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid WKT format", e);
        }
    }

    public static String pointToWKT(Point point) {
        return "POINT(" + point.getX() + " " + point.getY() + ")";
    }
}