package com.gbill.createfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gbill.createfinalconsumerbill.mapper.BillItemMapper;
import com.gbill.createfinalconsumerbill.model.BillItem;
import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;

import com.gbill.createfinalconsumerbill.repository.BillItemRepository;

@Service
public class BillItemService implements IBillItemService {

    private final BillItemRepository billItemRepository;

    public BillItemService(BillItemRepository billItemRepository){
        this.billItemRepository = billItemRepository;
    }

    @Override
    public List<BillItemDTO> getAllitem() {
        return billItemRepository.findAll().stream().map(BillItemMapper::toDto).toList();
    }

    @Override
    public Optional<BillItemDTO> getByid(Long id) {
        return billItemRepository.findById(id).map(BillItemMapper::toDto);
    }

    @Override
    public CreateBillItemDTO saveItem(CreateBillItemDTO createBillItemDTO) {
        BillItem billItem = BillItemMapper.toEntity(createBillItemDTO);
        billItemRepository.save(billItem);
        return BillItemMapper.toCreateDto(billItem);
    }

    @Override
    public List<CreateBillItemDTO> saveAllItems(List<CreateBillItemDTO> createBillItemDTOS) {

        List<BillItem> items = createBillItemDTOS.stream().map(BillItemMapper::toEntity).collect(Collectors.toList());
    
        return billItemRepository.saveAll(items).stream().map(BillItemMapper::toCreateDto).collect(Collectors.toList());
    }

}
