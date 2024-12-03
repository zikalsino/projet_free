package com.example.demo.Repository;

import com.example.demo.entity.PageInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageInformationRepository extends JpaRepository<PageInformation, Long> {
}
