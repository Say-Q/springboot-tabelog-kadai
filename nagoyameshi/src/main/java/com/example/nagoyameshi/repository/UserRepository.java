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
	@Query("SELECT COUNT(u) FROM User u WHERE u.createdAt <= :date AND u.role.id <> :roleId")
	long countByCreatedAtLessThanEqualAndRoleNotAdmin(@Param("date") Timestamp date, @Param("roleId") Integer roleId);

	@Query("SELECT COUNT(u) FROM User u WHERE u.createdAt <= :date AND u.role.id = :roleId")
	long countByCreatedAtLessThanEqualAndRoleFree(@Param("date") Timestamp date, @Param("roleId") Integer roleId);

	@Query("SELECT COUNT(u) FROM User u WHERE u.updatedAt <= :date AND u.role.id = :roleId")
	long countByUpdatedAtLessThanEqualAndRolePay(@Param("date") Timestamp date, @Param("roleId") Integer roleId);
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.updatedAt BETWEEN :startDate AND :endDate AND u.role.id <> :roleId")
	long countByUpdatedAtBetweenAndRoleNotAdmin(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Integer roleId);

	@Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate AND u.role.id = :roleId")
	long countByCreatedAtBetweenAndRoleFree(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Integer roleId);

	@Query("SELECT COUNT(u) FROM User u WHERE u.updatedAt BETWEEN :startDate AND :endDate AND u.role.id = :roleId")
	long countByUpdatedAtBetweenAndRolePay(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Integer roleId);

	//CSV出力用
	@Query("SELECT u FROM User u WHERE u.role.id <> :roleId")
	List<User> findByRoleIdNot(Integer roleId);
}
