package com.app.derin.currency.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.domain.*; // for static metamodels
import com.app.derin.currency.repository.CurCurrencyDateRepository;
import com.app.derin.currency.service.dto.CurCurrencyDateCriteria;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;

/**
 * Service for executing complex queries for {@link CurCurrencyDate} entities in the database.
 * The main input is a {@link CurCurrencyDateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurCurrencyDateDTO} or a {@link Page} of {@link CurCurrencyDateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurCurrencyDateQueryService extends QueryService<CurCurrencyDate> {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateQueryService.class);

    private final CurCurrencyDateRepository curCurrencyDateRepository;

    private final CurCurrencyDateMapper curCurrencyDateMapper;

    public CurCurrencyDateQueryService(CurCurrencyDateRepository curCurrencyDateRepository, CurCurrencyDateMapper curCurrencyDateMapper) {
        this.curCurrencyDateRepository = curCurrencyDateRepository;
        this.curCurrencyDateMapper = curCurrencyDateMapper;
    }

    /**
     * Return a {@link List} of {@link CurCurrencyDateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurCurrencyDateDTO> findByCriteria(CurCurrencyDateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CurCurrencyDate> specification = createSpecification(criteria);
        return curCurrencyDateMapper.toDto(curCurrencyDateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurCurrencyDateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurCurrencyDateDTO> findByCriteria(CurCurrencyDateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CurCurrencyDate> specification = createSpecification(criteria);
        return curCurrencyDateRepository.findAll(specification, page)
            .map(curCurrencyDateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CurCurrencyDateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CurCurrencyDate> specification = createSpecification(criteria);
        return curCurrencyDateRepository.count(specification);
    }

    /**
     * Function to convert {@link CurCurrencyDateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CurCurrencyDate> createSpecification(CurCurrencyDateCriteria criteria) {
        Specification<CurCurrencyDate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CurCurrencyDate_.id));
            }
            if (criteria.getCurrencyDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencyDate(), CurCurrencyDate_.currencyDate));
            }
            if (criteria.getSourceUnitValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSourceUnitValue(), CurCurrencyDate_.sourceUnitValue));
            }
            if (criteria.getResultUnitValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResultUnitValue(), CurCurrencyDate_.resultUnitValue));
            }
            if (criteria.getBuyingRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuyingRate(), CurCurrencyDate_.buyingRate));
            }
            if (criteria.getSellingRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellingRate(), CurCurrencyDate_.sellingRate));
            }
            if (criteria.getEffectiveBuyingRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectiveBuyingRate(), CurCurrencyDate_.effectiveBuyingRate));
            }
            if (criteria.getEffectiveSellingRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectiveSellingRate(), CurCurrencyDate_.effectiveSellingRate));
            }
            if (criteria.getIsService() != null) {
                specification = specification.and(buildSpecification(criteria.getIsService(), CurCurrencyDate_.isService));
            }
            if (criteria.getSourceCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceCurrencyId(),
                    root -> root.join(CurCurrencyDate_.sourceCurrency, JoinType.LEFT).get(CurCurrencies_.id)));
            }
            if (criteria.getResultCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultCurrencyId(),
                    root -> root.join(CurCurrencyDate_.resultCurrency, JoinType.LEFT).get(CurCurrencies_.id)));
            }
        }
        return specification;
    }
}
