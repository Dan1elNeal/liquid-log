package ru.naumen.perfhouse.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.naumen.sd40.log.parser.DataType;
import ru.naumen.perfhouse.statdata.StatData;
import ru.naumen.perfhouse.statdata.StatDataService;
import ru.naumen.sd40.log.parser.IRenderMode;

/**
 * Created by doki on 23.10.16.
 */
@Controller
public class HistoryController
{
    @Autowired
    StatDataService service;

    @Autowired
    private Map<String, IRenderMode> parsingModes;

    private static final String NO_HISTORY_VIEW = "no_history";

    @RequestMapping(path = "/history/{client}/{parser}")
    public ModelAndView history(
            @PathVariable("client") String client,
            @PathVariable("parser") String parser,
            @RequestParam(name = "count", defaultValue = "864") int count
    ) throws ParseException {
        IRenderMode mode = parsingModes.get(parser);
        DataType dataType = mode.getDataType();
        String view = mode.getView();

        return getDataAndView(client, dataType, count, view);
    }

    @RequestMapping(path = "/history/{client}/{parser}/{year}/{month}/{day}")
    public ModelAndView historyByDay(
            @PathVariable("client") String client,
            @PathVariable("parser") String parser,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month,
            @PathVariable(name = "day", required = false) int day
    ) throws ParseException {
        IRenderMode mode = parsingModes.get(parser);
        DataType dataType = mode.getDataType();
        String view = mode.getView();

        return getDataAndViewByDate(client, dataType, year, month, day, view);
    }

    @RequestMapping(path = "/history/{client}/{parser}/{year}/{month}")
    public ModelAndView historyByMonth(
            @PathVariable("client") String client,
            @PathVariable("parser") String parser,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month
    ) throws ParseException {
        IRenderMode mode = parsingModes.get(parser);
        DataType dataType = mode.getDataType();
        String view = mode.getView();

        return getDataAndViewByDate(client, dataType, year, month, 0, view, true);
    }

    @RequestMapping(path = "/history/{client}/custom/{parser}")
    public ModelAndView customHistory(
            @PathVariable("client") String client,
            @PathVariable("parser") String parser,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("maxResults") int count
    ) throws ParseException {
        IRenderMode mode = parsingModes.get(parser);
        DataType dataType = mode.getDataType();
        String view = mode.getView();

        return getDataAndViewCustom(client, dataType, from, to, count, view);
    }

    private ModelAndView getDataAndView(String client, DataType dataType, int count, String viewName) throws ParseException {
        ru.naumen.perfhouse.statdata.StatData data = service.getData(client, dataType, count);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);

        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewByDate(String client, DataType type, int year, int month, int day,
            String viewName) throws ParseException
    {
        return getDataAndViewByDate(client, type, year, month, day, viewName, false);
    }

    private ModelAndView getDataAndViewByDate(String client, DataType type, int year, int month, int day,
            String viewName, boolean compress) throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData dataDate = service.getDataDate(client, type, year, month, day);
        if (dataDate == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }

        dataDate = compress ? service.compress(dataDate, 3 * 60 * 24 / 5) : dataDate;
        Map<String, Object> model = new HashMap<>(dataDate.asModel());
        model.put("client", client);
        model.put("year", year);
        model.put("month", month);
        model.put("day", day);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewCustom(String client, DataType dataType, String from, String to, int maxResults,
            String viewName) throws ParseException
    {
        StatData data = service.getDataCustom(client, dataType, from, to);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        data = service.compress(data, maxResults);
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("custom", true);
        model.put("from", from);
        model.put("to", to);
        model.put("maxResults", maxResults);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }
}
