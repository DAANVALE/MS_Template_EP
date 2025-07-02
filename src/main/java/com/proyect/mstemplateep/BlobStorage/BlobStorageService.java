package com.proyect.mstemplateep.BlobStorage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BlobStorageService{


    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public BlobStorageService() {
    }

    public String uploadfile(String blobName, MultipartFile file) {

        try {

            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();

        }catch (Exception e){
            throw new RuntimeException("Error al subir imagen: " + blobName, e);
        }
    }
}
