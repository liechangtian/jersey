package com.net.dao;

import com.net.domain.Package;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015 XiaoMi Inc. All Rights Reserved.
 * Authors: Wangshuai <wangshuai16@xiaomi.com> .
 * Date: 18-1-29.
 */
@Repository
public class PackageDao {
    @PersistenceContext
    private EntityManager entityManager;

    public PackageDao() {
    }

    public Package findById(final String id) {
        Assert.notNull(id);
        try {
            return entityManager.find(Package.class, id);
        } catch (final Exception e) {
            return null;
        }
    }

    public List<Package> findAll() {
        return findAll(false, 0, 0);
    }


    public List<Package> findAll(final boolean isPaging, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Package> cq = cb.createQuery(Package.class);
        final TypedQuery<Package> q = entityManager.createQuery(cq);
        if (isPaging) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }


    // 写入数据库
    @Transactional
    public Package store(final Package entity) {
        return entityManager.merge(entity);
    }

    // 更新就绪状态
    @Transactional
    public void setIsReady(final String packageId, final Boolean isReady) {
        Query query=entityManager.createQuery("UPDATE Package p SET p.isReady=?1 WHERE p.id=?2");
        query.setParameter(1,isReady);
        query.setParameter(2,packageId);
        query.executeUpdate();
        entityManager.flush();
    }

    // 更新激活状态
    @Transactional
    public void setIsActive(final String packageId,final Boolean isActive) {
        Query query=entityManager.createQuery("UPDATE Package p SET p.isActive=?1 WHERE p.id=?2");
        query.setParameter(1,isActive);
        query.setParameter(2,packageId);
        query.executeUpdate();
        entityManager.flush();
    }


    //删除NFVO上package注册信息及文件
    @Transactional
    public boolean remove(final String PackageId) {
        final Package aPackage = findById(PackageId);
        if (aPackage != null) {
            File packageFile = new File(aPackage.getPosition());
            packageFile.delete();
            entityManager.remove(aPackage);
            return true;
        } else {
            return false;
        }
    }


}
