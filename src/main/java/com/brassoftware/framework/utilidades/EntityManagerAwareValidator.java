/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import javax.persistence.EntityManager;

/**
 *
 * @author RIBEIRO TRABALHO
 */
public interface EntityManagerAwareValidator {  
     void setEntityManager(EntityManager entityManager); 
} 
