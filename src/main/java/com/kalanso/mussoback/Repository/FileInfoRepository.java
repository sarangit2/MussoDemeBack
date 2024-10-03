package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByName(String name);
}
