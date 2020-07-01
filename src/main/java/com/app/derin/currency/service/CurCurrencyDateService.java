package com.app.derin.currency.service;

import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;

import net.bytebuddy.asm.Advice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.derin.currency.domain.CurCurrencyDate}.
 */
@Component
public interface CurCurrencyDateService {

    /**
     * Cron ile gelen Merkez Bankası verilerinin CurCurrencyDate tablosuna eklenmesi
     *
     * @param jsonArray the jsonArray information.
     * @param date the date information.
     *
     */
    void addCronJobRatesToCurCurrencyDate(JSONArray jsonArray, LocalDate date);

    /**
     * Kullanıcı tarafından yeni kur tanımlamasında [getJsonBuyingRates] ve [getJsonSellingRates] jsonArray'lerinin tek bir
     * JsonArray'e dönüşmesi, tüm kurların dolu olduğundan emin olunması, CurCurrencyDate'e eklenmesi
     * Ve CurCurrencyMatrix'e son günün verisinin eklenmesi
     * @param code kullanıcı tarafından tanımlanan kur kodu.
     * @param startDate kullanıcının seçtiği başlangıç tarihi
     * @param endDate kullanıcının seçtiği bitiş tarihi
     */
    void setJsonArrayAndSave( String code, LocalDate startDate, LocalDate endDate) throws IOException, ParseException;


    /**
     * Kullanıcı tarafından yeni kur tanımlamasında seçilen tarih itibari ile gelen SATIŞ kur verileri
     * Gelen XML tipindeki veri JSONArray tipinde dönüyor
     * @param jsonObject kullanıcı tarafından tanımlanan kur kodu.
     * @param resultCurrencyId kullanıcının seçtiği tarihten itibaren olan tarihler
     * @param buyingRate kullanıcının seçtiği tarihten itibaren olan tarihler
     * @param sellingRate kullanıcının seçtiği tarihten itibaren olan tarihler
     *
     */
    CurCurrencyDateDTO insertRatesToCurCurrencyDate(JSONObject jsonObject, Long resultCurrencyId, Double buyingRate, Double sellingRate);


    /**
     * Save a curCurrencyDate.
     *
     * @param curCurrencyDateDTO the entity to save.
     * @return the persisted entity.
     */
    CurCurrencyDateDTO save(CurCurrencyDateDTO curCurrencyDateDTO);

    /**
     * Get all the curCurrencyDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurCurrencyDateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" curCurrencyDate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurCurrencyDateDTO> findOne(Long id);

    /**
     * Delete the "id" curCurrencyDate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
