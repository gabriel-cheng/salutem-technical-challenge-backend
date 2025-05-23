package com.example.demo.domain.hamburger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HamburgerRepository extends JpaRepository<Hamburger, String> {

    boolean existsByCode(String code);

    boolean existsByCodeAndHamburgerIdNot(String code, String hamburgerId);

}
