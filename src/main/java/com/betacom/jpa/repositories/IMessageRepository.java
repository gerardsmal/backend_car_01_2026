package com.betacom.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.Messaggi;

public interface IMessageRepository extends JpaRepository<Messaggi, String>{

}
