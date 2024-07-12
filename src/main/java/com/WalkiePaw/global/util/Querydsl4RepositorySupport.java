package com.WalkiePaw.global.util;

import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * pageable.pageSize() + 1 로 리스트를 가져온 뒤 가져온 값이 == pageSize() + 1 보다 작으면 마지막 페이지
     * @return Slice 구현체
     * @param <T> 반환 객체
     */
    protected <T> Slice<T> slice(Pageable pageable, Function<JPAQueryFactory, JPAQuery> sliceQuery) {
        JPAQuery query = (JPAQuery) sliceQuery.apply(getJpaQueryFactory())
                .offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<T> content = createContent(pageable, query);
        boolean hasNext = isHasNext(pageable, content);
        return new SliceImpl<>(content, pageable, hasNext);
    }

    protected static <T> boolean isHasNext(final Pageable pageable, final List<T> content) {
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }
        return hasNext;
    }

    /**
     *
     * @param pageable
     * @param sliceQuery
     * @param transformer
     * @return
     * @param <T>
     * @param <R>
     */
    protected <T, R> Slice<R> slice(Pageable pageable,
                                    Function<JPAQueryFactory, JPAQuery<T>> sliceQuery,
                                    Function<T, R> transformer) {
        JPAQuery<T> query = sliceQuery.apply(getJpaQueryFactory())
                .offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<T> content = createContent(pageable, query);
        List<R> transformedContent = content.stream()
                .map(transformer)
                .toList();
        boolean hasNext = isHasNext(pageable, transformedContent);
        return new SliceImpl<>(transformedContent, pageable, hasNext);
    }


    protected <T> List<T> createContent(final Pageable pageable, final JPAQuery query) {
        List<T> content = null;
        if (pageable.getSort() == null) {
            content = getQuerydsl().applyPagination(sliceWithoutSort(pageable), query).fetch();
        } else {
            content = getQuerydsl().applyPagination(sliceWithSort(pageable), query).fetch();
        }
        return content;
    }

    private static PageRequest sliceWithSort(final Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), sliceSize(pageable), pageable.getSort());
    }

    private static PageRequest sliceWithoutSort(final Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), sliceSize(pageable));
    }

    private static int sliceSize(Pageable pageable) {
        return pageable.getPageSize() + 1;
    }

    protected <T> Page<T> page(Pageable pageable, Function<JPAQueryFactory, JPAQuery> pageQuery) {
        JPAQuery query = pageQuery.apply(getJpaQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }
}

