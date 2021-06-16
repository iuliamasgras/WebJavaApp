package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.LocationUpdateDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable final Long id) throws BusinessException
    {
        final CustomUserDetails userPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(locationService.findLocationById(userPrincipal,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocationById(@PathVariable final Long id) throws BusinessException {
        final CustomUserDetails userPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        locationService.deleteById(userPrincipal, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocationById(@PathVariable("id") final Long locationId, @RequestBody final LocationUpdateDTO updatedInfo) throws BusinessException {
        final CustomUserDetails userPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(locationService.updateById(userPrincipal, locationId, updatedInfo));
    }

    @PostMapping("/register")
    public ResponseEntity<Location> registerLocation(@RequestBody final Location location) throws BusinessException {
        final CustomUserDetails userPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok( locationService.registerLocation(userPrincipal, location));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getLocations(@RequestParam("id") final Long userId, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date endDate) {
        return ResponseEntity.ok(locationService.getAllLocations(userId, startDate, endDate));
    }
}
