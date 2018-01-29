package com.net.dao;

import com.net.domain.Image;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.util.List;

/***
 * Created on 2017/11/7 at 21:57.  
 ***/
@Repository
public class ImageDao {
    @PersistenceContext
    private EntityManager entityManager;

    public ImageDao() {
    }


    public Image findById(final String id) {
        Assert.notNull(id);
        try {
            return entityManager.find(Image.class, id);
        } catch (final Exception e) {
            return null;
        }
    }

    public List<Image> findAll() {
        return findAll(false, 0, 0);
    }


    public List<Image> findAll(final boolean isPaging, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Image> cq = cb.createQuery(Image.class);
        final TypedQuery<Image> q = entityManager.createQuery(cq);
        if (isPaging) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

//删除NFVO上镜像注册信息及文件
    @Transactional
    public boolean remove(final String MirrorId) {
        final Image image = findById(MirrorId);
        if (image != null) {
            File imageFile=new File(image.getPosition());
            imageFile.delete();
            entityManager.remove(image);
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public Image store(final Image entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void update(final Image entity) {
        final StringBuilder jpql = new StringBuilder();
        jpql.append("UPDATE Image b SET b.name='").append(entity.getName());
        jpql.append("', b.position='").append(entity.getPosition());
        jpql.append("' WHERE b.id=").append(entity.getId());
        entityManager.createQuery(jpql.toString()).executeUpdate();
    }

//    @Transactional
//    public String register(final String mid, final String vid) {
//        try {
//            Image mirror = entityManager.find(Image.class, mid);
//            mirror.addVIM(entityManager.getReference(Vim.class, vid));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "false";
//        }
//        return "success";
//    }

    @Transactional
    public void save(final Image entity) {
        entityManager.persist(entity);
    }

}
