package com.rent.controller;

import com.rent.controller.main.Attributes;
import com.rent.model.Cars;
import com.rent.model.CarsDescription;
import com.rent.model.Statistics;
import com.rent.model.Users;
import com.rent.model.enums.BodyType;
import com.rent.model.enums.Fuel;
import com.rent.model.enums.Transmission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/cars")
public class CarsCont extends Attributes {

    @GetMapping("/{id}")
    public String Car(Model model, @PathVariable Long id) {
        AddAttributesCar(model, id);
        return "car";
    }

    @GetMapping("/add")
    public String CarAdd(Model model) {
        AddAttributesCarAdd(model);
        return "car_add";
    }

    @GetMapping("/edit/{id}")
    public String CarEdit(Model model, @PathVariable Long id) {
        AddAttributesCarEdit(model, id);
        return "car_edit";
    }

    @GetMapping("/delete/{id}")
    public String CarDelete(@PathVariable Long id) {
        carsRepo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/my")
    public String CarMy(Model model) {
        AddAttributesCarsMy(model);
        return "my_cars";
    }

    @GetMapping("/return/{id}")
    public String CarReturn(@PathVariable Long id) {
        Users user = getUser();
        Cars car = carsRepo.getReferenceById(id);
        car.setFree(true);
        user.removeCar(car);
        usersRepo.save(user);
        return "redirect:/cars/my";
    }

    @PostMapping("/rent/{id}")
    public String CarRent(@PathVariable Long id, @RequestParam int days) {
        Cars car = carsRepo.getReferenceById(id);

        Users user = getUser();
        user.addCar(car);
        usersRepo.save(user);

        car.setFree(false);
        car.getStatistics().setDays(car.getStatistics().getDays() + days);

        carsRepo.save(car);

        return "redirect:/cars/{id}";
    }

    @PostMapping("/add")
    public String CarAddNew(Model model, @RequestParam String name, @RequestParam MultipartFile[] photos, @RequestParam int price, @RequestParam int average, @RequestParam BodyType bodyType, @RequestParam String description, @RequestParam Fuel fuel, @RequestParam Transmission transmission, @RequestParam int capacity) {
        if (photos != null && !Objects.requireNonNull(photos[0].getOriginalFilename()).isEmpty()) {
            try {
                String[] result_photos;
                String result_screenshot;
                String uuidFile = UUID.randomUUID().toString();
                result_photos = new String[photos.length];
                for (int i = 0; i < result_photos.length; i++) {
                    result_screenshot = "cars/" + uuidFile + "_" + photos[i].getOriginalFilename();
                    photos[i].transferTo(new File(uploadImg + "/" + result_screenshot));
                    result_photos[i] = result_screenshot;
                }
                Cars car = new Cars(name, price, true, result_photos);
                car.setStatistics(new Statistics(car));
                car.setDescription(new CarsDescription(bodyType, fuel, transmission, average, description, capacity));
                carsRepo.save(car);
            } catch (Exception e) {
                AddAttributesCarAdd(model);
                model.addAttribute("message", "Ошибка, некорректный данные!");
                return "car_add";
            }
        } else {
            AddAttributesCarAdd(model);
            model.addAttribute("message", "Ошибка, некорректный данные!");
            return "car_add";
        }
        return "redirect:/cars/add";
    }

    @PostMapping("/edit/{id}")
    public String CarEditOld(Model model, @RequestParam String name, @RequestParam MultipartFile[] photos, @RequestParam int price, @RequestParam int average, @RequestParam BodyType bodyType, @RequestParam String description, @RequestParam Fuel fuel, @RequestParam Transmission transmission, @RequestParam int capacity, @PathVariable Long id) {
        Cars car = carsRepo.getReferenceById(id);
        String[] result_photos;
        if (photos != null && !Objects.requireNonNull(photos[0].getOriginalFilename()).isEmpty()) {
            try {
                String result_photo;
                String uuidFile = UUID.randomUUID().toString();
                result_photos = new String[photos.length];
                for (int i = 0; i < result_photos.length; i++) {
                    result_photo = "cars/" + uuidFile + "_" + photos[i].getOriginalFilename();
                    photos[i].transferTo(new File(uploadImg + "/" + result_photo));
                    result_photos[i] = result_photo;
                }
                car.setPhotos(result_photos);
            } catch (Exception e) {
                AddAttributesCarAdd(model);
                model.addAttribute("message", "Ошибка, некорректный данные!");
                return "car_edit";
            }
        }

        car.setName(name);
        car.setPrice(price);
        carsRepo.save(car);

        CarsDescription carsDescription = car.getDescription();
        carsDescription.setDescription(description);
        carsDescription.setFuel(fuel);
        carsDescription.setBodyType(bodyType);
        carsDescription.setTransmission(transmission);
        carsDescription.setAverage(average);
        carsDescription.setCapacity(capacity);
        carsDescriptionRepo.save(carsDescription);

        return "redirect:/";
    }
}
