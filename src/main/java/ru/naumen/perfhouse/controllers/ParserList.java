package ru.naumen.perfhouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.sd40.log.parser.IRenderMode;

import java.text.ParseException;
import java.util.*;

@Controller
public class ParserList {
    @Autowired
    private Map<String, IRenderMode> renderModes;

    @RequestMapping(path = "/parsers/{client}")
    public ModelAndView list(
            @PathVariable("client") String client,
            @RequestParam(name = "count", defaultValue = "864") int count
    ) {
        Map<String, Object> model = new HashMap<>();
        model.put("renderModes", this.renderModes);
        model.put("client", client);

        return new ModelAndView("parser_list", model, HttpStatus.OK);
    }

    @RequestMapping(path = "/parsers/{client}/{year}/{month}")
    public ModelAndView listByMonths(
            @PathVariable("client") String client,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month
    ) throws ParseException {
        return getDataAndViewByDate(client, year, month, 0);
    }

    @RequestMapping(path = "/parsers/{client}/{year}/{month}/{day}")
    public ModelAndView listByDay(
            @PathVariable("client") String client,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month,
            @PathVariable(name = "day", required = false) int day
    ) throws ParseException {
        return getDataAndViewByDate(client, year, month, day);
    }

    @RequestMapping(path = "/parsers/{client}/custom")
    public ModelAndView customList(
            @PathVariable("client") String client,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("maxResults") int maxResults
    ) throws ParseException {
        Map<String, Object> model = new HashMap<>();
        model.put("renderModes", this.renderModes);
        model.put("client", client);
        model.put("custom", true);
        model.put("from", from);
        model.put("to", to);
        model.put("maxResults", maxResults);

        return new ModelAndView("parser_list", model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewByDate(String client, int year, int month, int day) throws ParseException
    {

        Map<String, Object> model = new HashMap<>();
        model.put("renderModes", this.renderModes);
        model.put("client", client);
        model.put("year", year);
        model.put("month", month);
        model.put("day", day);
        return new ModelAndView("parser_list", model, HttpStatus.OK);
    }
}
