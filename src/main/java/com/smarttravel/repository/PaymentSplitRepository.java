package com.smarttravel.repository;

import com.smarttravel.model.PaymentSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentSplitRepository extends JpaRepository<PaymentSplit, Integer> {
    List<PaymentSplit> findByBookingId(Integer bookingId);
    List<PaymentSplit> findByUserId(Integer userId);
    List<PaymentSplit> findByStatus(PaymentSplit.PaymentStatus status);
}
