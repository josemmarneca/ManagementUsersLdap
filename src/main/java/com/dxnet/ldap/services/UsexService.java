package com.dxnet.ldap.services;

import com.dxnet.ldap.database.entities.Usex;
import com.dxnet.ldap.database.repository.UsexRepository;
import com.dxnet.ldap.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsexService {


    @Autowired
    UsexRepository usexRepository;


    public Usex findByNameActive(String userName) throws ResourceNotFoundException {
        Usex usex = this.usexRepository.findByActiveIsTrueAndCn(userName);
        if(usex == null){
            throw new ResourceNotFoundException("Usex not exit with fullname:" + userName);
        }
        return usex;
    }

    public Usex findByDnActive(String dn) throws ResourceNotFoundException {
        Usex usex = this.usexRepository.findByActiveIsTrueAndDn(dn);
        if(usex == null){
            throw new ResourceNotFoundException("Usex not exit with dn:" + dn);
        }
        return usex;
    }

    public Usex save(Usex usex) {
        return this.usexRepository.save(usex);
    }

    public void saveAll(List<Usex> usexList) {
        this.usexRepository.saveAll(usexList);
    }

    public List<Usex> findAllActive() {
        return this.usexRepository.findAllByActiveIsTrue();
    }

    public void delete(Usex user) {
        this.usexRepository.delete(user);
    }
}
