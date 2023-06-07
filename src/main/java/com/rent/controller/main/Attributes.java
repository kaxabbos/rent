package com.rent.controller.main;

import com.rent.model.Statistics;
import com.rent.model.enums.BodyType;
import com.rent.model.enums.Fuel;
import com.rent.model.enums.Role;
import com.rent.model.enums.Transmission;
import org.springframework.ui.Model;

import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesEnums(Model model) {
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("fuels", Fuel.values());
        model.addAttribute("transmissions", Transmission.values());
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", usersRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesCar(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("car", carsRepo.getReferenceById(id));
    }

    protected void AddAttributesCarsMy(Model model) {
        AddAttributes(model);
        model.addAttribute("cars", getUser().getCars());
    }

    protected void AddAttributesCarAdd(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
    }

    protected void AddAttributesCarEdit(Model model, Long id) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("car", carsRepo.getReferenceById(id));
    }

    protected void AddAttributesCatalog(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("cars", carsRepo.findAllByOrderByFreeDesc());
    }

    protected void AddAttributesCatalogSearch(Model model, BodyType bodyType, Fuel fuel, Transmission transmission) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("bSelect", bodyType);
        model.addAttribute("fSelect", fuel);
        model.addAttribute("tSelect", transmission);
        model.addAttribute("cars", carsRepo.findAllByDescription_BodyTypeAndDescription_FuelAndDescription_TransmissionOrderByFreeDesc(bodyType, fuel, transmission));
    }

    protected void AddAttributesStatistics(Model model) {
        AddAttributes(model);
        List<Statistics> statistics = statisticsRepo.findAll();
        int income = statistics.stream().reduce(0, (i, s) -> i + (s.getDays() * s.getCar().getPrice()), Integer::sum);
        model.addAttribute("statistics", statistics);
        model.addAttribute("income", income);
    }
}
