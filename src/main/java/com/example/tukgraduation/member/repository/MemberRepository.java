package com.example.tukgraduation.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tukgraduation.member.domain.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
}
