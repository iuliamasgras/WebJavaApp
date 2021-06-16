package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.LocationUpdateDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.model.security.Role;
import org.scd.repository.LocationRepository;
import org.scd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Location registerLocation(CustomUserDetails userDetails, Location location) throws BusinessException {
        if (Objects.isNull(location)) {
            throw new BusinessException(401, "Body null!");
        }
        if (Objects.isNull(location.getLatitude())) {
            throw new BusinessException(400, "Latitude can not be null!");
        }
        if (Objects.isNull(location.getLongitude())) {
            throw new BusinessException(400, "Longitude can not be null!");
        }

        final Location insertLocation = new Location();
        insertLocation.setDate(new Date());
        insertLocation.setLongitude(location.getLongitude());
        insertLocation.setLatitude(location.getLatitude());
        insertLocation.setUser(userDetails.getUser());
        locationRepository.save(insertLocation);
        return insertLocation;
    }


    @Override
    public Location findLocationById(CustomUserDetails userPrincipal, Long id) throws BusinessException {
        if (Objects.isNull(id)) {
            throw new BusinessException(400, "Id can not be null!");
        }
        Location location = locationRepository.findById(id).orElse(null);
        if (location == null) {
            throw new BusinessException(404, "Location not found");
        } else if (!((location.getUser().getId() == userPrincipal.getUser().getId()) || userPrincipal.getUser().getRoles().contains(new Role((long) 1, "ADMIN")))) {
            throw new BusinessException(400, "Location not found");
        } else {
            return location;
        }
    }

    @Override
    public Location updateById(CustomUserDetails userPrincipal, Long id, LocationUpdateDTO updatedInfo) throws BusinessException {
        Location updateLocation = findLocationById(userPrincipal, id);
        updateLocation.setLatitude(updatedInfo.getLatitude());
        updateLocation.setLongitude(updatedInfo.getLongitude());
        return updateLocation;
    }

    @Override
    public void deleteById(CustomUserDetails userPrincipal, Long id) throws BusinessException {
        if (findLocationById(userPrincipal, id) != null) {
            locationRepository.deleteById(id);
        }
    }

    @Override
    public List<Location> getAllLocations(Long userId, Date startDate, Date endDate) {
        startDate.setHours(00);
        startDate.setMinutes(00);
        startDate.setSeconds(00);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        List<Location> allLocations = (List<Location>) locationRepository.getLocations(startDate, endDate, userId);
        return allLocations;

    }
}
