package com.system.badminton_management_system.Service.Impl;

import com.system.badminton_management_system.Pojo.BookingPojo;
import com.system.badminton_management_system.Repo.BookingRepo;
import com.system.badminton_management_system.Repo.BadmintonRepo;
import com.system.badminton_management_system.Repo.UserRepo;
import com.system.badminton_management_system.Service.BookingService;
import com.system.badminton_management_system.entity.Booking;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final BadmintonRepo badmintonRepo;
    private final JavaMailSender mailSender;
    private final ThreadPoolTaskExecutor taskExecutor;


    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;

    @Override
    public BookingPojo saveOrder(BookingPojo bookingPojo) {
        Booking booking = new Booking();
        if(bookingPojo.getBookId()!=null){
            booking.setBookId(booking.getBookId());
        }

        booking.setBookId(bookingPojo.getBookId());
        booking.setBadminton(badmintonRepo.findById(bookingPojo.getFid()).orElseThrow());
        booking.setUser(userRepo.findById(bookingPojo.getId()).orElseThrow());
        booking.setDate(Date.valueOf(bookingPojo.getDate()));
        booking.setStarting(bookingPojo.getStarting());
        booking.setEmail(bookingPojo.getEmail());
        bookingRepo.save(booking);
        return new BookingPojo(booking);


    }

    public List<Booking> findAllInList(List<Booking> list) {
        Stream<Booking> allBooking = list.stream().map(booking ->
                Booking.builder()
                        .bookId(booking.getBookId())
                        .date(booking.getDate())
                        .starting(booking.getStarting())
                        .user(booking.getUser())
                        .badminton(booking.getBadminton())
                        .build());
        list = allBooking.toList();
        return list;
    }

    @Override
    public List<Booking> fetchAll() {
        return this.bookingRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        bookingRepo.deleteById(id);

    }

    @Override
    public Booking fetchById(Integer id) {
        Booking application= bookingRepo.findById(id).orElseThrow(()->new RuntimeException("not found application"));
        application =Booking.builder()
                .bookId(application.getBookId())
                .user(application.getUser())
                .badminton(application.getBadminton())
                .date(application.getDate())
                .starting(application.getStarting())
                .build();
        return application;

    }

    @Override
    public List<Booking> findBookingById(Integer id) {
        return findAllInList(bookingRepo.findBookingById(id));

    }

    @Override
    public List<String> bookedTime(java.sql.Date date, Integer id) {
//        List<String>  time = new ArrayList<>();
//        for (int i=6; i<20; i++){
//            time.add(i-6, i+":00-"+(i+1)+":00");
//        }
//        List<String> bookedTime = bookingRepo.selectedTimes(date, id);
//
//        for (int i=0; i < bookedTime.size(); i++){
//            for (int j=0; j<time.size(); j++){
//                if (Objects.equals(time.get(j), bookedTime.get(i))){
//                    time.remove(j);
//                    j--;
//                }
//            }
//        }
//
//        return time;
        return bookingRepo.selectedTimes(date, id);
    }


    private void sendPassword(String email, String name, String badminton, String date, String time){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Booking Verification");
        message.setText("Hi"+ name+ "You have booked"+badminton+"badminton"+"on"+ date+"from"+time+"please be on time");
        mailSender.send(message);
    }


    @Override
    public void processPasswordResetRequest(String email){
        Optional<Booking> bookingOptional = bookingRepo.findByEmail(email);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            String name = booking.getUser().getName();
            String badminton = booking.getBadminton().getBadmintonname();
            String time = booking.getStarting();
            String date = String.valueOf(booking.getDate());
            sendPassword(email, name, badminton, time, date);

            bookingRepo.save(booking);
        }
    }
    @Override
    public void sendEmail() {
        try {
            Map<String, String> model = new HashMap<>();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template template = emailConfig.getTemplate("emailTemp.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo("sendfrom@yopmail.com");
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("Registration");
            mimeMessageHelper.setFrom("sendTo@yopmail.com");

            taskExecutor.execute(new Thread() {
                public void run() {
                    mailSender.send(message);
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}