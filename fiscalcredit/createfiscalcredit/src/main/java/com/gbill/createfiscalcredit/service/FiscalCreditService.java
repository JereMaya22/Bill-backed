package com.gbill.createfiscalcredit.service;

import com.gbill.createfiscalcredit.clients.GetProductsByIds;
import com.gbill.createfiscalcredit.clients.ValidationService;
import com.gbill.createfiscalcredit.mapper.FiscalCreditMapper;
import com.gbill.createfiscalcredit.model.FiscalCredit;
import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditDTO;
import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditItemDTO;
import com.gbill.createfiscalcredit.modeldto.ShowProductDTO;
import com.gbill.createfiscalcredit.repository.FiscalCreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiscalCreditService implements IFiscalCreditService {

    private final FiscalCreditRepository fiscalCreditRepository;
    private final ValidationService validationService;
    private final GetProductsByIds getProductsByIds;

    /**
     * Crea un nuevo crédito fiscal
     * 1. Valida el token de autenticación
     * 2. Consulta los productos desde el servicio de inventario
     * 3. Convierte el DTO a entidad usando el Mapper
     * 4. Guarda el crédito fiscal en la base de datos
     * 
     * @param token Token de autenticación
     * @param createFiscalCreditDTO DTO con los datos del crédito fiscal
     * @return FiscalCredit creado y guardado
     */
    @Override
    @Transactional
    public FiscalCredit createFiscalCredit(String token, CreateFiscalCreditDTO createFiscalCreditDTO) {
        
        // 1. TEMPORAL: Sin validación de token para pruebas
        // String userEmail = validationService.validateToken(token);
        String userEmail = "test@beckysflorist.com"; // Email temporal para pruebas
        
        // Si el account no viene en el DTO, usar el email del usuario validado
        if (createFiscalCreditDTO.getAccount() == null || createFiscalCreditDTO.getAccount().isEmpty()) {
            createFiscalCreditDTO.setAccount(userEmail);
        }
        
        // 2. Obtener IDs de productos solicitados
        List<Long> productIds = createFiscalCreditDTO.getProducts().stream()
                .map(CreateFiscalCreditItemDTO::getProductId)
                .collect(Collectors.toList());
        
        // 3. Consultar productos desde el servicio de inventario
        List<ShowProductDTO> products = getProductsByIds.getByIds(productIds);
        
        // 4. Crear un mapa para acceso rápido por ID
        Map<Long, ShowProductDTO> productMap = products.stream()
                .collect(Collectors.toMap(ShowProductDTO::getId, product -> product));
        
        // 5. Convertir DTO a entidad (incluye generación de códigos y cálculo de totales)
        FiscalCredit fiscalCredit = FiscalCreditMapper.toEntity(createFiscalCreditDTO, productMap);
        
        // 6. Guardar en base de datos
        return fiscalCreditRepository.save(fiscalCredit);
    }

    /**
     * Obtiene un crédito fiscal por ID
     */
    @Override
    public Optional<FiscalCredit> getFiscalCreditById(Long id) {
        return fiscalCreditRepository.findById(id);
    }

    /**
     * Obtiene un crédito fiscal por código de generación
     */
    @Override
    public Optional<FiscalCredit> getFiscalCreditByGenerationCode(String generationCode) {
        return fiscalCreditRepository.findByGenerationCode(generationCode);
    }

    /**
     * Obtiene un crédito fiscal por número de control
     */
    @Override
    public Optional<FiscalCredit> getFiscalCreditByControlNumber(String controlNumber) {
        return fiscalCreditRepository.findByControlNumber(controlNumber);
    }

    /**
     * Obtiene todos los créditos fiscales
     */
    @Override
    public List<FiscalCredit> getAllFiscalCredits() {
        return fiscalCreditRepository.findAll();
    }
}
