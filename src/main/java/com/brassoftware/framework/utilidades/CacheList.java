/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RIBEIRO TRABALHO
 */
public class CacheList {
    private static final Map<String,List> cache;
    static{
        cache = new HashMap<>();
    }
    
    public static List getList(String nomeLista){
        return cache.get(nomeLista);
    }
    public static List putList(String nomeLista,List lista){
        return cache.put(nomeLista,lista);
    }
}
