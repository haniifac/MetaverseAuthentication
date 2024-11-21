package org.ukdw.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.entity.ResourceEntity;
import org.ukdw.authservice.repository.ResourceRepository;
import org.ukdw.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public List<ResourceEntity> getAllResources(){
        return resourceRepository.findAll();
    }

    public ResourceEntity getResourceById(Long resourceId){
        Optional<ResourceEntity> resourceOpt = resourceRepository.findById(resourceId);
        if(resourceOpt.isEmpty()){
            throw new ResourceNotFoundException("Resource id: "+ resourceId + " not found");
        }

        return resourceOpt.get();
    }

    /*public ResourceEntity getResourceByName(String resourceName){
        Optional<ResourceEntity> resourceOpt = resourceRepository.findByResourceName(resourceName);
        if(resourceOpt.isEmpty()){
            throw new ResourceNotFoundException("Resource: "+ resourceName + " not found");
        }

        return resourceOpt.get();
    }*/

    public ResourceEntity getResourceByBitmask(Long resourceBitmask){
        Optional<ResourceEntity> resourceOpt = resourceRepository.findByResourceBitmask(resourceBitmask);
        if(resourceOpt.isEmpty()){
            throw new ResourceNotFoundException("Resource bitmask: "+ resourceBitmask + " not found");
        }

        return resourceOpt.get();
    }

    public ResourceEntity createResource(ResourceEntity resource){
        Long resBitmask = calculateBitmask(resource.getResourceBitmask());
        resource.setResourceBitmask(resBitmask);
        return resourceRepository.save(resource);
    }

    public boolean deleteResourceById(Long resourceId){
        resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: " + resourceId + " not found"));

        resourceRepository.deleteById(resourceId);
        return true;
    }

    /*public boolean deleteResourceByBitmask(Long bitshift){
        Long bitmask = calculateBitmask(bitshift);

        resourceRepository.findByResourceBitmask(bitmask)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with bitmask: " + bitmask + " not found"));

//        resourceRepository.deleteByResourceBitmask(bitmask);
        return true;
    }*/

    /*public boolean deleteResourceByName(String resourceName){
        resourceRepository.findByResourceName(resourceName)
                .orElseThrow(() -> new ResourceNotFoundException("Resource name: " + resourceName + " not found"));

        resourceRepository.deleteByResourceName(resourceName);
        return true;
    }*/

    private Long calculateBitmask(Long bitmaskShift) {
        return (long) Math.pow(2, bitmaskShift);
    }

    /**
     * Load resources based on the permission bitmask dynamically from the database.
     * @param permission The bitmask representing the permissions.
     * @return A map of permission values to their respective names from the database.
     */
    public Map<Long, String> loadResourceNames(long permission) {
        List<ResourceEntity> allResources = resourceRepository.findAll();
        Map<Long, String> resources = new TreeMap<>();
        for (ResourceEntity resource : allResources) {
            long resourceBitmask = resource.getResourceBitmask();
            if ((permission & resourceBitmask) == resourceBitmask) {
                resources.put(resourceBitmask, resource.getResourceName());
            }
        }
        return resources;
    }

}
