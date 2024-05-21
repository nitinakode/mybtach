package com.batch2.batch2.all;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionStatus {
    @Id
    private String id;
    private String fileName;
    private String status;
    private int processedRecords;
    private int totalRecords;

}
