package com.civitasv.spider.util;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;

public class ParseUtil {
    /**
     * 解析csv或txt
     *
     * @param path 解析文件路径
     * @return 解析结果
     */
    public static List<Map<String, String>> parseTxtOrCsv(String path) {
        File file = new File(path);
        List<Map<String, String>> res = new ArrayList<>();
        if (file.exists()) {
            try {
                Files.lines(file.toPath())
                        .forEach(new Consumer<String>() {
                            private int index = 0;
                            private String[] keys;

                            @Override
                            public void accept(String line) {
                                if (index == 0) {
                                    String[] arr = line.split(",");
                                    keys = new String[arr.length];
                                    for (int i = 0; i < arr.length; i++)
                                        keys[i] = arr[i].trim();
                                } else {
                                    String[] values = line.split(",");
                                    Map<String, String> item = new LinkedHashMap<>();
                                    for (int i = 0; i < values.length; i++)
                                        item.put(keys[i], values[i]);
                                    res.add(item);
                                }
                                index++;
                            }
                        });
            } catch (IOException e) {
                return null;
            }
        }
        return res;
    }

    /**
     * 解析字符串至数值
     *
     * @param text 字符串
     * @return int整型
     */
    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
