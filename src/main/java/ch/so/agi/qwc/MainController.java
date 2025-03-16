package ch.so.agi.qwc;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.util.factory.Hints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/ping")
    public ResponseEntity<String> ping(@RequestHeader Map<String, String> headers, HttpServletRequest request) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        
        log.info("server name: " + request.getServerName());
        log.info("context path: " + request.getContextPath());
        
        log.info("ping"); 
        
        return new ResponseEntity<String>("datahub", HttpStatus.OK);
    }    

    @GetMapping("/foo")
    public String foo() throws IOException {
        File file = new File("/Users/stefan/Downloads/ch.so.agi.lidar_2014.dtm.tif");
//        File file = new File("/Users/stefan/Downloads/swissalti3d_2019_2614-1258_0.5_2056_5728.tif");

//        Hints hints = new Hints(Hints.VIRTUAL_TABLE_PARAMETERS, Collections.singletonMap("SKIP_GDAL_METADATA", "true"));
//        GeoTiffReader reader = new GeoTiffReader(file, hints);

        Hints hints = new Hints();
        
        // This disables the GDAL metadata parsing
        hints.put(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        
        // Create the reader with our hints
        GeoTiffReader reader = new GeoTiffReader(file);
        
        // Read the coverage
        GridCoverage2D coverage = reader.read(null);
        
        return "foo";
    }
    

    
}
