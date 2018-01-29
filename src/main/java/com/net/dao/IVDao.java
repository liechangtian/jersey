package com.net.dao;

import com.net.domain.Image_VIM;
import com.net.domain.Images;
import com.net.domain.VIMs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

/***
 * Created on 2017/11/11 at 23:20.  
 ***/
@Repository
public class IVDao {
    @PersistenceContext
    private EntityManager entityManager;

    public IVDao() {
    }

    //存储VIM与镜像关系对
    @Transactional
    public Image_VIM store(final Image_VIM entity) {
        Image_VIM image_vim = entityManager.merge(entity);
        return image_vim;
    }

    //删除关系对
    @Transactional
    public boolean remove(final String mid, final String vid) {
        final Image_VIM image_vim = findImageVimPair(mid, vid);
        if (image_vim != null) {
            entityManager.remove(image_vim);
            return true;
        } else {
            return false;
        }
    }

    //返回指定关系对
    public Image_VIM findImageVimPair(final String mid, final String vid) {
        Query query = entityManager.createQuery("select vm from Image_VIM vm  where vm.imageId= ?1 and vm.vimId=?2");
        query.setParameter(1, mid).setParameter(2, vid);
        Image_VIM image_vim = (Image_VIM) query.getSingleResult();  //若未命中返回是否为null?
        return image_vim;
    }

    //返回所有关系对
    public List<Image_VIM> findAll() {
        return findAll(false, 0, 0);
    }

    public List<Image_VIM> findAll(final boolean isPaging, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Image_VIM> cq = cb.createQuery(Image_VIM.class);
        final TypedQuery<Image_VIM> q = entityManager.createQuery(cq);
        if (isPaging) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    //查询包含指定镜像的VIM
    public VIMs findVIMsByMid(final String mid) {
        Query query = entityManager.createQuery("select vm.vimId from Image_VIM vm  where vm.imageId= ?1");
        query.setParameter(1, mid);
        List VidList = query.getResultList();
        List VIMList = new ArrayList();
        if (VidList.size() != 0) {
            Query query2 = entityManager.createQuery("select v from Vim v  where v.id in ?1");
            query2.setParameter(1, VidList);
            VIMList = query2.getResultList();
        }

        return new VIMs(VIMList);
    }

    //查询指定VIM上的镜像
    public Images findMirrorsByVid(final String vid) {
        Query query = entityManager.createQuery("select iv.imageId from Image_VIM iv  where iv.vimId= ?1");
        query.setParameter(1, vid);
        List MIdList = query.getResultList();
        List MirrorList = new ArrayList();
        if (MIdList.size() != 0) {
            Query query2 = entityManager.createQuery("select i from Image i where i.id in ?1");
            query2.setParameter(1, MIdList);
            MirrorList = query2.getResultList();
        }
        return new Images(MirrorList);
    }

}
