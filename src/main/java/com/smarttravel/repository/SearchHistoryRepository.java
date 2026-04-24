package com.smarttravel.repository;

import com.smarttravel.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {
    List<SearchHistory> findByUserId(Integer userId);

    @Query("SELECT sh FROM SearchHistory sh WHERE sh.userId = :userId ORDER BY sh.searchDate DESC")
    List<SearchHistory> findUserSearchHistory(@Param("userId") Integer userId);
}
