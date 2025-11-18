package com.api.dispute.repository;

import com.api.dispute.model.Dispute;
import com.api.dispute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {
    List<Dispute> findByUser(User user);
}
