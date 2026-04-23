package com.hotel.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	boolean existsByBookingId(Long bookingId);

	Optional<Payment> findByTransactionId(String transactionId);

	List<Payment> findByBookingIdIn(List<Long> bookingIds);

	@Query("""
		select coalesce(sum(p.amount), 0)
		from Payment p
		where p.status = :status
		""")
	Double sumAmountByStatus(@Param("status") PaymentStatus status);

}
