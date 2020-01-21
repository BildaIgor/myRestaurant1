package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.CookConverter;
import myRestaurant.converter.WaiterConverter;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.CookEntity;
import myRestaurant.repository.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    private final WaiterRepository waiterRepository;
    private final CookRepository cookRepository;

    public void createWaiter(WaiterDto waiterDto){
        waiterRepository.save(WaiterConverter.toWaiterEntity(waiterDto));
    }
    public void createCook(CookDto cookDto){
        cookRepository.save(CookConverter.toCookEntity(cookDto));
    }
    public void deleteWaiter(Integer waiterId){
        waiterRepository.delete(waiterRepository.getById(waiterId));
    }
    public void deleteCook(Integer cookId){
        cookRepository.delete(cookRepository.getById(cookId));
    }
}
