package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.BlobStorage.BlobStorageService;
import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.service.CityService;
import com.proyect.mstemplateep.service.ServiceService;
import com.proyect.mstemplateep.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private final ServiceService serviceService;

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Autowired
    private final CityService cityService;

    @Autowired
    private BlobStorageService blobStorageService;

    @Autowired
    public ServiceController(
            ServiceService serviceService, ServiceTypeService serviceTypeService,
            CityService cityService, BlobStorageService blobStorageService) {
        this.serviceService = serviceService;
        this.serviceTypeService = serviceTypeService;
        this.cityService = cityService;
        this.blobStorageService = blobStorageService;
    }

    @GetMapping
    public ResponseEntity<Page<ServiceModel>> getAllServices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        Page<ServiceModel> services = serviceService.getAllServices(page, size);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/ServiceType/{serviceTypeId}")
    public ResponseEntity<Page<ServiceModel>> getAllServiceTypes(
            @PathVariable Integer serviceTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        ServiceType serviceType = serviceTypeService.getServiceTypeById(serviceTypeId).orElseThrow();

        Page<ServiceModel> services = serviceService.findByServiceType(page, size,serviceType);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable Integer id) {
        Optional<ServiceModel> service = serviceService.findById(id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceModel> createService(@RequestBody ServiceModel serviceModel) {
        try {
            // Mapea los IDs de los ServiceTypes a objetos ServiceType
            Set<ServiceType> serviceTypes = serviceModel.getServiceType().stream()
                    .map(serviceType -> serviceTypeService.getServiceTypeById(serviceType.getId())  // Aquí usamos el ID directamente
                            .orElseThrow(() -> new RuntimeException("ServiceType not found")))
                    .collect(Collectors.toSet());  // Usamos collect(toSet()) para evitar duplicados

            Set<CityModel> cityModels = serviceModel.getCityModel().stream()
                    .map(cityType -> cityService.getCityById(cityType.getId())  // Aquí usamos el ID directamente
                            .orElseThrow(() -> new RuntimeException("City not found")))
                    .collect(Collectors.toSet());  // Usamos collect(toSet()) para evitar duplicados


            // Asocia los ServiceTypes al ServiceModel
            serviceModel.setServiceType(serviceTypes);
            serviceModel.setCityModel(cityModels);
            serviceModel.setURL_Img(serviceModel.getURL_Img());

            // Guarda el ServiceModel
            ServiceModel savedService = serviceService.saveServiceModel(serviceModel);

            return ResponseEntity.ok(savedService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/Img")
    public ResponseEntity<ServiceModel> createServiceImg(@RequestBody ServiceModel serviceModel, Set<MultipartFile> files) {

        ServiceModel savedService = createService(serviceModel).getBody();

        if(savedService == null) {
            return ResponseEntity.badRequest().build();
        }

        Set<String> urls = savedService.getURL_Img();

        int count = 0;
        long time = Instant.now().getEpochSecond();

        for(MultipartFile file : files){

            String extension = getExtension(file.getName());

            String imageUrl = "service/" + savedService.getId() + "/" + time + "_" + count + extension;
            count++;
            urls.add( blobStorageService.uploadfile(imageUrl, file) );
        }

        savedService.setURL_Img(urls);
        serviceService.saveServiceModel(savedService);

        return ResponseEntity.ok(savedService);
    }

    @PostMapping(value = "Img/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ServiceModel> updateServiceImg(@PathVariable Integer id, @RequestParam("files") List<MultipartFile> files) {
        Optional<ServiceModel> service = serviceService.findById(id);

        if(!service.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        int count = 0;
        long time = Instant.now().getEpochSecond();

        Set<String> urls = service.get().getURL_Img();


        for(MultipartFile file : files){

            String extension = getExtension(file.getOriginalFilename());

            String imageUrl = "service/" + service.get().getId() + "/" + time + "_" + count + extension;
            count++;
            urls.add( blobStorageService.uploadfile(imageUrl, file) );
        }

        service.get().setURL_Img(urls);
        serviceService.saveServiceModel(service.get());

        return ResponseEntity.ok(service.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable Integer id) {
        serviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex == -1) return "";
        return filename.substring(lastIndex);
    }
}
