package com.dxnet.ldap.database.repository;

import com.dxnet.ldap.database.entities.Usex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsexRepository extends JpaRepository<Usex, Long> {

    Usex findByActiveIsTrueAndCn(String name);

    Usex findByActiveIsTrueAndDn(String name);

    List<Usex> findAllByActiveIsTrue();
}
