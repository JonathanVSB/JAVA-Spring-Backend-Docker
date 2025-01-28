package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    public void createCenter(Center center) {
        centerRepository.save(center);
    }

    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    public void updateCenter(Long id, Center updatedCenter) {
        Center existingCenter = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found."));

        if (updatedCenter.getName() != null) existingCenter.setName(updatedCenter.getName());
        if (updatedCenter.getCapacity() != null) existingCenter.setCapacity(updatedCenter.getCapacity());
        if (updatedCenter.getStatus() != null) existingCenter.setStatus(updatedCenter.getStatus());
        if (updatedCenter.getCoordinates() != null) existingCenter.setCoordinates(updatedCenter.getCoordinates());
        if (updatedCenter.getMaxCapacity() != null) existingCenter.setMaxCapacity(updatedCenter.getMaxCapacity());

        if (updatedCenter.getCurrentLoad() != null) {
            // Validar que currentLoad no exceda maxCapacity
            if (existingCenter.getMaxCapacity() != null && updatedCenter.getCurrentLoad() > existingCenter.getMaxCapacity()) {
                throw new IllegalArgumentException("Current load cannot exceed maximum capacity.");
            }
            existingCenter.setCurrentLoad(updatedCenter.getCurrentLoad());
        }

        centerRepository.save(existingCenter);
    }


    public void deleteCenter(Long id) {
        if (!centerRepository.existsById(id)) {
            throw new RuntimeException("Center not found.");
        }
        centerRepository.deleteById(id);
    }
}
