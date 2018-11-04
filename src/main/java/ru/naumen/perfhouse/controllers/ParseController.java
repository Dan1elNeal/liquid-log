package ru.naumen.perfhouse.controllers;
;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import ru.naumen.sd40.log.parser.LogParser;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class ParseController {
    @RequestMapping(path = "/parse", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity parseLog(
            @RequestParam(name = "dbname") String dbName,
            @RequestParam(name = "mode", defaultValue = "sdng") String mode,
            @RequestParam(name = "filepath") String filePath,
            @RequestParam(name = "timezone", defaultValue = "GMT") String timezone,
            @RequestParam(name = "trace", defaultValue = "show") String trace
    ) throws ParseException, IOException {
        boolean withTrace = trace.equals("show");
        LogParser.parse(dbName, mode, filePath, timezone, withTrace);

        return new ResponseEntity<>("Successfully completed!", HttpStatus.OK);
    }
}
