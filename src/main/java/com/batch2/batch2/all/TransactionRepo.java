package com.batch2.batch2.all;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionStatus,String> {
//    @Query(value = "Select * from transaction_repo where file_name=:1",nativeQuery = true)
//    List<TransactionStatus>findByFileName(String fileName);
}
