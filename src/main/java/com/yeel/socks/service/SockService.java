package com.yeel.socks.service;

import com.yeel.socks.dto.SockDTO;
import com.yeel.socks.model.Sock;
import org.springframework.web.multipart.MultipartFile;

public interface SockService {
    Sock registerIncome(SockDTO sockDTO);

    Sock registerOutcome(SockDTO sockDTO);

    Integer getSocksCount(String color, String operator, Integer cottonPercentage);

    Sock update(Long id, SockDTO sockDTO);

    void registerBatch(MultipartFile multipartFile);
}
