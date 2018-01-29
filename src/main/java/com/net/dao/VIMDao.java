package com.net.dao;

import com.net.domain.Vim;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/***
 * Created on 2017/11/11 at 21:24.  
 ***/
@Repository
public class VIMDao {
    @PersistenceContext
    private EntityManager entityManager;

    public VIMDao() {
    }


    public Vim findById(final String id) {
        Assert.notNull(id);
        try {
            return entityManager.find(Vim.class, id);
        } catch (final Exception e) {
            return null;
        }
    }

    public List<Vim> findAll() {
        return findAll(false, 0, 0);
    }


    public List<Vim> findAll(final boolean isPaging, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Vim> cq = cb.createQuery(Vim.class);
        final TypedQuery<Vim> q = entityManager.createQuery(cq);
        if (isPaging) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }


    @Transactional
    public boolean remove(final String VIMId) {
        final Vim vim = findById(VIMId);
        if (vim != null) {
            entityManager.remove(vim);
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public Vim store(final Vim entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void update(final Vim entity) {
        final StringBuilder jpql = new StringBuilder();
        jpql.append("UPDATE Vim b SET b.name='").append(entity.getName());
        jpql.append("' WHERE b.id=").append(entity.getId());
        entityManager.createQuery(jpql.toString()).executeUpdate();
    }

//    @Transactional
//    public String register(final String mid, final String vid) {
//        try {
//            Image mirror = entityManager.find(Image.class, mid);
//            Vim vim = entityManager.find(Vim.class, vid);
//            mirror.getVimSet().add(vim);
//            vim.getMirrorSet().add(mirror);
//            entityManager.persist(mirror);
//            entityManager.persist(vim);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "false";
//        }
//        return "success";
//    }

    @Transactional
    public void save(final Vim entity) {
        entityManager.persist(entity);
    }

}
