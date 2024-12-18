package com.yeel.socks.service;

import com.opencsv.CSVReader;
import com.yeel.socks.dto.SockDTO;
import com.yeel.socks.model.Sock;
import com.yeel.socks.repo.SockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {
    private final SockRepository sockRepository;

    @Override
    public Sock registerIncome(SockDTO sockDTO) {
        return sockRepository.save(
                sockRepository.findByColorAndPercentage(
                        sockDTO.getColor(),
                        sockDTO.getPercentage()
                ).orElse(
                        new Sock(
                                sockDTO.getColor(),
                                sockDTO.getPercentage()
                        )
                ).increaseCount(
                        sockDTO.getCount()
                )
        );
    }

    @Override
    public Sock registerOutcome(SockDTO sockDTO) {
        try {
            return sockRepository.save(
                    sockRepository.findByColorAndPercentage(
                            sockDTO.getColor(),
                            sockDTO.getPercentage()
                    ).orElseThrow(
                            () -> new IllegalArgumentException("Некорректный формат данных.")
                    ).decreaseCount(
                            sockDTO.getCount()
                    )
            );
        } catch (IllegalArgumentException exception) {
            throw new  IllegalArgumentException("Нехватка носков на складе.");
        }
    }

    @Override
    public Integer getSocksCount(String color, String operator, Integer percentage) {
        return sockRepository.findSocksByCriteria(color, operator, percentage).orElse(0);
    }

    @Override
    public Sock update(Long id, SockDTO sockDTO) {
        return sockRepository.save(
                sockRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ).update(
                        sockDTO.getColor(),
                        sockDTO.getPercentage(),
                        sockDTO.getCount()
                )
        );
    }

    @Override
    public void registerBatch(MultipartFile multipartFile) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                registerIncome(
                        new SockDTO(
                                line[0],
                                Integer.parseInt(line[1]),
                                Integer.parseInt(line[2])
                        )
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибки при обработке файлов. " + e.getMessage());
        }
    }
}
