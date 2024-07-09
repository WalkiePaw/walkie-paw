package com.WalkiePaw.global.util;

import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

/**
 * Querydsl Page Util Class
 * @see
 * org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 */
@Repository
public abstract class Querydsl4RepositorySupport {

    private final Class domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    public Querydsl4RepositorySupport(Class<?> domainClass) {
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected JPAQueryFactory getJpaQueryFactory() {
        return jpaQueryFactory;
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getJpaQueryFactory().selectFrom(from);
    }

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return getJpaQueryFactory().select(expr);
    }

    protected <T> Slice<T> slice(Pageable pageable, Function<JPAQueryFactory, JPAQuery> sliceQuery) {
        JPAQuery query = (JPAQuery) sliceQuery.apply(getJpaQueryFactory())
                .offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<T> content = getQuerydsl().applyPagination(pageable, query).fetch();
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    protected <T> Page<T> page(Pageable pageable, Function<JPAQueryFactory, JPAQuery> pageQuery) {
        JPAQuery query = pageQuery.apply(getJpaQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }
}