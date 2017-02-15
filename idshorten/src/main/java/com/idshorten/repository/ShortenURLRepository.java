package com.idshorten.repository;

import com.idshorten.model.ShortenURL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ShortenURLRepository {
    @Value("${idshort.sql.getURL}")
    private String sqlGetURL;

    @Value("${idshort.sql.verifyExistsURL}")
    private String sqlSelectURL;

    @Value("${idshort.sql.getAlias}")
    private String sqlSelectAlias;

    @Value("${idshort.sql.getInformation}")
    private String sqlGetInformations;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Object getURL(String shorten) throws DataAccessException {

        try{
            Query query = entityManager.createNativeQuery(sqlGetURL)
                    .setParameter("param",shorten);
            Object resultList = query.getSingleResult();
            return resultList;
        }catch (NoResultException e){
            return null;
        }
    }

    @Transactional
    public void addShorten(ShortenURL shortenURL){
        entityManager.persist(shortenURL);
    }

    @Transactional
    public String getSqlGetURL(String url){
        try{
            Query query = entityManager.createNativeQuery(sqlSelectURL)
                    .setParameter("param",url);
            Object resultList = query.getSingleResult();
            return resultList.toString();
        }catch (NoResultException e){
            return null;
        }
    }

    @Transactional
    public List<String> getAllAlias() {
        try {
            Query query = entityManager.createNativeQuery(sqlSelectAlias);
            List<String> resultList = query.getResultList();
            return resultList;
        }catch (NoResultException e){
            return null;
        }
    }

    @Transactional
    public Object getInformations(String url) {
        try {
            Query query = entityManager.createNativeQuery(sqlGetInformations)
                    .setParameter("param", url);
            Object result = query.getSingleResult();
            return result;
        }catch (NoResultException e){
            return null;
        }
    }
}
