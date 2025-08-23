package com.gbill.getIdBill.service;

import com.gbill.getIdBill.modeldto.ShowBill;

import java.util.Optional;

public interface IBillService {
    Optional<ShowBill> getById(String codeGenerator);
}
