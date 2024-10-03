package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.FileInfo;
import com.kalanso.mussoback.Repository.FileInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FileInfoService {

    @Autowired
    private FileInfoRepository fileInfoRepository;


    private FilesStorageService filesStorageService;

    @Transactional(Transactional.TxType.REQUIRED)
    public FileInfo creerFileInfo(MultipartFile file) {
        // Vérifier si le fichier existe déjà
        FileInfo existingFileInfo = fileInfoRepository.findByName(file.getOriginalFilename());
        if (existingFileInfo != null) {
            // Si le fichier existe déjà, retourner l'info existante
            return existingFileInfo;
        }

        // Sinon, sauvegarder le nouveau fichier
        String fileUrl = filesStorageService.save(file); // Obtenir l'URL du fichier
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setUrl(fileUrl); // Définir l'URL du fichier
        return this.fileInfoRepository.save(fileInfo);
    }

}
