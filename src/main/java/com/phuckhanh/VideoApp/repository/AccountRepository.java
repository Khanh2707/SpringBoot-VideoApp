package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Account;
import com.phuckhanh.VideoApp.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findAccountByChannel_NameUnique(String nameUniqueChannel);

    @Query("SELECT a FROM Account a JOIN a.roles r WHERE r.idRole = :idRole")
    Page<Account> findAllByIdRole(Integer idRole, Pageable pageable);

    @Query("SELECT a FROM Account a " +
            "LEFT JOIN a.channel c " +
            "LEFT JOIN Video v ON v.channel.idChannel = c.idChannel " +
            "WHERE (:idRole = 0 OR EXISTS (SELECT 1 FROM a.roles r WHERE r.idRole = :idRole)) " +
            "GROUP BY a.idAccount " +
            "ORDER BY COUNT(v) DESC")
    Page<Account> findAllOrderByVideoCountDesc(
            Pageable pageable,
            @Param("idRole") Integer idRole
    );

    @Query("SELECT a FROM Account a " +
            "LEFT JOIN a.channel c " +
            "LEFT JOIN Video v ON v.channel.idChannel = c.idChannel " +
            "WHERE (:idRole = 0 OR EXISTS (SELECT 1 FROM a.roles r WHERE r.idRole = :idRole)) " +
            "GROUP BY a.idAccount " +
            "ORDER BY COUNT(v) ASC")
    Page<Account> findAllOrderByVideoCountAsc(
            Pageable pageable,
            @Param("idRole") Integer idRole
    );

    @Query("SELECT a FROM Account a WHERE LOWER(a.channel.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Account> findByAccountChannelNameContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT a FROM Account a JOIN a.roles r WHERE LOWER(a.channel.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND r.idRole = :idRole")
    Page<Account> findByAccountChannelNameContainingIgnoreCaseAndIdRole(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idRole") Integer idRole
    );

    @Query("SELECT a FROM Account a " +
            "LEFT JOIN a.channel c " +
            "LEFT JOIN Video v ON v.channel.idChannel = c.idChannel " +
            "WHERE LOWER(a.channel.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idRole = 0 OR EXISTS (SELECT 1 FROM a.roles r WHERE r.idRole = :idRole)) " +
            "GROUP BY a.idAccount " +
            "ORDER BY COUNT(v) ASC")
    Page<Account> findAllOrderByVideoCountAscAndAccountChannelNameContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idRole") Integer idRole
    );

    @Query("SELECT a FROM Account a " +
            "LEFT JOIN a.channel c " +
            "LEFT JOIN Video v ON v.channel.idChannel = c.idChannel " +
            "WHERE LOWER(a.channel.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idRole = 0 OR EXISTS (SELECT 1 FROM a.roles r WHERE r.idRole = :idRole)) " +
            "GROUP BY a.idAccount " +
            "ORDER BY COUNT(v) DESC")
    Page<Account> findAllOrderByVideoCountDescAndAccountChannelNameContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idRole") Integer idRole
    );

    @Transactional
    void deleteAccountByUsername(String username);
}
