package com.example.tukgraduation.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tukgraduation.member.domain.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);
}
