package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.ClimateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/climate")
public class ClimateEndpoint extends AbstractEndpoint {

    private static final Logger LOGGER = Logger.getLogger(ClimateEndpoint.class.getName());

    @Autowired
    ClimateFacade defaultClimateFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody final ClimateData climateData) throws PersistenceException {
        defaultClimateFacade.createClimateRecord(climateData);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void getSome(@RequestBody final Map<String, Object> getFilters) throws ValidationException {
        defaultClimateFacade.findAllMatchingFilter(getFilters);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateSome(@RequestBody final UpdateWrapper updateWrapper) throws ValidationException {
        defaultClimateFacade.updateAllMatchingFilter(
                updateWrapper.getFilters(),
                updateWrapper.getValues()
        );
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteSome(@RequestBody final Map<String, Object> deleteFilters) throws ValidationException {
        defaultClimateFacade.deleteAllMatchingFilter(deleteFilters);
    }
}