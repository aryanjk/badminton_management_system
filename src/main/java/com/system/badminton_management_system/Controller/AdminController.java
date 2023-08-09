package com.system.badminton_management_system.Controller;

import com.system.badminton_management_system.Pojo.BookingPojo;
import com.system.badminton_management_system.Pojo.BadmintonPojo;
import com.system.badminton_management_system.Service.BookingService;
import com.system.badminton_management_system.Service.ContactService;
import com.system.badminton_management_system.Service.BadmintonService;
import com.system.badminton_management_system.Service.UserService;
import com.system.badminton_management_system.entity.Booking;
import com.system.badminton_management_system.entity.Contact;
import com.system.badminton_management_system.entity.Badminton;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BadmintonService badmintonService;
    private final BookingService bookingService;
    private final ContactService contactService;

    @GetMapping("/dashboard")
    public String fetchAllbooking(Model model){
        List<Booking> adminbooking = bookingService.fetchAll();
        model.addAttribute("book", adminbooking.stream().map(booking ->
                Booking.builder()
                        .bookId(booking.getBookId())
                        .date(booking.getDate())
                        .starting(booking.getStarting())
                        .user(booking.getUser())
                        .badminton(booking.getBadminton())
                        .email(booking.getEmail())
                        .build()
        ));

        model.addAttribute("books", new BookingPojo());



        return "dashboard";
    }

    @GetMapping("/contact")
    public String createcontact(Model model) {
        List<Contact> admincontact = contactService.fetchAll();
        model.addAttribute("contact", admincontact.stream().map(contact ->
                Contact.builder()
                        .contactId(contact.getContactId())
                        .contactname(contact.getContactname())
                        .contactemail(contact.getContactemail())
                        .contactsubject(contact.getContactsubject())
                        .contactmessage(contact.getContactmessage())
                        .build()
        ));
        return "viewreview";
    }

    @GetMapping("/view")
    public String fetchAllBadminton(Model model){
        List<Badminton> adminbadminton = badmintonService.fetchAll();
        model.addAttribute("badmintons", adminbadminton.stream().map(badminton ->
                Badminton.builder()
                        .badminton_Id(badminton.getBadminton_Id())
                        .badmintonname(badminton.getBadmintonname())
                        .badmintonprice(badminton.getBadmintonprice())
                        .badmintoncontact(badminton.getBadmintoncontact())
                        .badmintonlocation(badminton.getBadmintonlocation())
                        .Description(badminton.getDescription())
                        .imageBase64(getImageBase64(badminton.getBadmintonimage()))
                        .image1Base64(getImageBase64(badminton.getBadmintonimage1()))
                        .image2Base64(getImageBase64(badminton.getBadmintonimage2()))
                        .build()
        ));
        return "viewbadminton";
    }


    @GetMapping("/del/{id}")
    public String deletereview(@PathVariable("id") Integer id) {
        contactService.deleteById(id);
        return "redirect:/admin/dashboard";
    }


    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/images/";
        File file = new File(filePath + fileName);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

//    @GetMapping("/review")
//    public String review() {
//
//        return "viewreview";
//    }
    @GetMapping("/report")
    public String report() {

        return "report";
    }

    @GetMapping("/product/{id}")
    public String getBadmintonProfiile(@PathVariable("id") Integer id, Model model ){
        Badminton badminton = badmintonService.fetchById(id);
        model.addAttribute("badmintons", new BadmintonPojo(badminton));

        model.addAttribute("clickedbadminton", badminton);
        return "editbadminton";
    }
    @GetMapping("/edit/{id}")
    public String editbadminton(@PathVariable("id") Integer id, Model model){
        Badminton badminton =badmintonService.fetchById(id);
        model.addAttribute("clickedbadminton", new BadmintonPojo(badminton));
        return "redirect:/admin/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        badmintonService.deleteById(id);
        return "redirect:/admin/view";
    }


    @PostMapping("/changepassword")
    public String changepassword(@RequestParam("email") String email, Model model, @Valid BookingPojo bookingPojo){
        bookingService.processPasswordResetRequest(bookingPojo.getEmail());
        model.addAttribute("message","Your new password has been sent to your email Please " +
                "check your inbox");
        return "redirect:/admin/report";
    }






}