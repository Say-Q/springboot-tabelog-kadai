package com.example.nagoyameshi.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findBymail(String mail);

	@Query("SELECT u FROM User u WHERE u.name LIKE :keyword OR u.furigana LIKE :keyword")
	Page<User> findByNameLikeOrFuriganaLike(@Param("keyword") String keyword, Pageable pageable);

	public Page<User> findByMailLike(String mail, Pageable pageable);

	//集計画面で使用
    long countByCreatedAtLessThanEqualAndRole_IdNot(Timestamp date, Integer roleId);
    long countByCreatedAtLessThanEqualAndRole_Id(Timestamp date, Integer roleId);
    long countByUpdatedAtLessThanEqualAndRole_Id(Timestamp date, Integer roleId);
    
    long countByUpdatedAtBetweenAndRole_IdNot(Timestamp startDate, Timestamp endDate, Integer roleId);
    long countByCreatedAtBetweenAndRole_Id(Timestamp startDate, Timestamp endDate, Integer roleId);
    long countByUpdatedAtBetweenAndRole_Id(Timestamp startDate, Timestamp endDate, Integer roleId);

    // CSV出力用
    List<User> findByRole_IdNot(Integer roleId);	
	
}
