package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.SmtpEmailDetailsDto;

public interface EmailSenderService {

    String sendSimpleMail(SmtpEmailDetailsDto SmtpEmailDetails);

    String sendMailWithAttachment(SmtpEmailDetailsDto SmtpEmailDetails);
}
