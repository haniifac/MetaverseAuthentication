package org.ukdw.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukdw.authservice.entity.ResourceEntity;
import org.ukdw.authservice.service.ResourceService;

import java.util.List;

@RestController()
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    // Create a new resource
    @PostMapping
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    public ResponseEntity<ResourceEntity> createResource(@RequestBody ResourceEntity resourceEntity) {
        ResourceEntity createdResource = resourceService.createResource(resourceEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    // Get all resources
    @GetMapping
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    public ResponseEntity<List<ResourceEntity>> getAllResources() {
        List<ResourceEntity> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    // Get resource by ID
    @GetMapping("/{id}")
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    public ResponseEntity<ResourceEntity> getResourceById(@PathVariable Long id) {
        ResourceEntity resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    // Update a resource
    /*@PutMapping("/{id}")
    public ResponseEntity<ResourceEntity> updateResource(
            @PathVariable Long id,
            @RequestBody ResourceEntity resourceEntity) {
        ResourceEntity updatedResource = resourceService.updateResource(id, resourceEntity);
        return ResponseEntity.ok(updatedResource);
    }*/

    // Delete resource by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    public ResponseEntity<Void> deleteResourceById(@PathVariable Long id) {
        resourceService.deleteResourceById(id);
        return ResponseEntity.noContent().build();
    }

    /*// Delete resource by bitmask
    @DeleteMapping("/bitmask/{bitmask}")
    public ResponseEntity<Void> deleteResourceByBitmask(@PathVariable Long bitmask) {
        resourceService.deleteResourceByBitmask(bitmask);
        return ResponseEntity.noContent().build();
    }

    // Delete resource by name
    @DeleteMapping("/name/{resourceName}")
    public ResponseEntity<Void> deleteResourceByName(@PathVariable String resourceName) {
        resourceService.deleteResourceByName(resourceName);
        return ResponseEntity.noContent().build();
    }*/

}
